
# Peticiones

 - listar usuarios:
    - URL: /api/all
    - Permisos: token de sesión
    - Petición: NADA
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
    - Ejemplo:
      - Respuesta
      
      ````
      Ej 
      ````
    
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
        
    - Status code:
      - 200: correcto
      - 400: usuario incorrecto
      - 417: password incorrecto  
     - Respuesta: Usuario con el token de sesión
        ```
        "mail": null,
        "nombre": "usr",
        "password": "1234",
        "token": "TOKEN",
        "role": null
        ```
      - Ejemplo:
        - Respuesta
      
        ````
        Ej 
        ````
    
        
     - Registrar:
        - URL: /api/register
        - Permisos: NADA
        - Petición:
       
        ```
        {
        "mail": "mail"
        "nombre": "usr",
        "password": "123554"       
         }
        ```
        - Status code:
          - 201: creado
          
          - 417: usuario o mail existen
        
        
         - Respuesta: Usuario con el token de sesión
          ```
          "mail": "usr6@gmail.com",
          "nombre": "usr6",
          "password": "$2a$10$DqKn46IT8TQYlufXOC3nYuFVcADbcspRqSuRyMwP6lMqN0DbJwWxy",
          "token": "TOKEN",
          "role": "USER"
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
