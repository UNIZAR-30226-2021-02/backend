


version=$1 



if [ $# -eq 1 ];then


#construimos la app
docker build -f Dockerfile -t proyectokalboware/app:v${version} .


#hacemos push a docker hub
docker push proyectokalboware/app:v${version}
fi
