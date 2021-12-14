const express = require("express");
const path = require('path');
const { BAD_REQUEST, CREATED, OK } = require("../util/httpResponseCodes");
const router = express.Router();

const usuarioDAO = require(path.join(path.resolve(__dirname, '..'), 'dataAccess', 'usuarioDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))

// Rutas para la entidad Usuario

let ID_VACIO = 0

router.route('/usuarios')
    .get(auth.comprobarToken, (req, res) => {
        usuarioDAO.obtenerTodos()
        .then(usuarios => {
            res.status(OK).json(usuarios)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
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

router.route('/usuarios/:id')
    .get(auth.comprobarToken, (req, res) => {
        if (req.params.id == ID_VACIO || req.params.id == req.user.id) {
            usuarioDAO.obtener(req.user.id)
            .then(usuario => {
                usuario.id = ID_VACIO
                res.status(OK).json(usuario)
            })
            .catch(err => {
                console.log(err)
                res.status(BAD_REQUEST).json(err)
            })
        }
        else {
            usuarioDAO.obtener(req.params.id)
            .then(usuario => {
                res.status(OK).json(usuario)
            })
            .catch(err => {
                console.log(err)
                res.status(BAD_REQUEST).json(err)
            })
        }
    })
    .patch(auth.comprobarToken, (req, res) => {
        const usuario = req.body
        if (usuarioValido(usuario) ){
            usuarioDAO.actualizar(req.user.id, usuario)
            .then(_ => {
                res.status(OK).json({ Mensaje : 'Usuario actualizado'})
            })
            .catch(err => {
                console.log(err)
                res.status(BAD_REQUEST).json(err)
            })
        }
        else {
            res.status(BAD_REQUEST).json({Mensaje : 'Faltan datos'})
        }
    })
    .delete(auth.comprobarToken, (req, res) => {
        usuarioDAO.eliminar(req.user.id)
        .then(_ => {
            res.status(OK).json({ Mensaje : 'Usuario eliminado'})
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

function usuarioValido(usuario){
    return usuario.hasOwnProperty('correo')
        && usuario.hasOwnProperty('nombrePropio')
        && usuario.hasOwnProperty('nombreUsuario')
        && usuario.hasOwnProperty('fechaNacimiento')
        && usuario.hasOwnProperty('estado')
        && usuario.hasOwnProperty('tipo')
}

module.exports = router