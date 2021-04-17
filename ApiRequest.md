
# Peticiones

- Listar usuarios:
  - Método: GET
  - URL: /api/all
  - version: 1.0.0
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
  - version: 1.0.0
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
  - version: 1.0.0
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
  - version: 1.0.0
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
  - version: -
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
  - version: -
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
  - version: -
  - Permisos: TOKEN
  - Petición: 
    HEADER: añadir un campo: key="identificador" y value="tu_nombre"
    BODY: raw + JSON donde pone Text
    {
    "nombre": "NOMBRE" //Nombre del user al que le envias la peticion
    }

  - Status code:
    - 200: peticion enviada correctamente
    - 208: tenías una petición de el usuario al que quieres enviar la peticion, no envías la petición y se añade directamente como amigo 
    - 417: ya le habías enviado una petición o sois amigos
    - 204: no existe el usuario al que quieres enviar la petición

- Listar peticiones amistad:
  - Método: GET
  - URL: /api/listRequest
  - version: -
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
  - version: -
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
  - version: -
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


-Cambiar avatar:
  - Método: GET
  - URL: /api/changeNameProfile
  - Permisos: TOKEN
  - Petición:
    HEADER:
      key="identificador" y value="usuario" //Usuario = nombre viejo
    BODY:
      {
      "usuario": "Nombre" //Nombre nuevo
      }

  - Status code: 200 OK, el nombre ha sido cambiado
                 417 Expectation Failed, el nombre ya está en uso







------------------------
     VERSION 1.3.0
------------------------

-Crear Partida:
  -Método: POST
  - URL: /api/newGame
  - Permisos: TOKEN
  - Petición:
    HEADER:
      key="identificador" y value="usuario" 
    BODY:
      {
      "nombre": "nombredelapartida"
      }

  - Status code: 200 OK, la partida ha sido creada
  - Respuesta: Ejemplo
    {
    "nJugadores_": 1,
    "jugadores_": null,
    "host_": {
        "mail": "1@.",
        "nombre": "1",
        "password": null,
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
        "peticion": null,
        "invitaciones": null,
        "partidas": null,
        "partidasHost": null,
        "respuestas": null
    },
    "estado_": "esperando",
    "hilos_": null,
    "id": 46,
    "nombre": "Prueba"
    }
  

-Listar Partidas
-Método: GET
  - URL: /api/listGames
  - Permisos: TOKEN
  - Petición:
    HEADER:
      key="identificador" y value="usuario" 

  - Status code: 
      200 OK, muestra la lista

  - Respuesta: Ejemplo(lista de 1 sola partida por no poner una ristra en la documentacion)
  [
    {
        "nJugadores_": 2,
        "jugadores_": null,
        "host_": {
            "mail": "A@.",
            "nombre": "A",
            "password": null,
            "token": null,
            "role": "USER",
            "fotPerf": "foto0.png",
            "estrellas": 0,
            "monedas": 0,
            "pDibujo": 0,
            "pListo": 0,
            "pGracioso": 0,
            "nAmigos": 1,
            "amigo": null,
            "peticion": null,
            "partidas": null,
            "partidasHost": null,
            "respuestas": null
        },
        "estado_": "esperando",
        "hilos_": null,
        "id": 49,
        "nombre": "Partida1"
    }
  ]
      
- Enviar invitación a partida:
  - Método: GET
  - URL: /api/inviteGame
  - Permisos: TOKEN
  - Petición: 
    HEADER: 
      key="idPartida" y value="id"
      key="identificador" y value="yo" (persona que invita)  
      key="idInvitado" y value="a quien quieres invitar"

  - Status code:
    - 200: peticion enviada correctamente
    - 417: no se ha podido enviar la petición porque el usuario está invitado / ya está en la partida / no es tu amigo

