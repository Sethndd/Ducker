const express = require("express")
const { resolve } = require("path")
const path = require('path')
const { BAD_REQUEST, OK } = require("../util/httpResponseCodes")
const router = express.Router()

const perfilDAO = require(path.join(resolve(__dirname, '..'), 'dataAccess', 'perfilDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))

// Rutas para la entidad Perfil

router.route('/perfil')
    .get(auth.comprobarToken, (req, res) => {
        perfilDAO.obtenerPerfil(req.user.id)
        .then(perfil => {
            res.status(OK).json(perfil)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })
    .patch(auth.comprobarToken, (req, res) => {
        const perfil = req.body
        if (validarPerfil(perfil)) {
            perfilDAO.actualizar( req.user.id, perfil)
            .then(_ => {
                res.status(OK).json({Mensaje : 'Perfil actualizado!'})
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

router.route('/perfil/:id')
    .get(auth.comprobarToken, (req, res) => {
        let idUsuario = req.params.id
        if (idUsuario == 0) {
            idUsuario = req.user.id
        }
        perfilDAO.obtenerPerfil(idUsuario)
        .then(perfil => {
            res.status(OK).json(perfil)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })


function validarPerfil(perfil){
    return perfil.hasOwnProperty('imgRuta')
        && perfil.hasOwnProperty('bannerRuta')
}

module.exports = router