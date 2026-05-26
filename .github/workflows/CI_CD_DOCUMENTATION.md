# Documentación del Pipeline CI/CD

## Flujo general del Pipeline CI/CD:

```text
[Push / Pull Request (ramas: main, desarrollo)] 
   → [Trigger GitHub Actions] 
   → [Entorno GitHub Runner (ubuntu-latest)] 
   → [Checkout del repositorio]
   → [Setup Java 17 & Maven] 
   → [Ejecución de Pruebas (mvn test)] 
   → [Condición: ¿Es la rama 'main'?]
   → [Conexión SSH a Instancia EC2 AWS]
   → [Actualización de código en el servidor]
   → [Despliegue y Orquestación (Docker Compose)]
```

## Descripción de los pasos

**PASO 1 Trigger: Detección del Push y Pull Request**
El sistema de GitHub Actions está configurado para escuchar eventos de tipo push o pull_request explícitamente en las ramas main y desarrollo. Al detectar actividad en estas ramas, lanza el pipeline automáticamente.

**PASO 2 Provisión del entorno de ejecución**
Se aprovisiona un entorno virtual (runner) proporcionado por GitHub bajo el sistema operativo ubuntu-latest. Este será el encargado de ejecutar de manera aislada el trabajo inicial de Integración Continua (Build & Test).

**PASO 3 Checkout del Código**
El runner clona o hace fetch del repositorio en el commit exacto que disparó el push (via actions/checkout), garantizando reproducibilidad y trazabilidad del artefacto resultante.

**PASO 4 Configuración de dependencias**
Se instala Java JDK 17 (distribución Temurin) preparándolo para compilar, se configura la caché de dependencias y se habilitan los permisos de ejecución para el wrapper de Maven (mvnw).

**PASO 5 Ejecución de Pruebas Unitarias**
Ubicándose en el directorio del proyecto (./blogging_platform), el sistema ejecuta los tests unitarios y de integración mediante el comando ./mvnw test. Si alguna prueba falla, el pipeline se detiene garantizando que código defectuoso no pase a las siguientes etapas. Se generan reportes de cobertura para análisis posterior.

**PASO 6 Validación de reglas para despliegue**
Antes de iniciar el trabajo de Despliegue Continúo (CD), el pipeline impone dos reglas: el trabajo anterior de pruebas (Test) debió finalizar con éxito, y la rama que disparó el flujo debe ser estrictamente main. Si esto se cumple, levanta un nuevo runner para continuar.

**PASO 7 Conexión segura al entorno de producción**
El runner ensambla de forma segura una llave privada previamente almacenada en los Secretos de GitHub. Usando esta llave, se autentica y establece una conexión SSH con la instancia EC2 remota (AWS) de producción.

**PASO 8 Actualización de código en el servidor**
Una vez dentro del servidor EC2, el script verifica si el proyecto ya existe; si no, lo clona. Si ya existe, se fuerza una sincronización con la rama main remota (git pull origin main), asegurando que el servidor tenga exactamente el mismo código que pasó las pruebas.

**PASO 9 Despliegue y Orquestación (Docker Compose)**
Mediante comandos de infraestructura como código, se reconstruyen las imágenes de los servicios actualizados (`docker compose build`) y se levantan los contenedores en segundo plano (`docker compose up -d`). Esto pone a correr la aplicación Java y la base de datos MySQL, culminando el ciclo de despliegue en producción.

## 3. Detalle por paso

**3.1 Trigger: Detección del Push y Pull Request**
Configuración de los eventos que inician el pipeline para las ramas especificadas:
```yaml
on:
  push:
    branches: [ 'main', 'desarrollo' ]
  pull_request:
    branches: [ 'main', 'desarrollo' ]
```

**3.2 Provisión del entorno de ejecución**
Definición del entorno virtual (runner) donde ejecutarán los Jobs:
```yaml
    runs-on: ubuntu-latest
```

**3.3 Checkout del Código**
Acción de GitHub para obtener el código fuente en el entorno:
```yaml
      - uses: actions/checkout@v2
```

**3.4 Configuración de dependencias**
Instalación de Java 17, configuración de la caché de Maven y permisos para el wrapper:
```yaml
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
          cache: maven

      - name: Make mvnw executable
        run: chmod +x mvnw
```

**3.5 Ejecución de Pruebas Unitarias**
Definición del directorio de trabajo y ejecución del comando de pruebas de Maven:
```yaml
    defaults:
      run:
        working-directory: ./blogging_platform
...
      - name: Run Maven Tests
        run: ./mvnw test
```

**3.6 Validación de reglas para despliegue**
Condiciones esenciales (dependencia de tests y restricción de rama) para lanzar el trabajo de deploy:
```yaml
  Deploy:
    name: Deploy to EC2
    needs: Test # <--- CRUCIAL: Esto indica que no puede haber Deploy si el 'Test' falla
    if: github.ref == 'refs/heads/main' # <--- RESTRICCIÓN: Solo ejecuta en la rama main
```

