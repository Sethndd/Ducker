const express = require('express');
const path = require('path');

const app = express();

app.set('port', process.env.PORT || 1806);

//Middleware
app.use(express.json());

//Routes
const routes = path.join(__dirname, '/routes')
app.use(require(path.join(routes, 'login.js')))
app.use(require(path.join(routes, 'users.js')))


//Starting app
app.listen(app.get('port'), () => {
    console.log('Server listening port', app.get('port'))
});