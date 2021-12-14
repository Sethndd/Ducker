const express = require("express")
const { resolve } = require("path")
const path = require('path')
const { BAD_REQUEST, CREATED, OK } = require("../util/httpResponseCodes")
const router = express.Router()

const seguidosDAO = require(path.join(resolve(__dirname, '..'), 'dataAccess', 'seguidosDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))

// Rutas para la entidad Seguido

router.route('/seguir/:id')
    .post(auth.comprobarToken, (req, res) => {
        seguidosDAO.crearSeguidos(req.user.id, req.params.id)
        .then(respuesta => {
            res.status(CREATED).json(respuesta)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/seguidores/:id')
    .get(auth.comprobarToken, (req, res) => {
        var id = req.params.id

        if (id == 0) {
            id = req.user.id
        }

        seguidosDAO.obtenerSeguidores(id)
        .then(seguidores => {
            res.status(OK).json(seguidores)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/seguidos/:id')
    .get(auth.comprobarToken, (req, res) => {
        var id = req.params.id

        if (id == 0) {
            id = req.user.id
        }
        seguidosDAO.obtenerSeguidos(id)
        .then(seguidos => {
            res.status(OK).json(seguidos)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/seguidores/cantidad/:id')
    .get(auth.comprobarToken, (req, res) => {
        let id = req.params.id
        if (id == 0) {
            id = req.user.id
        }
        seguidosDAO.obtenerNumeroSeguidores(id)
        .then(respuesta => {
            res.status(OK).json(respuesta)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/seguidos/cantidad/:id')
    .get(auth.comprobarToken, (req, res) => {
        let id = req.params.id
        if (id == 0) {
            id = req.user.id
        }
        seguidosDAO.obtenerNumeroSeguidos(id)
        .then(respuesta => {
            res.status(OK).json(respuesta)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/seguidoscomprobar/:id')
    .get(auth.comprobarToken, (req, res) => {
        seguidosDAO.comprobarSeguido(req.user.id, req.params.id)
        .then(respuesta => {
            res.status(OK).json(respuesta)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

module.exports = router