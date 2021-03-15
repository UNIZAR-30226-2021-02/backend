

listar usuarios:
(/api/all) HACE FALTA TOKEN DE SESION O DEVUELVE 403
Respuesta:
LISTA DE:
mail:"mail"
nombre:"nombre"
password:"password"


registrar usuario:
(/api/register)
Body:
mail:"correo"
nombre:"nombre"
password:"password"

Si registro correcto:
codigo 201 y token:"token" (Devuelve todo el usuario, entre ellos el campo token)

Si registro incorrecto:
codigo 417


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
