const express = require('express');
const path = require('path');
const cors = require('cors');

const app = express();

app.set('port', process.env.PORT || 1806);

//Middleware
app.use(express.json());
app.use(cors());

//Routes
const rutas = path.join(__dirname, '/routes')
app.use(require(path.join(rutas, 'login.js')))
app.use(require(path.join(rutas, 'quack.js')))


//Starting app
app.listen(app.get('port'), () => {
    console.log('Server listening port', app.get('port'))
});