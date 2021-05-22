
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
    "mail": "mail",
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
    HEADER: añadir un campo: key="identificador" y value="tu_mail"
    BODY: raw + JSON donde pone Text
    {
    "mail": "mail" //mail del user cuya petición quieres aceptar
    }

  - Status code:
    - 200: aceptado


- Rechazar petición amistad:
  - Método: POST
  - URL: /api/denyRequest
  - version: -
  - Permisos: TOKEN
  - Petición: 
    HEADER: añadir un campo: key="identificador" y value="tu_mail"
    BODY: raw + JSON donde pone Text
    {
    "mail": "mail" //mail del user cuya petición quieres rechazar
    }

  - Status code:
    - 200: peticion rechazada (ha ido bien)

- Enviar petición amistad:
  - Método: POST
  - URL: /api/sendRequest
  - version: -
  - Permisos: TOKEN
  - Petición: 
    HEADER: añadir un campo: key="identificador" y value="tu_mail"
    BODY: raw + JSON donde pone Text
    {
    "mail": "mail" //Mail del user al que le envias la peticion
    }

  - Status code:
    - 200: peticion enviada correctamente
    - 208: tenías una petición de el usuario al que quieres enviar la peticion, no envías la petición y se añade directamente como amigo 
    - 417: ya le habías enviado una petición o sois amigos
    - 405: no existe el usuario al que quieres enviar la petición

- Listar peticiones amistad:
  - Método: GET
  - URL: /api/listRequest
  - version: -
  - Permisos: TOKEN
  - Petición: 
    HEADER: añadir un campo: key="identificador" y value="tu_mail"
    
  - Status code:
    - 200: Todo bien

  - Respuesta: Lista de usuarios que te han mandado petición
    //Formato de un usuario que te manda peticion, devuelve una lista de estos
    "mail": "MAIL",
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
    HEADER: añadir un campo: key="identificador" y value="tu_mail"
    
  - Status code:
    - 200: Todo bien

  - Respuesta: Lista de usuarios que te han mandado petición
    //Formato de un usuario que es tu amigo, devuelve una lista de estos
    "mail": "MAIL",
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
    HEADER: añadir un campo: key="identificador" y value="tu_mail"
    BODY: raw + JSON donde pone Text
    {
    "mail": "mail" //mail del user que quieres eliminar de amigo
    }

  - Status code:
    - 200: pamigo eliminado (ha ido bien)

- Ver perfil:
  - Método: GET
  - URL: /api/viewProfile
  - Permisos: TOKEN
  - Petición: 
    HEADER: añadir un campo: key="identificador" y value="tu_mail"

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
      key="identificador" y value="mail"
      key="idFoto" y value="foto" (foto0.png, foto1.png, ..., foto4.png)

  - Status code: 200 OK, el avatar ha sido cambiado


-Cambiar nombre:
  - Método: GET
  - URL: /api/changeNameProfile
  - Permisos: TOKEN
  - Petición:
    HEADER:
      key="identificador" y value="mail" 
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
      key="identificador" y value="mail" 
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
      key="identificador" y value="mail" 

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
      key="identificador" y value="mi_mail" (persona que invita)  
      key="idInvitado" y value="mail_invitado"

  - Status code:
    - 200: peticion enviada correctamente
    - 417: no se ha podido enviar la petición porque el usuario está invitado / ya está en la partida / no es tu amigo

-Listar invitaciones a partida
  - Método: GET
  - URL: /api/listInvite
  - Permisos: TOKEN
  - Petición: 
    HEADER: key="identificador" y value="mail"
    
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
      key="identificador" y value="mail" 
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
    HEADER: añadir un campo: key="identificador" y value="tu_mail" y campo: key="idPartida" y value="id"

  - Status code:
    - 200: se ha rechazado la invitación correctamente



-----------------
  VERSION 1.3.1
-----------------
  -EN ESTA VERSIÓN SE AÑADE LA POSIBILIDAD DE INICIAR UNA PARTIDA Y ENVIAR RESPUESTAS A LA MISMA
  
  - Empezar partida(debes ser el host):
    - Método: GET
    - URL: /api/startGame
    - Permisos: TOKEN
    - Petición: 
      HEADER:
        key="identificador" y value="quien eres" 
        key="idPartida" y value="mail"
    - Status code:
      - 200: se ha iniciado la partida 
      - 503: la partida ya está empezada
      - 417: no eres el host y por tanto no tienes permiso para iniciarla


