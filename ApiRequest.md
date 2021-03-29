
# Peticiones

- Listar usuarios:
  - Método: GET
  - URL: /api/all
  - Permisos: token (copiar la ristra SIN COMILLAS y pegar en Authorization -> Type:Bearer Token)
  - Petición: NADA (body -> none)

  - Status code:
    - Ok: correcto

    - Forbidden: no tienes permisos

  - Respuesta: lista  de todos los usuarios con formarto
    {
    "mail": MAIL,
    "nombre": NOMBRE,
    "password": PSWD,
    "token": null, //No vale para nade
    "role": "USER"
    }

    
- Registrar: 
  - Método: POST
  - URL: /api/register
  - Permisos: NADA
  - Petición: (body: raw + JSON donde pone Text)
    {
    "mail": "MAIL"
    "nombre": "NAME",
    "password": "PSWD"       
    }
  
  - Status code:
    - 201: creado
    - 417: usuario o mail existen

  - Respuesta: Usuario con el token de sesión
    "mail": "MAIL",
    "nombre": "NOMBRE",
    "password": "PSWD",
    "token": "TOKEN",
    "role": "USER"
    
  - Si registro correcto:
      codigo 201(CREATED) y token vale una cosa mu larga 
  - Si registro incorrecto:
      codigo 417 (El usuario o el mail ya estan en uso)

- Login:
  - Método: POST
  - URL: /api/login
  - Permisos: NADA
  - Petición: (body: raw + JSON donde pone Text)
    {
    "nombre": "NOMBRE",
    "password": "PSWD"
    }

  - Status code:
    - 200: correcto
    - 400: usuario incorrecto
    - 417: password incorrecto

  - Respuesta: Usuario con el token de sesión
    "mail": null,
    "nombre": "NOMBRE",
    "password": "PSWD",
    "token": "TOKEN",
    "role": null

  - Si login correcto:
      codigo 200 (OK) y token vale una cosa mu larga
  - Si login incorrecto:
      codigo 400 si nombre mal
      codigo 417 si contraseña incorrecta


- Registrar:
  - Método: POST
  - URL: /api/register
  - Permisos: NADA
  - Petición: (body: raw + JSON donde pone Text)
    {
    "mail": "MAIL"
    "nombre": "NOMBRE",
    "password": "PSWD"
    }

  - Status code:
    - 201: creado
    - 417: usuario o mail existen

  - Respuesta: Usuario con el token de sesión
    "mail": "MAIL",
    "nombre": "NOMBRE",
    "password": "PSWD",
    "token": "TOKEN",
    "role": "USER"

  - Si registro correcto:
      codigo 201(CREATED) y token vale una cosa mu larga
  - Si registro incorrecto:
      codigo 417 (El usuario o el mail ya estan en uso)


- Aceptar petición amistad:
  - Método: POST
  - URL: /api/acceptRequest
  - Permisos: TOKEN
  - Petición: 
    HEADER: añadir un campo: key="identificador" y value="tu_nombre"
    BODY: raw + JSON donde pone Text
    {
    "nombre": "NOMBRE" //Nombre del user cuya petición quieres aceptar
    }

  - Status code:
    - 200: aceptado


- Rechazar petición amistad:
  - Método: POST
  - URL: /api/denyRequest
  - Permisos: TOKEN
  - Petición: 
    HEADER: añadir un campo: key="identificador" y value="tu_nombre"
    BODY: raw + JSON donde pone Text
    {
    "nombre": "NOMBRE" //Nombre del user cuya peticion quieres rechazar
    }

  - Status code:
    - 200: peticion rechazada (ha ido bien)

- Enviar petición amistad:
  - Método: POST
  - URL: /api/sendRequest
  - Permisos: TOKEN
  - Petición: 
    HEADER: añadir un campo: key="identificador" y value="tu_nombre"
    BODY: raw + JSON donde pone Text
    {
    "nombre": "NOMBRE" //Nombre del user al que le envias la peticion
    }

  - Status code:
    - 200: peticion enviada correctamente
    - 208: El usuario al que quieres enviar una metición ya te ha mandado una
    - 417: ya le habías enviado una petición o sois amigos

- Listar peticiones amistad:
  - Método: GET
  - URL: /api/listRequest
  - Permisos: TOKEN
  - Petición: 
    HEADER: añadir un campo: key="identificador" y value="tu_nombre"
    
  - Status code:
    - 200: Todo bien

  - Respuesta: Lista de usuarios que te han mandado petición
    //Formato de un usuario que te manda peticion, devuelve una lista de estos
    "mail": "null",
    "nombre": "NOMBRE",
    "password": "null",
    "token": "null",
    "role": "null"
    

- Listar amigos:
  - Método: GET
  - URL: /api/listFriends
  - Permisos: TOKEN
  - Petición: 
    HEADER: añadir un campo: key="identificador" y value="tu_nombre"
    
  - Status code:
    - 200: Todo bien

  - Respuesta: Lista de usuarios que te han mandado petición
    //Formato de un usuario que es tu amigo, devuelve una lista de estos
    "mail": "null",
    "nombre": "NOMBRE",
    "password": "null",
    "token": "null",
    "role": "null"
  

- Eliminar amigo:
  - Método: POST
  - URL: /api/deleteFriend
  - Permisos: TOKEN
  - Petición: 
    HEADER: añadir un campo: key="identificador" y value="tu_nombre"
    BODY: raw + JSON donde pone Text
    {
    "nombre": "NOMBRE" //Nombre del user que quieres eliminar de amigo
    }

  - Status code:
    - 200: pamigo eliminado (ha ido bien)

- Ver perfil:
  - Método: GET
  - URL: /api/viewProfile
  - Permisos: TOKEN
  - Petición: 
    HEADER: añadir un campo: key="identificador" y value="usuario"

  - Status code:
    - 200: Todo bien
  
  - Respuesta: Ejemplo con usuario ya creado
    {
    "mail": "1@.",
    "nombre": "1",
    "password": "123",
    "token": null,
    "role": "USER",
    "fotPerf": "foto1.png",
    "estrellas": 0,
    "monedas": 0,
    "pDibujo": 0,
    "pListo": 0,
    "pGracioso": 0,
    "nAmigos": 0,
    "amigo": null,
    "peticion": null
    }

-Visualizar/Devolver avatar:
  - Método: GET
  - URL: /api/returnImageProfile/{foto}
    Comentario: {foto} debe ser uno de los avatares de la BBDD devueltos como campo fotoPerf en /viewProfile (petición anterior)
                -foto0.png, foto1.png, ..., foto4.png
  - Permisos: TOKEN
  - Petición: Sin HEADER
  - Respuesta: foto de perfil especificada
  - Status code: 200 OK

-Cambiar avatar:
  - Método: GET
  - URL: /api/changeImageProfile
  - Permisos: TOKEN
  - Petición:
    HEADER: 2 campos
      key="identificador" y value="usuario"
      key="idFoto" y value="foto" (foto0.png, foto1.png, ..., foto4.png)

  - Status code: 200 OK, el avatar ha sido cambiado

# Códigos

200: ok

201: creado

208: El usuario al que quieres enviar una metición ya te ha mandado una

417: contraseña incorrecta (login)
     usuario o email en uso (register)

400: bad request

