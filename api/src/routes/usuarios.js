const express = require("express");
const path = require('path');
const router = express.Router();

const usuarioDAO = require(path.join(path.resolve(__dirname, '..'), 'dataAccess', 'usuarioDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))

router.route('/usuarios')
    .get(auth.comprobarToken, (req, res) =>{
        usuarioDAO.obtenerTodos()
        .then(usuarios =>{
            res.status(200).json(usuarios)
        })
        .catch(err => {
            console.log(err)
            res.status(400).json(err)
        })
    })

router.route('/usuarios/:id')
    .get(auth.comprobarToken, (req, res) =>{
        if(req.params.id == 0 || req.params.id == req.user.id){
            usuarioDAO.obtener(req.user.id)
            .then(usuario =>{
                usuario.id = 0
                res.status(200).json(usuario)
            })
            .catch(err => {
                console.log(err)
                res.status(400).json(err)
            })
        }
        else{
            usuarioDAO.obtener(req.params.id)
            .then(usuario =>{
                res.status(200).json(usuario)
            })
            .catch(err => {
                console.log(err)
                res.status(400).json(err)
            })
        }
    })
    .patch(auth.comprobarToken, (req, res) =>{
        const usuario = req.body
        if(usuarioValido(usuario)){
            usuarioDAO.actualizar(req.user.id, usuario)
            .then(respuesta => {
                res.status(200).json({ Mensaje : 'Usuario actualizado'})
            })
            .catch(err => {
                console.log(err)
                res.status(400).json(err)
            })
        }
        else{
            res.status(400).json({Mensaje : 'Faltan datos'})
        }
    })
    .delete(auth.comprobarToken, (req, res) =>{
        usuarioDAO.eliminar(req.user.id)
        .then(respuesta => {
            res.status(200).json({ Mensaje : 'Usuario eliminado'})
        })
        .catch(err => {
            console.log(err)
            res.status(400).json(err)
        })
    })

function usuarioValido(usuario){
    if(usuario.hasOwnProperty('correo')
    && usuario.hasOwnProperty('nombrePropio')
    && usuario.hasOwnProperty('nombreUsuario')
    && usuario.hasOwnProperty('fechaNacimiento')
    && usuario.hasOwnProperty('estado')
    && usuario.hasOwnProperty('tipo')){
        return true
    }
    return false
}

module.exports = router