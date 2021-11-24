const express = require("express");
const path = require('path');

const parentFolder = path.resolve(__dirname, '..')
const userController = require(path.join(parentFolder,  '/dataaccess', 'userController.js'))
const auth = require(path.join(parentFolder,  '/util', 'auth.js'))

const router = express.Router();

router.post('/login', (req, res) =>{
    user = req.body

    if(user.hasOwnProperty('correo') && user.hasOwnProperty('contrasena')){
        userController.validarCredenciales(user.correo, user.contrasena, (err, resultado) =>{
            if(resultado){
                delete resultado.contrasena;
                // auth.sign(user, 'password', '120s', (err, token) => {
                auth.firmar(resultado, 'password', (err, token) => {
                    res.status(200).json({token})
                })
            }
            else{
                res.status(401).json({Mensaje: 'Correo o contraseña incorrectos'})
            }
        })
    }
    else{
        res.status(401).json({Mensaje: 'Datos Faltantes'})
    }
});

router.post('/register', (req, res) =>{
    user = req.body

    if(user.hasOwnProperty('correo') && user.hasOwnProperty('contrasena')){
        userController.registrarUsuario(user, (err, respuesta) =>{
            if(err){
                res.status(502).json(err)
            }
            else{
                res.status(200).json(respuesta)
            }
        })
    }
    else{
        res.status(401).json({Mensaje: 'Datos Faltantes'})
    }
});

router.get('/usersAuth', auth.comprobarToken, (req, res) =>{
    res.json(req.user)
});

module.exports = router