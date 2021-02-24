Para obtener un nuevo .jar para el docker hacer click derecho, run as, maven install
Despues de esto tendremos el .jar compilado en la ruta introducida en el fichero Dockerfile


Para configurar el docker:
En la terminal con docker instalado y en la raiz de nuestro proyecto

- docker build -f Dockerfile -t dockerdemo .
- docker run dockerdemo

El proyecto se compila con java 15
