# Plan de Pruebas de Caja Negra - API Blogging Platform

## 1. Introducción
Este documento define los casos de prueba de caja negra para los endpoints de la API de la plataforma de blogs. Las pruebas se centran en validar las entradas del usuario frente a las restricciones de negocio (Jakarta Validation) y comprobar las respuestas HTTP adecuadas en escenarios de éxito y error.

## 2. Estrategia de Pruebas
- **Partición de Equivalencia:** Agrupar datos de entrada en rangos válidos e inválidos.
- **Análisis de Valores Límite:** Probar los límites exactos de los tamaños de los campos (ej. 2, 3, 40, 150 caracteres).
- **Pruebas de Seguridad:** Verificación de respuestas `400 Bad Request` cuando se viola una validación de DTO, garantizando que el `GlobalExceptionHandler` devuelva el mensaje correspondiente.

---

## 3. Casos de Prueba de DTOs y Endpoints

### 3.1. Gestión de Usuarios (`/api/users`)
**DTO Principal:** `UserRegisterUpdateDTO` / `UserLoginDTO`
- `username`: 3-40 caracteres, al menos una letra, permite [a-zA-Z0-9_.-]
- `email`: max 60 caracteres, formato email válido.
- `password`: 8-80 caracteres, al menos 1 minúscula, 1 mayúscula, 1 número, 1 carácter especial [@#$%^&+=!].

| ID | Endpoint | Método | Escenario de Prueba (Input) | Salida Esperada | Estado HTTP |
| :--- | :--- | :---: | :--- | :--- | :---: |
| **TC-U01** | `/register` | POST | **Válido:** `user: "Juan_12"`, `email: "juan@test.com"`, `pass: "Secret123!"` | Usuario registrado correctamente. | `200 / 201` |
| **TC-U02** | `/register` | POST | **Límite Inferior Inválido:** `username` con 2 caracteres ("Ju"). | JSON error: "Not a valid name..." | `400 Bad Request` |
| **TC-U03** | `/register` | POST | **Formato Inválido:** `username` solo numérico ("12345"). | JSON error: "Username must contain at least one letter..." | `400 Bad Request` |
| **TC-U04** | `/register` | POST | **Formato Inválido:** `password` débil ("password123"). carece de mayúscula y carácter especial. | JSON error detallando la regla del password. | `400 Bad Request` |
| **TC-U05** | `/login` | POST | **Credenciales correctas** de usuario existente. | Token JWT generado en la respuesta. | `200 OK` |
| **TC-U06** | `/login` | POST | **Credenciales incorrectas** (password erróneo). | "Invalid credentials" | `401 Unauthorized` |

### 3.2. Gestión de Publicaciones (`/api/posts`)
**DTO Principal:** `PostDto` / `PostDtoGetPostPut`
- `title`: No en blanco, 1-150 caracteres, debe contener letras, no más de 20 números seguidos.
- `content`: No en blanco, 1-6000 caracteres, debe contener letras, no más de 20 números seguidos.
- `user_id` / `status_id`: No nulos.

| ID | Endpoint | Método | Escenario de Prueba (Input) | Salida Esperada | Estado HTTP |
| :--- | :--- | :---: | :--- | :--- | :---: |
| **TC-P01** | `/posts` | POST | **Válido:** Title válido, Content válido, IDs válidos. | Post creado exitosamente. | `200 / 201` |
| **TC-P02** | `/posts` | POST | **Límite Superior Inválido:** `title` de 151 caracteres. | JSON error respecto al tamaño del título. | `400 Bad Request` |
| **TC-P03** | `/posts` | POST | **Formato Inválido:** `content` con solo números ("12345"). | JSON error: "Must contain at least one letter...". | `400 Bad Request` |
| **TC-P04** | `/posts` | POST | **Campo relacional omitido:** `user_id` no enviado. | JSON error: "User ID must be provided". | `400 Bad Request` |
| **TC-P05** | `/posts` | POST | **Seguridad Numérica:** `title` con 21 números seguidos. | JSON error: "Must contain at least one letter and cannot...".| `400 Bad Request` |

### 3.3. Gestión de Comentarios (`/api/comments`)
**DTO Principal:** `CommentDto` / `CommentDtoGetPostPut`
- `comment`: No en blanco, 2-3000 caracteres, debe contener letras, no más de 20 números seguidos.

| ID | Endpoint | Método | Escenario de Prueba (Input) | Salida Esperada | Estado HTTP |
| :--- | :--- | :---: | :--- | :--- | :---: |
| **TC-CM01**| `/comments`| POST | **Válido:** Comentario con texto válido. | Comentario publicado. | `200 / 201` |
| **TC-CM02**| `/comments`| POST | **Formato Inválido:** Comentario con 21 números seguidos. | JSON error por restricción de números. | `400 Bad Request` |
| **TC-CM03**| `/comments/{id}`| PUT | **Actualización Válida:** Modifica texto a formato válido. | Comentario actualizado. | `200 OK` |

### 3.4. Gestión de Categorías (`/api/categories`)
**DTO Principal:** `CategoryDTO` / `CategoryDTOGetPostPut`
- `category`: No en blanco, 1-60 caracteres, sin números.
- `description`: Máximo 250 caracteres, debe contener letras, no más de 20 números seguidos.

| ID | Endpoint | Método | Escenario de Prueba (Input) | Salida Esperada | Estado HTTP |
| :--- | :--- | :---: | :--- | :--- | :---: |
| **TC-CT01**| `/categories`| POST | **Válido:** `category: "Tecnologia"`, `description: "Texto"`. | Categoría creada. | `200 / 201` |
| **TC-CT02**| `/categories`| POST | **Formato Inválido:** `category` con números ("Tecnolog1a"). | JSON error: "Cannot contain numbers". | `400 Bad Request` |
| **TC-CT03**| `/categories`| POST | **Formato Inválido:** `description` pura de números. | JSON error por restricción alfabética/numérica. | `400 Bad Request` |

### 3.5. Gestión de Etiquetas (`/api/tags`)
**DTO Principal:** `TagDto` / `TagDtoGetPostPut`
- `tag`: No en blanco, 2-40 caracteres, sin números.

| ID | Endpoint | Método | Escenario de Prueba (Input) | Salida Esperada | Estado HTTP |
| :--- | :--- | :---: | :--- | :--- | :---: |
| **TC-TG01**| `/tags` | POST | **Válido:** `tag: "Java"` (sin números). | Etiqueta creada exitosamente. | `200 / 201` |
| **TC-TG02**| `/tags` | POST | **Límite Inferior Inválido:** `tag: "C"` (1 carácter). | JSON error: "Not a valid tag". | `400 Bad Request` |
| **TC-TG03**| `/tags` | POST | **Formato Inválido:** `tag: "Java17"` (contiene número).| JSON error: "Cannot contain numbers". | `400 Bad Request` |

### 3.6. Roles y Estados (`/api/roles` / `/api/statuses`)
**DTO Principal:** `RoleDTO`, `StatusDTO`.
- `role`: sin números.
- `status`: sin números.

| ID | Endpoint | Método | Escenario de Prueba (Input) | Salida Esperada | Estado HTTP |
| :--- | :--- | :---: | :--- | :--- | :---: |
| **TC-R01** | `/roles` | POST | **Válido:** `role: "admin"` | Rol persistido. | `200 / 201` |
| **TC-R02** | `/roles` | POST | **Formato Inválido:** `role: "admin1"` (contiene número).| JSON error: "Cannot contain numbers". | `400 Bad Request` |
| **TC-S01** | `/statuses`| POST | **Formato Inválido:** `status: "DRAFT2"` | JSON error: "Cannot contain numbers". | `400 Bad Request` |

---

## 4. Comportamiento del Interceptor Global de Errores
Para cualquier escenario donde se espere un `400 Bad Request` debido a validaciones fallidas, el Payload de respuesta debe contener el mapa exacto definido en el `GlobalExceptionHandler`:
```json
{
  "nombre_del_campo": "Mensaje de restricción definido en el DTO"
}
```
**Criterio de Aceptación:** Nunca se debe devolver una traza interna de Spring (Stack Trace) al cliente cuando falle una validación de `@Valid`.