-----------------
  VERSION 1.4.0
-----------------
 EN ESTA VERSIÓN SE AÑADE LA LÓGICA DE LOS TURNOS Y FINALIZAR LAS PARTIDAS CUANDO SE PASAN TODOS LOS TURNOS. 
 


- Listar amigos que no estén en tu partida:
    - Método: GET
    - URL: /api/listFriendsGame
    - Permisos: TOKEN
    - Petición: 
      HEADER:
        key="idPartida" y value="id"
        key="identificador" y value="mail"
      
      
      
     - Respuesta: te devuelve la lista de amigos que no están en partida
   [ {
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
    }]
    -Status code:
      - 200: se lista correctamente
      



- Listar jugadores en tu partida:
    - Método: GET
    - URL: /api/listPlayers
    - Permisos: TOKEN
    - Petición: 
      HEADER:
        key="idPartida" y value="id"
        key="identificador" y value="mail"
      
     - Respuesta: te devuelve lista de jugadores en la partida
        (No la ponemos pero es como una lista de usuarios cualquiera, como las de listFriendsGame o listFriends)
    -Status code:
      - 200: se lista correctamente


-----------------
  VERSION 1.5.0
-----------------
      

- Devolver la respuesta sobre la que te toca dibujar o escribir:
    - Método: GET
    - URL: /api/returnResponse
    - Permisos: TOKEN
    - Petición: 
      HEADER:
        key="idPartida" y value="id"
        key="identificador" y value="mail"
      
    - Respuesta: te devuelve la respuesta
      Ejemplo para que veais los campos 
    {
    "id_": -3,
    "autor_": null,
    "dibujo": null,
    "esDibujo": false,
    "frase": null
    }

    EXPLICACIÓN: ahora devolvemos siempre 200 y una respuesta, pero hay que mirar los campos para saber qué está pasando.
    
      -id: codifica el caso
          Error/caso especial:
        -3: partida acabada
        -2: ya has jugado el turno
        -1: es el primer turno y no hay respuesta previa (toca /addText)
        -Caso normal: contiene el identifiacdor de la respuesta (será positivo y os sirve para luego pedir la foto)
      -autor: os lo devolvemos siempre a null (no os interesa xd)
      -esDibujo: es un boleano que indica si la id de la respuesta se corresponde con un dibujo/foto o no
      -frase: valdrá null si esDibujo es true, y contendrá la frase en cuestión cuando la respuesta no sea un dibujo 



- Devolver la imagen sobre la que te toca escribir, si el returnResponse anterior ha tenido el booleano de esDibujo a true:
    - Método: GET
    - URL: /api/returnImageResponse/id(es el id que te devuelve returnResponse, ejemplo: /api/returnImageResponse/157)

    - Permisos: TOKEN
    - Petición: ni headers ni body
      
     - Respuesta: te devuelve la imagen
    
    -Status code:
      - 200: se devuelve correctamente


- Enviar texto como respuesta:
    - Método: POST
    - URL: /api/addText
    - Permisos: TOKEN
    - Petición: 
      HEADER:
        key="idPartida" y value="id"
        key="autor" y value="tu_mail"
      
      BODY:
      contenido(meter el string sin mas, ni nombre de parametro ni nada)
      
    -Status code:
      - 200: se añade correctamente
      - 417: no se ha podido añadir porque ya has jugado tu turno o porque la partida ya está acabada


- Enviar imagen como respuesta:
    - Método: POST
    - URL: /api/addImage
    - Permisos: TOKEN
    - Petición: 
      HEADER:
        key="idPartida" y value="id"
        key="autor" y value="tu_mail"
      
      BODY:
      contenido: Esto es un MultiPartFile, no se como me lo enviais la verdad
      
    -Status code:
      - 200: se añade correctamente
      - 417: no se ha podido añadir porque ya has jugado tu turno o porque la partida ya está acabada


      
-----------------
  VERSION 1.6.0
-----------------
-Añadida documentación sobre la petrición listPlayers (se nos había colado) y arregladas pequeñas erratas.

-CAMBIOS EN LAS PETICIONES PREVIAS (NOMBRE -> MAIL)
    -Para ahorraros leer todas las peticiones otra vez las reglas de cambio son:
      -En el header identificador cambiar el valor que le dais, en lugar del nombre ahora es el mail.
      -Si teneis que construir un body para representar/identificar a otro usuario cambiar el campo: "nombre":"el_nombre" por "mail":"el_mail"

