docker build --tag=dockerizer-usr .
docker run -it --rm -p 8080:8080 dockerizer-usr:latest
