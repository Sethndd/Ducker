#Node Commands
npm install express cors mysql jsonwebtoken bcrypt 
npm install -g nodemon

#Docker commands
docker build . -t ducker-api
docker run --name 'ducker-api' -p 1806:1806 -d ducker-api