-La petición /addRespuesta ya no existe, ahora se usa addText o addImage, de modo que en el returnResponse ya os decimos que tipo de mensaje os ha llegado, para que envieis el que toque. Revisar la peetición de returnResponse que la hemos cambiado para esto.

-PETICIÓN PARA DEVOLVER TODAS LAS RESPUESTAS AL FINAL DE LA PARTIDA

-No se si funcionará correctamente, pero he guardado las peticiones de postman con las que hemos probado el backend en una collección que podeis importar con este link: https://www.getpostman.com/collections/5985767f9f78ff6392f1

PD: me siento como el de riot cada vez que sacan parche, y ahora vamos con los platos fuertes de esta versión xd.



- Devolver todas las respuestas de una partida:
    - Método: GET
    - URL: /api/returnAllResponses
    - Permisos: TOKEN
    - Petición: 
      HEADER:
        key="idPartida" y value="id"   
      
     - Respuesta: devuelve una lista de hilos (que a su vez son listas de respuestas). Ejemplo:
      [
    {
        "id_": 186,
        "respuestas_": [
            {
                "id_": 191,
                "autor_": {
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
                    "nAmigos": 2,
                    "amigo": null,
                    "peticion": null,
                    "partidas": null,
                    "partidasHost": null,
                    "respuestas": null
                },
                "dibujo": null,
                "esDibujo": false,
                "frase": "inicialA"
            },
            {
                "id_": 193,
                "autor_": {
                    "mail": "B@.",
                    "nombre": "B",
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
                "dibujo": null,
                "esDibujo": true,
                "frase": null
            },
            {
                "id_": 197,
                "autor_": {
                    "mail": "C@.",
                    "nombre": "C",
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
                "dibujo": null,
                "esDibujo": false,
                "frase": "FINALC"
            }
        ],
        "jugadorInicial_": null,
        "partida_": null,
        "size": 3,
        "jugadorInicial": null
    },
    {
        "id_": 187,
        "respuestas_": [
            {
                "id_": 189,
                "autor_": {
                    "mail": "C@.",
                    "nombre": "C",
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
                "dibujo": null,
                "esDibujo": false,
                "frase": "inicialC"
            },
            {
                "id_": 192,
                "autor_": {
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
                    "nAmigos": 2,
                    "amigo": null,
                    "peticion": null,
                    "partidas": null,
                    "partidasHost": null,
                    "respuestas": null
                },
                "dibujo": null,
                "esDibujo": true,
                "frase": null
            },
            {
                "id_": 196,
                "autor_": {
                    "mail": "B@.",
                    "nombre": "B",
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
                "dibujo": null,
                "esDibujo": false,
                "frase": "FINALB"
            }
        ],
        "jugadorInicial_": null,
        "partida_": null,
        "size": 3,
        "jugadorInicial": null
    },
    {
        "id_": 188,
        "respuestas_": [
            {
                "id_": 190,
                "autor_": {
                    "mail": "B@.",
                    "nombre": "B",
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
                "dibujo": null,
                "esDibujo": false,
                "frase": "inicialB"
            },
            {
                "id_": 194,
                "autor_": {
                    "mail": "C@.",
                    "nombre": "C",
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
                "dibujo": null,
                "esDibujo": true,
                "frase": null
            },
            {
                "id_": 195,
                "autor_": {
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
                    "nAmigos": 2,
                    "amigo": null,
                    "peticion": null,
                    "partidas": null,
                    "partidasHost": null,
                    "respuestas": null
                },
                "dibujo": null,
                "esDibujo": false,
                "frase": "FINALA"
            }
        ],
        "jugadorInicial_": null,
        "partida_": null,
        "size": 3,
        "jugadorInicial": null
    }
]
   
    -Status code:
      - 200: se lista correctamente



-----------------
  VERSION 1.6.1
-----------------

- Se ha creado una petición para web para enviar el dibujo

- Enviar imagen como respuesta:
    - Método: POST
    - URL: /api/addImage2
    - Permisos: TOKEN
    - Petición: 
      HEADER:
        key="idPartida" y value="id"
        key="autor" y value="tu_mail"
      
      BODY:
      contenido: Esto es el String que contiene la imagen.
      
    -Status code:
      - 200: se añade correctamente
      - 417: no se ha podido añadir porque ya has jugado tu turno o porque la partida ya está acabada

      
