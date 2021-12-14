const express = require("express");
const path = require('path');
const { UNAUTHORIZED, BAD_REQUEST, CREATED, OK, BAD_GATEWAY} = require("../util/httpResponseCodes");

const carpeta = path.resolve(__dirname, '..')
const userController = require(path.join(carpeta,  '/util', 'userController.js'))
const auth = require(path.join(carpeta,  '/util', 'auth.js'))

require(path.join(__dirname, '..', 'util', 'httpResponseCodes.js'))
const router = express.Router();

// Rutas para la funcionalidad Login

router.post('/login', (req, res) => {
    user = req.body
    if (user.hasOwnProperty('correo') && user.hasOwnProperty('contrasena')) {
        userController.validarCredenciales(user.correo, user.contrasena)
        .then(resultado => {
            delete resultado.contrasena;
            auth.firmar(resultado, (err, token) => {
                res.status(OK).json({token})
            })
        })
        .catch(err => {
            res.status(UNAUTHORIZED).json({Mensaje: 'Correo o contraseña incorrectos'})
        })
    }
    else {
        res.status(UNAUTHORIZED).json({Mensaje: 'Datos Faltantes'})
    }
})

router.post('/registrarse', (req, res) => {
    user = req.body
    if (user.hasOwnProperty('correo') && user.hasOwnProperty('contrasena')) {
        userController.registrarUsuario(user)
        .then(respuesta => {
            if (respuesta.Respuesta === 'Correo electrónico ya registrado') {
                res.status(BAD_REQUEST).json(respuesta)
            }
            else {
                res.status(CREATED).json({Mensaje: 'Usuario registrado exitosamente'})
            }
        })
        .catch(err => {
            res.status(BAD_GATEWAY).json(err)
        })
    }
    else {
        res.status(UNAUTHORIZED).json({Mensaje: 'Datos Faltantes'})
    }
})

router.get('/validarauth', auth.comprobarToken, (req, res) => {
    res.status(OK).json({Mensaje: 'Comprobado'})
})

module.exports = router