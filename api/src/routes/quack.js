const express = require("express");
const path = require('path');
const { BAD_REQUEST, CREATED, OK } = require("../util/httpResponseCodes");
const router = express.Router();

const quackDAO = require(path.join(path.resolve(__dirname, '..'), 'dataAccess', 'quackDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))

// Rutas para la entidad Quack

router.route('/quacks')
    .post(auth.comprobarToken, (req, res) => {
        const patronHashtag = /(#+[a-zA-Z0-9(_)]{1,})/g
        const quack = req.body

        if (quackValido(quack)) {
            hasthags = quack.texto.match(patronHashtag)
            quackDAO.crear(req.user.id, quack, hasthags)
            .then(_ => {
                res.status(CREATED).json({Mensaje : 'Quack publicado!'})
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
    .get(auth.comprobarToken, (_, res) => {
        quackDAO.obtenerTodos()
        .then(quacks => {
            res.status(OK).json(quacks)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })


router.route('/quacks/:id')
    .get(auth.comprobarToken, (req, res) => {
        quackDAO.obtenerPorID(req.params.id)
        .then(quack => {
            res.status(201).json(quack)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })
    .delete(auth.comprobarToken, (req, res) => {
        quackDAO.eliminar(req.params.id)
        .then(_ => {
            res.status(OK).json({Mensaje: 'Quack eliminado'})
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/quacks/usuario/:id')
    .get(auth.comprobarToken, (req, res) => {
        id = req.params.id

        if (id == 0) {
            id = req.user.id
        }
        quackDAO.obtenerPorUsuario(id)
        .then(quack => {
            res.status(OK).json(quack)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/quacks/padre/:id')
    .get(auth.comprobarToken, (req, res) => {
        quackDAO.obtenerPadre(req.params.id)
        .then(quack => {
            res.status(OK).json(quack)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/quacks/padres/:id')
    .get(auth.comprobarToken, (req, res) => {
        quackDAO.obtenerPadres(req.params.id)
        .then(quacks => {
            res.status(OK).json(quacks)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/quacks/hijos/cantidad/:id')
    .get(auth.comprobarToken, (req, res) => {
        quackDAO.obtenerCantidadHijos(req.params.id)
        .then(respuesta => {
            res.status(OK).json(respuesta)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/quacks/hijos/:id')
    .get(auth.comprobarToken, (req, res) => {
        quackDAO.obtenerHijos(req.params.id)
        .then(hijos => {
            res.status(OK).json(hijos)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/quacksseguidos')
    .get(auth.comprobarToken, (req, res) => {
        quackDAO.obtenerSeguidos(req.user.id)
        .then(seguidos => {
            res.status(OK).json(seguidos)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/quackshashtag/:id')
    .get(auth.comprobarToken, (req, res) => {
        quackDAO.obtenerPorHashtag(req.params.id)
        .then(quack => {
            res.status(OK).json(quack)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

function quackValido(quack){
    return quack.hasOwnProperty('texto')
        && quack.hasOwnProperty('quackPadre')
        && quack.hasOwnProperty('idAdjunto')
}

module.exports = router