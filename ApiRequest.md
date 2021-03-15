
# Peticiones

 - listar usuarios:
    - URL: /api/all
    - Permisos: token de sesión
    - Petición:
    ```
    peticion
    ```
    - Status code:
        - Ok: correcto
        - Forbidden: no tienes permisos   

    - Respuesta: lista  de todos los usuarios con formarto 
    ```
    { "mail": <>
    "nombre": <>
     password: <>
    }
    ```
    - Ejemplo:
        - Petición:
        ```
        ej
        ```
         - Respuesta:
        ```
        ej
        ```



registrar usuario:
(/api/register)
Body:
mail:"correo"
nombre:"nombre"
password:"password"

Si registro correcto:
codigo 201 y token:"token" (Devuelve todo el usuario, entre ellos el campo token)

Si registro incorrecto:
codigo 417 (El usuario o el mail ya estan en uso)


Iniciar sesion:
(/api/login)
Body:
nombre:"nombre"
password:"password"

Si login correcto:
nombre:"nombre"
password:"password"
token:"token"
(demas campos del usuario a null)

Si login incorrecto:
Usuario incorrecto: error 400 
Contraseña incorrecta: error 417


# Códigos

200: ok

2001:

417: contraseña incorrecta
