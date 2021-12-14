const express = require('express');
const path = require('path');
const cors = require('cors');

const app = express();

let DEFAULT_PORT = 1806

app.set('port', process.env.PORT || DEFAULT_PORT);



//Middleware
app.use(express.json());
app.use(cors());

//Rutas
const rutas = path.join(__dirname, '/routes')
app.use(require(path.join(rutas, 'guardados.js')))
app.use(require(path.join(rutas, 'hashtag.js')))
app.use(require(path.join(rutas, 'imagenes.js')))
app.use(require(path.join(rutas, 'likes.js')))
app.use(require(path.join(rutas, 'login.js')))
app.use(require(path.join(rutas, 'notificaciones.js')))
app.use(require(path.join(rutas, 'perfiles.js')))
app.use(require(path.join(rutas, 'quack.js')))
app.use(require(path.join(rutas, 'seguidos.js')))
app.use(require(path.join(rutas, 'usuarios.js')))
app.use('/imagenes', express.static(path.join(__dirname, '/imagenes')));

//Manejo de errores
errores = require(path.join(__dirname, '/util', 'manejoErrores.js'))
app.use(errores.errorMulter);

//Comenzando app:
app.listen(app.get('port'), () => {
    console.log('Server listening port', app.get('port'))
});