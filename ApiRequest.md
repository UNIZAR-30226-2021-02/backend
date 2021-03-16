
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
    "mail": <mail>,
    "nombre": <nombre>,
    "password": <pswd>,
    "token": null, //No vale para nade
    "role": "USER"
    }


- Login:
  - Método: POST
  - URL: /api/login
  - Permisos: NADA
  - Petición: (body: raw + JSON donde pone Text)
    {
    "nombre": "<user>",
    "password": "<pswd>"
    }

  - Status code:
    - 200: correcto
    - 400: usuario incorrecto
    - 417: password incorrecto

  - Respuesta: Usuario con el token de sesión
    "mail": null,
    "nombre": "<user>",
    "password": "<pswd>",
    "token": "<TOKEN>",
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
    "mail": "<mail>"
    "nombre": "<name>",
    "password": "<pswd>"
    }

  - Status code:
    - 201: creado
    - 417: usuario o mail existen

  - Respuesta: Usuario con el token de sesión
    "mail": "<mail>",
    "nombre": "<name>",
    "password": "<pswd>",
    "token": "<TOKEN>",
    "role": "USER"

  - Si registro correcto:
      codigo 201(CREATED) y token vale una cosa mu larga
  - Si registro incorrecto:
      codigo 417 (El usuario o el mail ya estan en uso)

# Códigos

200: ok

201: creado

417: contraseña incorrecta (login)
     usuario o email en uso (register)

400: bad request

