
# Peticiones

 - listar usuarios:
    - URL: /api/all
    - Permisos: token de sesión
    - Petición: NADA
    ```
    peticion
    ```
    - Status code:
        - Ok: correcto
        - Forbidden: no tienes permisos   

    - Respuesta: lista  de todos los usuarios con formarto 
    ```
    { 
    "mail": <>
    "nombre": <>
     password: <>
    }
    ```
    - Login:
        - URL: /api/login
        - Permisos: NADA
        - Petición:
       
        ```
        {        
        "nombre": "usr",
        "password": "123554"       
         }
        ```
         - Respuesta: Usuario con el token de sesión
        ```
        "mail": null,
        "nombre": "usr",
        "password": "1234",
        "token": "TOKEN",
        "role": null
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

201: creado

417: contraseña incorrecta

400: bad request
