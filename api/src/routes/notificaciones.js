const express = require("express");
const path = require('path');
const { BAD_REQUEST, CREATED, OK } = require("../util/httpResponseCodes");
const router = express.Router();

const notificacionDAO = require(path.join(path.resolve(__dirname, '..'), 'dataAccess', 'notificacionDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))

require(path.join(__dirname, '..', 'util', 'httpResponseCodes.js'))

router.route('/notificaciones')
    .post(auth.comprobarToken, (req, res) => {
        const notificacion = req.body
        if (validarNotificacion(notificacion)) {
            notificacionDAO.crear(notificacion)
            .then(respuesta => {
                res.status(CREATED).json({Mensaje : 'Notificacion creada!'})
            })
            .catch(err => {
                console.log(err)
                res.status(BAD_REQUEST).json(err)
            })
        }
        else {
            res.status(BAD_REQUEST).json({Mensaje : 'Faltan Datos'})
        }
    })
    .get(auth.comprobarToken, (req, res) => {
        const idUsuario = req.user.id
        notificacionDAO.obtenerPorUsuario(idUsuario)
        .then(notificacion => {
            res.status(OK).json(notificacion)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })
    
router.route('/notificaciones/:id')
    .patch(auth.comprobarToken, (req, res) => {
        const id = req.params.id
        notificacionDAO.visto(id)
        .then(respuesta => {
            res.status(OK).json({Mensaje : "Notificacion actualizada"})
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

function validarNotificacion(notificacion) {
    var result = false
    if(notificacion.hasOwnProperty('idUsuario')
    && notificacion.hasOwnProperty('evento')
    && notificacion.hasOwnProperty('enlace')
    && notificacion.hasOwnProperty('visto')) {
        result = true
    }

    return result
}

module.exports = router