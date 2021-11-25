const express = require("express");
const path = require('path');
const router = express.Router();

const notificacionDAO = require(path.join(path.resolve(__dirname, '..'), 'dataAccess', 'notificacionDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))

router.route('/notificaciones')
    .post(auth.comprobarToken, (req, res) => {
        const notificacion = req.body
        if (validarNotificacion(notificacion)) {
            notificacionDAO.crear(notificacion, (err, respuesta) => {
                if (err) {
                    console.log(err)
                    res.status(400).json(err)
                    return
                }
                res.status(201).json({Mensaje : 'Notificacion creada!'})
            })
        }
        else {
            res.status(400).json({Mensaje : 'Faltan Datos'})
        }
    })

router.route('/notificaciones/:idUsuario')
    .get(auth.comprobarToken, (req, res) => {
        const idUsuario = req.params.idUsuario
        notificacionDAO.obtenerPorUsuario(idUsuario, (err, respuesta) => {
            if (err) {
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })
    
router.route('/notificaciones/:id')
    .patch(auth.comprobarToken, (req, res) => {
        const id = req.params.id
        notificacionDAO.visto(id, (err, respuesta) => {
            if (err) {
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json({Mensaje : "Notificacion actualizada"})
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