-Listar invitaciones a partida
  - Método: GET
  - URL: /api/listInvite
  - Permisos: TOKEN
  - Petición: 
    HEADER: key="identificador" y value="tu_nombre"
    
  - Status code:
    - 200: Todo bien

  - Respuesta: Ejemplo con 1 invitacion 
    //Basicamente es quien te ha invitado y la partida pero con solo los campos que interesan
    [
    {
        "id": 54,
        "invitador": {
            "mail": "A@.",
            "nombre": "A",
            "password": null,
            "token": null,
            "role": "USER",
            "fotPerf": "foto0.png",
            "estrellas": 0,
            "monedas": 0,
            "pDibujo": 0,
            "pListo": 0,
            "pGracioso": 0,
            "nAmigos": 1,
            "amigo": null,
            "peticion": null,
            "partidas": null,
            "partidasHost": null,
            "respuestas": null
        },
        "invitado": null, 
        "partida": {
            "nJugadores_": 1,
            "jugadores_": null,
            "host_": {
                "mail": "A@.",
                "nombre": "A",
                "password": null,
                "token": null,
                "role": "USER",
                "fotPerf": "foto0.png",
                "estrellas": 0,
                "monedas": 0,
                "pDibujo": 0,
                "pListo": 0,
                "pGracioso": 0,
                "nAmigos": 1,
                "amigo": null,
                "peticion": null,
                "partidas": null,
                "partidasHost": null,
                "respuestas": null
            },
            "estado_": "esperando",
            "hilos_": null,
            "id": 49,
            "nombre": "Partida1"
        }
    }
    ]

-Unirse a Partida/Aceptar invitacion:
  -Método: GET
  - URL: /api/acceptInvite
  - Permisos: TOKEN
  - Petición:
    HEADER:
      key="identificador" y value="usuario" 
      key="idPartida" y value="id"

  - Status code: 
      200 OK, te has unido a la partida
      417 Expectation Failed (3 opciones, sale en terminal)
          -La partida no existe o ya ha empezado
          -Ya estabas en la partida
          -No cabes en la partida (se ha alcanzado el max. de jugadores)
  - Respuesta: te devuelve la partida con el nuevo jugador
    {
    "nJugadores_": 2,
    "jugadores_": null,
    "host_": {
        "mail": "A@.",
        "nombre": "A",
        "password": null,
        "token": null,
        "role": "USER",
        "fotPerf": "foto0.png",
        "estrellas": 0,
        "monedas": 0,
        "pDibujo": 0,
        "pListo": 0,
        "pGracioso": 0,
        "nAmigos": 1,
        "amigo": null,
        "peticion": null,
        "partidas": null,
        "partidasHost": null,
        "respuestas": null
    },
    "estado_": "esperando",
    "hilos_": null,
    "id": 49,
    "nombre": "Partida1"
    }


- Rechazar invitación a partida:
  - Método: GET
  - URL: /api/denyInvite
  - Permisos: TOKEN
  - Petición: 
    HEADER: añadir un campo: key="identificador" y value="quien eres" y campo: key="idPartida" y value="id"
    

  - Status code:
    - 200: se ha rechazado la invitación correctamente



-----------------
  VERSION 1.3.1
-----------------
  -EN ESTA VERSIÓN SE AÑADE LA POSIBILIDAD DE INICIAR UNA PARTIDA Y ENVIAR RESPUESTAS A LA MISMA
  
  - Rechazar invitación a partida (debes ser el host):
    - Método: GET
    - URL: /api/startGame
    - Permisos: TOKEN
    - Petición: 
      HEADER:
        key="identificador" y value="quien eres" 
        key="idPartida" y value="id"
    - Status code:
      - 200: se ha iniciado la partida 
      - 503: la partida ya está empezada
      - 417: no eres el host y por tanto no tienes permiso para iniciarla

  - Enviar respuesta (dibujo o frase):
    - Método: POST
    - URL: /api/addRespuesta
    - Permisos: TOKEN
    - Petición: 
      HEADER:
        key="idPartida" y value="id"
        key="autor" y value="tu_nombre"
      BODY: raw + JSON donde pone Text
        {
        "contenido": [0,1,0,...] //Sea frase o dibujo es un array of bytes
        }
    -Status code:
      - 200: se ha registrado la respuesta correctamente
      - 417: el jugador no pertenece a la partida o ya ha jugado en ese turno (debe esperar a a que los demas jueguen)
      
      


 







