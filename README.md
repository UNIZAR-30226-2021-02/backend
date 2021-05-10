# backend


![tenor (1)](https://user-images.githubusercontent.com/46299278/117655619-99983800-b197-11eb-892f-2d6c95a54e3f.gif)



Para obtener un nuevo .jar para el docker hacer click derecho, run as, maven install
Despues de esto tendremos el .jar compilado en la ruta introducida en el fichero Dockerfile

Para configurar el docker:
En la terminal con docker instalado y en la raiz de nuestro proyecto

- docker build -f Dockerfile -t dockerdemo .
- docker run dockerdemo

Para crear el contenedor usar script docker_build.sh

Para ejecutar el contenedor usar script docker_run.sh

El proyecto se compila con java 15 (JDK 15)

POSTGRES 13.2
