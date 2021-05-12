


version=$1 



if [ $# -eq 1 ];then


#construimos la app
docker build -f Dockerfile -t proyectokalboware/app:v${version} .


#hacemos push a docker hub
docker push proyectokalboware/app:v${version}


git add .
git commit -m "dockercompose actualizado"
git push
ssh  ${USER}@35.246.75.160 "cd ~/backend/demo;git pull;docker-compose down;docker-compose up"
fi
