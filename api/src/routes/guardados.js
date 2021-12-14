const express = require("express")
const { resolve } = require("path")
const path = require('path')
const { CREATED, BAD_REQUEST, OK } = require("../util/httpResponseCodes")
const router = express.Router()

const guardadosDAO = require(path.join(resolve(__dirname, '..'), 'dataAccess', 'guardadosDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))

// Rutas para la entidad Guardado

router.route('/guardados')
    .post(auth.comprobarToken, (req, res) => {
        let idQuack = req.body
        if (validarIdQuack(idQuack)) {
            guardadosDAO.crear(idQuack.idQuack, req.user.id)
            .then(_ => {
                res.status(CREATED).json({Mensaje : 'Quack guardado!'})
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

    .get(auth.comprobarToken, (req, res) => { 
        guardadosDAO.obtenerTodosGuardados(req.user.id)
        .then(respuesta => {
            res.status(OK).json(respuesta)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/guardados/:id')
    .delete(auth.comprobarToken, (req, res) => { 
        guardadosDAO.eliminar([req.params.id])
        .then(_ => {
            res.status(OK).json({Mensaje: 'Eliminado'})
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    });

function validarIdQuack(idQuack){
    return idQuack.hasOwnProperty('idQuack')
}

module.exports = router