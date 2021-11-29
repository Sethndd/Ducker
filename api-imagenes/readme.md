#Node Commands
npm install express cors multer
npm install -g nodemon

#Docker commands
docker build . -t ducker-img-api
docker run --name 'ducker-img-api' -p 1807:1807 -d ducker-img-api