**3.7 Conexión segura al entorno de producción**
Uso de Secrets para variables sensibles y preparación de la llave SSH para la conexión:
```yaml
      - name: Build & Deploy via SSH
        env:
          PRIVATE_KEY: ${{ secrets.SSH_PRIVATE_KEY }}
          HOST_NAME: ${{ secrets.SSH_HOST }}
          USER_NAME: ${{ secrets.USER_NAME }}

        run: |
          echo "$PRIVATE_KEY" > private_key.pem && chmod 400 private_key.pem
          ssh -o StrictHostKeyChecking=no -i private_key.pem ${USER_NAME}@${HOST_NAME} '
```

**3.8 Actualización de código en el servidor**
Comandos bash remotos para clonar o sincronizar la rama main en la instancia EC2:
```bash
          if [ ! -d "/home/ubuntu/cruz-rodriguez-arias/.git" ]; then
            git clone https://github.com/sebastiancruzcardona/blogging_platform.git /home/ubuntu/cruz-rodriguez-arias;
          fi

          cd /home/ubuntu/cruz-rodriguez-arias &&

          git checkout main &&
          git fetch --all &&
          git reset --hard origin/main &&
          git pull origin main &&
```

**3.9 Despliegue y Orquestación (Docker Compose)**
Reconstrucción e inicio de los servicios orquestados en los contenedores:
```bash
          docker compose build &&
          docker compose up -d
          '
```

## 4. Consideraciones Clave

| Aspecto | Consideración en este Pipeline (deploy_2.yml) |
| :--- | :--- |
| **Orden de tests** | Se respeta estrictamente. El trabajo de *Deploy* exige `needs: Test`, garantizando que jamás se despliegue código si las pruebas unitarias fallan. |
| **Artefactos o Build Output** | Actualmente el entorno de Docker se reconstruye directamente en producción (`docker compose build`). *Como mejora futura, se podrían guardar los artefactos compilados mediante `actions/upload-artifact`.* |
| **Secrets y Seguridad** | Todas las variables críticas (usuario, host y llave privada SSH) nunca están en texto plano. Se inyectan de manera segura a través de **GitHub Secrets** (`${{ secrets.* }}`). |
| **Ambientes Separados** | Hay protección de ramificaciones. Aunque la integración continua se prueba tanto en `desarrollo` como en `main`, el despliegue a producción tiene un candado (`if: github.ref == 'refs/heads/main'`). |
| **Estrategia de Rollback** | Dado que el CD hace un `git reset --hard origin/main`, para aplicar un rollback bastaría con realizar un *revert* al commit anterior en GitHub, lo cual disparará este pipeline instalando la versión estable previa. |

## 5. Diagrama del Flujo

Representación del pipeline `deploy_2.yml` con sus etapas y puntos de control:

```text
  ┌─────────────────────────────────────────────────────────────┐
  │                    PIPELINE CI/CD (GitHub Actions)          │
  └─────────────────────────────────────────────────────────────┘
          │
          ▼
  ┌─────────────┐
  │  git push / │  ← Desarrollador hace push o PR a main/desarrollo
  │  pull req.  │
  └──────┬──────┘
         │
         ▼
  ┌─────────────┐
  │   Trigger   │  ← GitHub Actions detecta el evento
  └──────┬──────┘
         │
         ▼
  ┌─────────────┐
  │   Runner    │  ← Inicia Ubuntu (ubuntu-latest)
  └──────┬──────┘
         │
         ▼
  ┌─────────────┐
  │  Checkout   │  ← Descarga el repo (actions/checkout)
  └──────┬──────┘
         │
         ▼
  ┌─────────────┐
  │  Setup &    │  ← Setup Java 17 y compilación via Maven
  │  Build      │
  └──────┬──────┘
         │
         ▼
  ┌─────────────┐    FALLO
  │    Tests    │ ──────────► CI Pipeline abortado
  └──────┬──────┘
         │ OK
         ▼
  ┌─────────────┐    NO (ej. desarrollo)
  │ ¿Es main?   │ ──────────► Finaliza Pipeline exitosamente (solo CI)
  └──────┬──────┘
         │ SÍ
         ▼
  ┌─────────────┐
  │ Conexión SSH│  ← Conexión remota a AWS EC2 asumiendo Secretos
  └──────┬──────┘
         │
         ▼
  ┌─────────────┐
  │  Git Pull   │  ← Actualiza el código en el servidor
  └──────┬──────┘
         │
         ▼
  ┌─────────────┐
  │   Docker    │  ← docker compose build && up -d
  │   Compose   │    Artefacto corriendo en Producción
  └─────────────┘
```
