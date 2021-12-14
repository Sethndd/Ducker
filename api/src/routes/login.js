const express = require("express");
const path = require('path');
const { UNAUTHORIZED, BAD_REQUEST, CREATED, OK, BAD_GATEWAY, FORBIDEN} = require("../util/httpResponseCodes");

const carpeta = path.resolve(__dirname, '..')
const userController = require(path.join(carpeta,  '/util', 'userController.js'))
const auth = require(path.join(carpeta,  '/util', 'auth.js'))
const usuarioDAO = require(path.join(carpeta,  '/dataAccess', 'usuarioDAO.js'))

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
            if (respuesta.Respuesta === 'Correo electrónico ya registrado' || respuesta.Respuesta === 'nombre de usuario ya registrado') {
                res.status(BAD_REQUEST).json(respuesta)
            }
            else{
                res.status(OK).json({Mensaje: 'Usuario registrado exitosamente'})
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

router.post('/administrador/:id', auth.comprobarToken, (req, res) => {
    if(req.user.tipo === 'alpha'){
        usuarioDAO.crearAdmin(req.params.id)
        .then(
            res.status(OK).json({Mensje: 'Se ha actualizado correctamente el usuario'})
        )
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    }
    else{
        res.status(FORBIDEN).json({Mensje: 'No cuenta con los derechos para llevar a cabo esta acción'})
    }
});

router.get('/validarToken', auth.comprobarToken, (req, res) => {
    usuarioDAO.obtener(req.user.id)
    .then(usuario => {
        if(usuario.estado === 'activo') {
            res.status(OK).json({Mensaje: 'Comprobado'})
        }
        else{
            res.status(FORBIDEN).json({Mensaje: 'Usuario no valido'})
        }
    })
    .catch(err => {
        console.log(err)
        res.status(BAD_REQUEST).json(err)
    })
});

module.exports = router