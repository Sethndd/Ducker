const express = require("express");
const path = require('path');

const carpeta = path.resolve(__dirname, '..')
const userController = require(path.join(carpeta,  '/util', 'userController.js'))
const auth = require(path.join(carpeta,  '/util', 'auth.js'))
const usuarioDAO = require(path.join(carpeta,  '/dataAccess', 'usuarioDAO.js'))

const router = express.Router();

router.post('/login', (req, res) =>{
    user = req.body

    if(user.hasOwnProperty('correo') && user.hasOwnProperty('contrasena')){
        userController.validarCredenciales(user.correo, user.contrasena, (err, resultado) =>{
            if(resultado){
                delete resultado.contrasena;
                // auth.sign(user, '120s', (err, token) => {
                auth.firmar(resultado, (err, token) => {
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

router.post('/registrarse', (req, res) =>{
    user = req.body

    if(user.hasOwnProperty('correo') && user.hasOwnProperty('contrasena')){
        userController.registrarUsuario(user, (err, respuesta) =>{
            if(err){
                res.status(502).json(err)
            }
            else{
                if(respuesta.Respuesta === 'Correo electrónico ya registrado' || respuesta.Respuesta === 'nombre de usuario ya registrado'){
                    res.status(400).json(respuesta)
                }
                else{
                    res.status(200).json({Mensaje: 'Usuario registrado exitosamente'})
                }
            }
        })
    }
    else{
        res.status(401).json({Mensaje: 'Datos Faltantes'})
    }
});

router.post('/administrador/:id', auth.comprobarToken, (req, res) =>{
    if(req.user.tipo === 'alpha'){
        usuarioDAO.crearAdmin(req.params.id)
        .then(
            res.status(200).json({Mensje: 'Se ha actualizado correctamente el usuario'})
        )
        .catch(err => {
            console.log(err)
            res.status(400).json(err)
        })
    }
    else{
        res.status(403).json({Mensje: 'No cuenta con los derechos para llevar a cabo esta acción'})
    }
});

router.get('/validarToken', auth.comprobarToken, (req, res) =>{
    usuarioDAO.obtener(req.user.id)
    .then(usuario =>{
        if(usuario.estado === 'activo'){
            res.status(200).json({Mensaje: 'Comprobado'})
        }
        else{
            res.status(403).json({Mensaje: 'Usuario no valido'})
        }
    })
    .catch(err => {
        console.log(err)
        res.status(400).json(err)
    })
});

module.exports = router