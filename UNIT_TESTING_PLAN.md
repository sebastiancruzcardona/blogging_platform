# **Plan de pruebas**

# **bloggin\_platform**

**Repositorio:** [https://github.com/sebastiancruzcardona/blogging\_platform](https://github.com/sebastiancruzcardona/blogging_platform) 

**Presentado por:** Sebastián Cruz Cardona  
**Código:** 240220222010

Historial de cambios del documento

| Número de versión | Fecha | Colaboradores | Notas de versión |
| ----- | ----- | ----- | ----- |
| V1.0.0 | 25/04/2026 | Sebastián Cruz Cardona | Versión inicial del plan de pruebas.  |
|  |  |  |  |
|  |  |  |  |

Última actualización el 25 de abril de 2026\.

# 

# **Descripción del producto de software**

El producto de software *blogging\_platform*, que de ahora en adelante se denominará en este documento como BP, es el backend completo para una plataforma de blogs. Está estructurado como una API REST y ha sido desarrollado en Java 17 con el framework Spring Boot. Esta API permite gestionar recursos como usuarios, roles, publicaciones, comentarios, categorías y estados, con el respaldo de una base de datos relacional MySQL 8.0. Toda su arquitectura está contenerizada, se utiliza Docker y Docker Compose para empaquetar y levantar la aplicación, la base de datos y un administrador visual de base de datos(Adminer) de forma sincronizada. Además cuenta con un flujo sencillo de Integración y Despliegue Continuo (CI/CD) configurado en GitHub Actions que se encarga de compilar una nueva versión de la aplicación y desplegarla automáticamente en un servidor en la nube (AWS EC2) cada vez que se suben cambios a la rama main del repositorio.

# **Objetivo de las pruebas**

El presente plan de pruebas tiene como objetivo evaluar algunas de las funcionalidades principales de BP mediante la planeación y ejecución de pruebas unitarias, de tal manera que se pueda asegurar que la API cumple con los requerimientos especificados y las expectativas de los usuarios en términos de funcionalidad en lo que respecta la gestión de Usuarios y a la gestión de Posts.  
Además, el objetivo es detectar y documentar cualquier defecto o anomalía en el sistema lo más temprano posible en el ciclo de desarrollo, para garantizar la entrega de un producto de alta calidad y confiabilidad en el entorno de producción.

# **Alcance**

El alcance de este plan de pruebas cubrirá la validación de métodos pertenecientes a la capa de servicio, asociados a 16 funcionalidades principales de la API relacionadas con la gestión de Usuarios y la gestión de Posts. Estas pruebas unitarias estarán orientadas a verificar el correcto funcionamiento de la lógica de negocio implementada en los servicios. A continuación se listan los métodos a probar:

| Módulo | Funcionalidad asociada | Método del servicio a probar | Objetivo de la función |
| :---: | :---: | :---: | :---: |
| User | Obtener todos los usuarios | UserService.findAll() | Obtener todos los usuarios registrados |
| User | Obtener usuario por ID | UserService.findById() | Obtener un usuario por su id |
| User | Obtener usuario por username | UserService.findByUsername() | Obtener un usuario por su username |
| User | Registro de usuario por un admin | UserService.save() | Crear un usuario por parte de un admin del blog |
| User | Registro público de usuario | UserService.saveForUser() | Registrarse en el blog como usuario |
| User | Iniciar sesión | UserDetailsService.loadUserByUsername() | Obtener UserDetails correcto por nombre de usuario al momento de la autenticación |
| User | Actualizar usuario por un admin | UserService.update() | Actualizar información de un usuario por parte de un admin del blog |
| User | Actualización del perfil propio de un usuario no Admin | UserService.updateForUser() | Actualizar información del usuario como usuario |
| User | Eliminar usuario | UserService.deleteById() | Eliminar usuario |
| Post | Obtener posts | PostService.findAll() | Obtener todos los posts creados |
| Post | Obtener posts por ID | PostService.findById() | Obtener un post por su id |
| Post | Obtener posts publicados | PostService.findPublished() | Obtener los posts que se encuentran publicados |
| Post | Crear Post | PostService.save() | Crear un post |
| Post | Editar Post | PostService.update() | Editar un post |
| Post | Actualizar Likes/Dislikes de un post | PostService.updateLikesDislikes() | Actualizar likes o dislikes del post |
| Post | Eliminar post | PostService.deleteById() | Eliminar un post por su id |

Quedan explícitamente excluidas del alcance de este plan las pruebas funcionales, las comprobaciones de seguridad del sistema, las revisiones de compatibilidad y portabilidad del software, las pruebas de integración, así como pruebas UI/UX.

# **Equipo**

El equipo de pruebas está conformado por un estudiante de Ingeniería de Software que asumirá el rol de tester dentro del proceso de aseguramiento de la calidad. Será responsable de la planificación, diseño y elaboración del plan de pruebas, así como de la ejecución de los casos de prueba definidos y la documentación detallada de los resultados obtenidos. Asimismo, tendrá a su cargo la identificación, registro y seguimiento de incidencias detectadas durante este proceso.  
Debido a que el alcance excluye explícitamente temas de seguridad, compatibilidad y UI/UX, el esfuerzo del equipo se concentrará estrictamente en garantizar la calidad funcional y la confiabilidad de la API BP antes de la entrega final en el entorno de producción.

# **Estrategia**

La estrategia de pruebas adoptada para BP se centrará en la validación de los módulos de Gestión de Usuarios y Gestión de Posts mediante la ejecución de pruebas unitarias orientadas a verificar el correcto comportamiento de los métodos de la capa de servicio definidos dentro del alcance. Para ello, se realizará una descomposición por módulos funcionales, permitiendo evaluar de manera independiente cada operación asociada a la lógica de negocio, como consultas, registros, actualizaciones, autenticación y eliminaciones.  
Cada caso de prueba será diseñado considerando el escenario positivo. Esta estrategia permitirá asegurar el cumplimiento de los requerimientos y la confiabilidad de la lógica interna de la aplicación.  
La ejecución de las pruebas se realizará en un entorno de desarrollo controlado utilizando pruebas unitarias basadas en mocks, con el fin de aislar el comportamiento de cada componente y evitar dependencias directas con servicios externos, en este caso, la base de datos. Para ello, se emplearán herramientas como JUnit 5 y Mockito, que permitirán simular el comportamiento de repositorios, servicios auxiliares y demás dependencias necesarias para validar de forma precisa la lógica implementada en los servicios de Usuarios y Posts.  
Finalmente, todos los resultados obtenidos serán documentados con registro de evidencias de ejecución, resultados esperados, resultados reales y defectos identificados, con el propósito de facilitar la trazabilidad de incidencias, el seguimiento de correcciones y la mejora continua de la calidad del software antes de su despliegue definitivo.

# **Criterios**

Para garantizar la calidad técnica y el rigor en la construcción del código se establecen los siguientes lineamientos:

## **i. Criterios de entrada**

Las pruebas unitarias comenzarán una vez que los métodos identificados en la capa de servicio (UserService, PostService y UserDetailsService) hayan finalizado su codificación y el código compile correctamente sin errores de sintaxis mediante Maven. Es requisito indispensable que el entorno local (Java 17\) esté configurado y que las dependencias de prueba (JUnit 5 y Mockito) estén correctamente integradas en el archivo pom.xml para permitir la simulación de los repositorios (Mocks de base de datos) de cara a la validación aislada de la lógica de negocio.

## **ii. Criterios de salida**

Se considerará que el ciclo de pruebas ha finalizado cuando se cumplan las siguientes condiciones de cobertura y ejecución automática:

- **Ejecución completa:** El 100% de los scripts de prueba diseñados con el framework JUnit 5 han podido ser ejecutados satisfactoriamente.  
- **Cobertura técnica:** Se debe alcanzar el éxito esperado en las pruebas según la prioridad de los requisitos técnicos del negocio.  
   

**iii. Prioridad de test cases**

| Prioridad | Relacionado con | ¿Qué significa? |
| :---- | :---- | :---- |
| Alta | Autenticación y CRUD Core: Login (loadUserByUsername), Creación, Edición y Eliminación de Usuarios y Posts (save, saveForUser, update, deleteById). | 100% de éxito de los tests. Obligatorio para garantizar la seguridad del sistema y la integridad de los datos. |
| Media | Consultas y filtros operacionales: Búsquedas específicas (findById, findByUsername, findPublished) y listados generales (findAll). | El 100% de éxito en los escenarios validados. Se deben revisar después que los métodos CRUD principales. |
| Baja | Métricas secundarias: Actualización de características sociales (updateLikesDislikes). | Ejecución sujeta a disponibilidad de tiempo en el ciclo actual de desarrollo. |

## **iv. Criterios de suspensión y reanudación**

La ejecución de las pruebas se suspenderá si se detectan errores críticos en la configuración de las anotaciones de simulación (@Mock, @InjectMocks de Mockito fallando recursivamente, etc.), dependencias rotas en Maven, o fallos estructurales en la sintaxis de las clases que impidan la compilación del proyecto generada por el comando mvn clean test. Las pruebas se reanudarán una vez que se restablezca la estabilidad del entorno y la compilación vuelva a ser exitosa.

## **v. Criterios de entrega**

Para considerar finalizadas las pruebas, las clases de la capa de servicio no deben presentar defectos críticos de lógica. El sistema debe demostrar el cumplimiento de los 16 requisitos técnicos analizados mediante la validación sistemática de su código Java aislado. Todo debe quedar documentado mediante los reportes de ejecución (generados por herramientas como Maven Surefire).

# **Ambientes**

Dado que la estrategia se enfoca puramente en pruebas unitarias a nivel de código fuente (Capa de Servicio) y excluye explícitamente pruebas de interfaz de usuario (UI) o integración, los requisitos de hardware y software se limitan a los entornos de compilación y ejecución de Java.  
Así las cosas se tendrán dos ambientes de prueba para garantizar la consistencia entre el desarrollo local y el despliegue continuo:

## **i. Entorno local de desarrollo (ejecución manual):**

- **Sistema Operativo:** Windows 11 (Entorno actual de la estación de trabajo principal).  
- **Plataforma Base:** Java Development Kit (JDK) 17\.  
- **Gestor de Dependencias y Construcción:** Apache Maven 3.x.  
- **Herramientas de Testing:** JUnit 5 (Framework de pruebas) y Mockito (Framework de simulación/mocking).  
- **IDE:** Visual Studio Code o InteliJ IDEA.

## **i. Entorno de integración continua (ejecución automática):**

- **Sistema Operativo:** ubuntu-latest (Provisto por los runners de GitHub Actions, según configuración en deploy.yml).  
- **Plataforma Base:** Java Development Kit (JDK) 17\.  
- **Ejecución:** Comando automatizado de Maven (mvn test o mvn clean package).

# **Entregables**

Los entregables que se generarán como resultado de este ciclo de pruebas son:

- **Plan de pruebas:** El presente documento.  
- **Scripts de pruebas automatizadas:** Conjunto de archivos fuente (clases) en lenguaje Java (con el sufijo \*Test.java, por ejemplo: UserServiceTest.java, PostServiceTest.java) ubicados en el directorio src/test/java/com/eam/bloggin\_platform. Estas clases contienen la lógica de validación unitaria utilizando JUnit 5 y la implementación de réplicas de dependencias mediante Mockito.  
- **Evidencias de ejecución:** Documento o bitácora extraída de los resultados de la compilación y validación, como los generados por el IDE (Visual Studio Code) o el plugin Maven Surefire. Esta evidencia muestra los logs de ejecución, aserciones exitosas, fallos por línea de código y cobertura de los métodos evaluados.  
- **Reporte de defectos técnicos:** Documento (Issues en el repositorio de GitHub) donde se registran las anomalías lógicas detectadas. Para cada anomalía se indicará el componente fallido, la excepción arrojada, la causa raíz técnica, y su estado de resolución.  
- **Informe final de pruebas:** Resumen técnico que certifica la solidez y estabilidad de la capa de servicio, garantizando que las 16 funcionalidades de negocio cumplen con los requisitos técnicos antes de autorizar la integración del código a la rama main y su subsecuente despliegue en EC2.

Todos estos entregables de código vivirán directamente en el repositorio de GitHub del proyecto, lo que garantizará que las pruebas y las evidencias viajen junto con el código fuente. La documentación complementaria se almacenará en [https://drive.google.com/drive/folders/18zlP5gFUVpzom9ZrSBJxC5xFq1AroYpC?usp=sharing](https://drive.google.com/drive/folders/18zlP5gFUVpzom9ZrSBJxC5xFq1AroYpC?usp=sharing) accesible para correos de la EAM, para garantizar su accesibilidad y trazabilidad.

# **Casos de prueba**

## **CP-User-Auth-01: UserDetailsServiceImpl.loadUserByUsername()**

**Módulo:** User (autenticación de usuarios)

**Objetivo de la prueba:** Validar que el servicio de autenticación recupere correctamente los detalles de seguridad de un usuario a partir de su username desde la base de datos, y verificar que la aplicación rechace de forma segura (lanzando la excepción correspondiente) los intentos de inicio de sesión con usuarios que no existen.

**Descripción de casos de prueba:**

(CP1) Pruebas de caja blanca sobre la función `loadUserByUsername()`. Se probarán dos escenarios:

**Escenario Positivo: Inicio de sesión con un usuario existente.**

Preparación: Se crea un objeto User en memoria (username "admin123", password "hashPassword" y rol "ROLE_ADMIN"). Se configura el Mock de UserRepository para que, cuando se llame al método `findByUsername("admin123")`, retorne un Optional que contiene a este usuario.

Acción: Se invoca el método `loadUserByUsername("admin123")` del servicio evaluado.

Resultado Esperado (Assert): El método debe retornar un objeto del tipo UserDetails (de Spring Security) no nulo. Se debe asegurar mediante aserciones (assertEquals) que el username, el password y los roles del objeto retornado coinciden exactamente con los de la entidad original mockeada.

**Escenario Negativo: Inicio de sesión con un usuario inexistente.**

Preparación: Se configura el Mock de UserRepository para que, al llamar al método `findByUsername("usuarioFalso")`, retorne un Optional.empty() (simulando que el usuario no está registrado en la base de datos).

Acción: Se intenta invocar el método `loadUserByUsername("usuarioFalso")`.

Resultado Esperado (Assert): El flujo de ejecución debe interrumpirse y el método debe lanzar explícitamente la excepción UsernameNotFoundException. Se debe validar con assertThrows() que la excepción es capturada correctamente por el entorno de prueba.

**Escenarios:**

1. Escenario positivo: Validar que el servicio de autenticación recupere correctamente los detalles de seguridad de un usuario a partir de su username.
2. Escenario negativo: Verificar que la aplicación rechace de forma segura (lanzando la excepción correspondiente) los intentos de inicio de sesión con usuarios que no existen.

## **CP-User-Service-02 a CP-User-Service-09: UserService (CRUD y consultas)**

**Módulo:** User (gestión de usuarios): UserService

**Objetivo:** Validar que el servicio permita ejecutar correctamente el CRUD de usuario + consultas y filtros de usuario:

- UserService.save()
- UserService.saveForUser()
- UserService.update()
- UserService.updateForUser()
- UserService.deleteById()
- UserService.findAll()
- UserService.findById()
- UserService.findByUsername()

**Descripción:**

(CP-02) Registro de un usuario por un Admin. Prueba de caja blanca sobre la función UserService.save().

Objetivo: Validar la creación exitosa de un usuario asignando un rol específico proporcionado en el DTO.

Proceso:
Preparación: Se crea un UserDTO con datos de usuario y roleID = 1L. Se configura RoleRepository.findById(1L) para retornar un Optional<Role> presente. Se configura PasswordEncoder.encode() para retornar un hash simulado. Se configura UserRepository.save() para que retorne un objeto User con los datos poblados y el rol asignado.
Acción: Se invoca UserService.save(userDTO).
Resultado Esperado (Assert): El método debe retornar un Optional<UserDTOGetPostPut> presente. Mediante assertEquals se valida que los datos del DTO retornado coincidan con el DTO enviado inicialmente. Se verifica con verify() que userRepository.save() fue invocado exactamente una vez.

(CP-03) Registro público de un usuario. Prueba de caja blanca sobre la función UserService.saveForUser().

Objetivo: Validar el registro de un nuevo usuario forzando la asignación del rol por defecto ("author").

Proceso:
Preparación: Se crea un UserRegisterUpdateDTO con username, email y password. Se configura RoleRepository.findByRole("author") para retornar un Optional<Role> presente. Se configura PasswordEncoder.encode() para el hash. Se configura UserRepository.save() para retornar el User guardado.
Acción: Se invoca UserService.saveForUser(userRegisterUpdateDTO).
Resultado Esperado (Assert): El método debe retornar un Optional<UserDTOGetPostPut> presente. Mediante aserciones, se confirma que el objeto devuelto no es nulo y contiene los datos registrados. Se verifica con verify() que userRepository.save() fue invocado exactamente una vez.

(CP-04) Actualización de un usuario por un Admin. Prueba de caja blanca sobre la función UserService.update().

Objetivo: Validar la modificación correcta de los datos incluyendo el rol de un usuario existente mediante su ID.

Proceso:
Preparación: Se define id = 1L y un UserDTO con los datos a actualizar y roleID = 2L. Se configura UserRepository.findById(1L) para retornar un Optional<User> existente. Se configura RoleRepository.findById(2L) para retornar un nuevo Optional<Role>. Se configura PasswordEncoder.encode(). Se configura UserRepository.save() para retornar el usuario modificado.
Acción: Se invoca UserService.update(1L, userDTO).
Resultado Esperado (Assert): El método retorna un Optional<UserDTOGetPostPut> presente. Se valida mediante assertEquals que el DTO resultante refleja los nuevos valores inyectados desde el userDTO de entrada.

(CP-05) Actualización del perfil propio de un usuario no Admin. Prueba de caja blanca sobre la función UserService.updateForUser().

Objetivo: Validar que un usuario pueda actualizar sus campos básicos (username, email, password) sin alterar su rol.

Proceso:
Preparación: Se define id = 1L y un UserRegisterUpdateDTO con las modificaciones. Se configura UserRepository.findById(1L) para retornar un Optional<User> preexistente (que ya tiene un rol asignado internamente). Se configura PasswordEncoder.encode(). Se configura UserRepository.save() para retornar la entidad actualizada.
Acción: Se invoca UserService.updateForUser(1L, userRegisterUpdateDTO).
Resultado Esperado (Assert): Retorna un Optional<UserDTOGetPostPut> presente. Las aserciones confirman que los datos llanos (email, username) fueron modificados correctamente en el DTO de salida devuelto.

(CP-06) Eliminar usuario. Prueba de caja blanca sobre la función UserService.deleteById().

Objetivo: Verificar que el método confirma la existencia del ID y ejecuta el borrado, retornando confirmación de éxito.

Proceso:
Preparación: Se define id = 1L. Se configura UserRepository.findById(1L) para retornar un Optional<User> presente (validando que existe). El método deleteById(1L) del repositorio no requiere configuración (Mockito asume doNothing() para métodos void por defecto).
Acción: Se invoca UserService.deleteById(1L).
Resultado Esperado (Assert): El método debe retornar el booleano true. Se usa assertTrue(resultado) para validarlo. Se constata mediante verify(userRepository, times(1)).deleteById(1L) que la instrucción de borrado en base de datos fue realizada una vez.

(CP-07) Obtener todos los usuarios. Prueba de caja blanca sobre la función UserService.findAll().

Objetivo: Validar que el método retorne correctamente la lista de todos los usuarios registrados, convertidos a sus respectivos DTOs de salida.

Proceso:
Preparación: Se crea una List<User> simulada en memoria que contenga al menos dos objetos User debidamente poblados. Se configura el mock UserRepository.findAll() para que retorne esta lista.
Acción: Se invoca UserService.findAll().
Resultado Esperado (Assert): El método debe retornar un objeto de tipo List<UserDTOGetPostPut>. Se utilizan aserciones para asegurar que la lista devuelta no sea nula (assertNotNull) y que su tamaño (size()) sea exactamente igual a la lista simulada. Se puede verificar que al menos un dato (ej. el email del primer elemento) coincida, validando asi la correcta conversion manual que hace el ciclo for interno. Se verifica una sola invocacion al userRepository.findAll().

(CP-08) Obtener usuario por ID. Prueba de caja blanca sobre la función UserService.findById().

Objetivo: Validar la recuperacion correcta y el mapeo a DTO de un usuario especifico dado su ID de base de datos.

Proceso:
Preparacion: Se define id = 1L y se crea en memoria una entidad User con ese ID. Se configura el mock UserRepository.findById(1L) para retornar un Optional<User> presente que contenga dicha entidad.
Accion: Se invoca UserService.findById(1L).
Resultado Esperado (Assert): El metodo debe retornar un Optional<UserDTOGetPostPut> presente. Mediante assertEquals se valida que los datos nativos de la entidad (ej. username, email) se hayan transferido intactos al DTO encapsulado. Se verifica con verify() que userRepository.findById(1L) fue ejecutado exactamente una vez.

(CP-09) Obtener un usuario por username. Prueba de caja blanca sobre la función UserService.findByUsername().

Objetivo: Validar la busqueda, hallazgo y conversion a DTO de un usuario partiendo estrictamente de su nombre de usuario.

Proceso:
Preparacion: Se define username = "sebastian_admin" y se crea el User respectivo en memoria. Se configura el mock UserRepository.findByUsername("sebastian_admin") para que retorne el Optional.of(user) correspondiente a dicha entidad.
Accion: Se invoca UserService.findByUsername("sebastian_admin").
Resultado Esperado (Assert): Retorna el Optional<UserDTOGetPostPut> correctamente presente. Las aserciones confirman que el atributo username del DTO interno coincida perfectamente con la cadena enviada como parametro. Se usa verify(userRepository).findByUsername("sebastian_admin") para asegurar que la capa de datos simulada proceso la instruccion correctamente de acuerdo a ese parametro unico.

**Escenarios:**

1. (CP-02) Registro de un usuario por un Admin: Validar la creación exitosa de un usuario asignando un rol específico proporcionado en el DTO.
2. (CP-03) Registro público de un usuario: Validar el registro de un nuevo usuario forzando la asignación del rol por defecto ("author").
3. (CP-04) Actualización de un usuario por un Admin: Validar la modificación correcta de los datos incluyendo el rol de un usuario existente mediante su ID.
4. (CP-05) Actualización del perfil propio de un usuario no Admin: Validar que un usuario pueda actualizar sus campos básicos (username, email, password) sin alterar su rol.
5. (CP-06) Eliminar usuario: Verificar que el método confirma la existencia del ID y ejecuta el borrado, retornando confirmación de éxito.
6. (CP-07) Obtener todos los usuarios: Validar que el método retorne correctamente la lista de todos los usuarios registrados, convertidos a sus respectivos DTOs de salida.
7. (CP-08) Obtener usuario por ID: Validar la recuperación correcta y el mapeo a DTO de un usuario específico dado su ID de base de datos.
8. (CP-09) Obtener un usuario por username: Validar la búsqueda, hallazgo y conversión a DTO de un usuario partiendo estrictamente de su username.

## **CP-Post-Service-10 a CP-Post-Service-16: PostService (CRUD y consultas)**

**Módulo:** Post (gestión de posts): PostService

**Objetivo:** Validar que el servicio permita ejecutar correctamente el CRUD de post + consultas y filtros de post:

- PostService.save()
- PostService.update()
- PostService.deleteById()
- PostService.findAll()
- PostService.findById()
- PostService.findPublished()
- PostService.updateLikesDislikes()

**Descripción:**

(CP-10) Crear post. Prueba de caja blanca sobre la función PostService.save().

Objetivo: Validar la correcta creación de un Post, garantizando la vinculación del usuario que lo creó, el estado asignado y el establecimiento inicial de variables (likes, dislikes, fechas).

Proceso:
Preparación: Se crea un PostDto válido (con userId = 1L y statusId = 1L). Se configuran los mocks: UserRepository.findById(1L) retorna un Optional<User> presente, y StatusRepository.findById(1L) retorna un Optional<Status> presente. Se configura PostRepository.save() para retornar una entidad Post idéntica a la enviada.
Acción: Se invoca PostService.save(postDto).
Resultado Esperado (Assert): El método debe retornar un Optional<PostDtoGetPostPut> presente. Las aserciones deben validar que el DTO devuelto tenga contadores inicializados (likes == 0, dislikes == 0). Se utiliza verify() para confirmar que postRepository.save(any(Post.class)) fue invocado exactamente una vez.

(CP-11) Editar post. Prueba de caja blanca sobre la función PostService.update().

Objetivo: Asegurar que las actualizaciones modifiquen correctamente campos como el título y contenido, y actualicen la fecha de última modificación sin corromper el creador original.

Proceso:
Preparación: Se define id = 10L y un PostUpdateDTO con nuevos valores y un status_id = 2L. Se configura PostRepository.findById(10L) para retornar un Optional<Post> preexistente, y StatusRepository.findById(2L) para retornar el nuevo estado. Finalmente, PostRepository.save() se configura para regresar la entidad actualizada.
Acción: Se llama a PostService.update(10L, postUpdateDTO).
Resultado Esperado (Assert): Retorna Optional<PostDtoGetPostPut> presente. Mediante assertEquals se confirma que la información del DTO de retorno provienen del objeto postUpdateDTO ingresado.

(CP-12) Eliminar post. Prueba de caja blanca sobre la función PostService.deleteById().

Objetivo: Verificar que el método confirma la existencia del ID, ejecuta el borrado del post y retorna confirmación de éxito.

Proceso:
Preparación: Se define id = 99L. Se programa PostRepository.findById(99L) para retornar un Optional<Post> indicando que el registro existe y que el código debe proceder al bloque if principal.
Acción: Se dispara el método PostService.deleteById(99L).
Resultado Esperado (Assert): La función evaluada debe retornar explícitamente true. Esto se acompaña validando sistemáticamente el pase al nivel de datos con verify(postRepository, times(1)).deleteById(99L).

(CP-13) Obtener todos los posts. Prueba de caja blanca sobre la función PostService.findAll().

Objetivo: Validar que el método retorne correctamente la lista de todos los posts registrados, convertidos a sus respectivos DTOs de salida, contrastando el tamaño de la lista de salida esperado con el tamaño de la lista retornada.

Proceso:
Preparación: Se instancia localmente una List<Post> cargada con entidades. Se vincula al mock PostRepository.findAll() para que esta estructura sea devuelta automáticamente.
Acción: Se lanza PostService.findAll().
Resultado Esperado (Assert): Debe retornar un objeto de tipo List<PostDtoGetPostPut>. Se aplica un assertEquals(expectedListSize, returnedList.size()) constatando el número exacto, más la comprobación de una sola ejecución de PostRepository.findAll().

(CP-14) Obtener post por ID. Prueba de caja blanca sobre la función PostService.findById().

Objetivo: Validar la recuperación correcta y el mapeo a DTO de un post específico dado su ID de base de datos.

Proceso:
Preparación: Se define id = 1L y se crea en memoria una entidad Post con ese ID. Se configura el mock PostRepository.findById(1L) para retornar un Optional<Post> presente que contenga dicha entidad.
Acción: Se invoca PostService.findById(1L).
Resultado Esperado (Assert): El método debe retornar un Optional<PostDtoGetPostPut> presente. Mediante assertEquals se valida que los datos nativos de la entidad (ej. title, content) se hayan transferido intactos al DTO encapsulado. Se verifica con verify() que postRepository.findById(1L) fue ejecutado solamente una vez.

(CP-15) Obtener posts publicados. Prueba de caja blanca sobre la función PostService.findPublished().

Objetivo: Validar la obtención y filtrado correcto de los posts, asegurando que se retornen y mapeen a DTO únicamente aquellos cuyo estado corresponda a "Publicado" (status_id = 2L).

Proceso:
Preparación: Se crea en memoria una List<Post> que incluya entidades mixtas: algunas con un Status cuyo ID sea 2L (Publicado) y otras con un ID diferente (ej. 1L para Borrador). Se configura el mock PostRepository.findAll() para retornar esta lista predefinida.
Acción: Se invoca PostService.findPublished().
Resultado Esperado (Assert): El método debe retornar una List<PostDtoGetPostPut>. Mediante assertEquals se valida que el tamaño de la lista resultante coincida exclusivamente con la cantidad de posts "Publicados" que se prepararon en la lista original. Se verifica con verify() que postRepository.findAll() fue ejecutado únicamente una vez.

(CP-16) Actualizar Likes/Dislikes de un post. Prueba de caja blanca sobre la función PostService.updateLikesDislikes().

Objetivo: Comprobar la modificación precisa de los atributos numéricos de engagement social sin alterar el resto de la entidad.

Proceso:
Preparación: Se define id = 15L y se crea un PostLikesDislikesDTO con métricas (ej. likes = 5, dislikes = 1). Se configura PostRepository.findById(15L) inyectando un Post simulado donde esos valores eran 0. Se configura el PostRepository.save() para retornar este Post tras asignarle las nuevas métricas.
Acción: Se ejecuta PostService.updateLikesDislikes(15L, postLikesDislikesDTO).
Resultado Esperado (Assert): El método devuelve Optional<PostDtoGetPostPut> presente. Se comprueba mediante assertEquals(5, dto.getLikes()) que la operación modificó las variables exclusivamente y guardó el cambio.

**Escenarios:**

1. (CP-10) Crear post: Validar la correcta creación de un Post, garantizando la vinculación del usuario que lo creó, el estado asignado y el establecimiento inicial de variables (likes, dislikes, fechas).
2. (CP-11) Editar post: Asegurar que las actualizaciones modifiquen correctamente campos como el título y contenido, y actualicen la fecha de última modificación sin corromper el creador original.
3. (CP-12) Eliminar post: Verificar que el método confirma la existencia del ID, ejecuta el borrado del post y retorna confirmación de éxito.
4. (CP-13) Obtener todos los posts: Validar que el método retorne correctamente la lista de todos los posts registrados, convertidos a sus respectivos DTOs de salida, contrastando el tamaño de la lista de salida esperado con el tamaño de la lista retornada.
5. (CP-14) Obtener post por ID: Validar la recuperación correcta y el mapeo a DTO de un post específico dado su ID de base de datos.
6. (CP-15) Obtener posts publicados: Validar la obtención y filtrado correcto de los posts, asegurando que se retornen y mapeen a DTO únicamente aquellos cuyo estado corresponda a "Publicado" (status_id = 2L).
7. (CP-16) Actualizar Likes/Dislikes de un post: Comprobar la modificación precisa de los atributos numéricos de engagement social sin alterar el resto de la entidad.

# **Gestión de incidentes**

Se utilizará el siguiente flujo de trabajo para abordar la gestión de los defectos que lleguen a encontrarse:

# **Matriz de riesgo**

| Riesgo | Probabilidad | Impacto | Mitigación | Contingencia |
| :---- | :---- | :---- | :---- | :---- |
| Retrasos en la ejecución de las pruebas | Media | Alto | Planificación de actividades, definición previa de casos de prueba y seguimiento del progreso | Repriorización de funcionalidades críticas y ajuste del alcance de pruebas |
| Dificultad en la implementación de mocks (Mockito) | Media | Alto | Estudio previo de la herramienta, revisión de documentación oficial y desarrollo de pruebas simples iniciales | Simplificación de escenarios de prueba o apoyo en recursos externos (tutoriales, ejemplos, asesoría del docente, ayuda de IA) |
| Errores en la definición de casos de prueba | Baja | Alta | Validación de cada caso con el docente antes de proceder a las pruebas | Corrección iterativa de casos de prueba durante la ejecución y con asesoría del docente |
| Problemas técnicos en entorno de pruebas | Media | Medio | Verificación previa de dependencias (Java, Spring, JUnit, Mockito) y configuración del proyecto | Reinstalación del entorno o uso de configuraciones alternativas. Solicitud de asesoría al docente y/o a la IA |

# **Tareas**

**![][image1]**  


[image1]: <data:image/png;base64,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>