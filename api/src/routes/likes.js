const express = require("express")
const { resolve } = require("path")
const path = require('path')
const { BAD_REQUEST, CREATED, OK } = require("../util/httpResponseCodes")
const router = express.Router()

const likesDAO = require(path.join(resolve(__dirname, '..'), 'dataAccess', 'likesDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))

// Rutas para la entidad Like

router.route('/likes')
    .post(auth.comprobarToken, (req, res) => {
        let like = req.body
        if (likeValido(like)) {
            likesDAO.crearLikes(like.idQuack, req.user.id)
            .then(respuesta => {
                res.status(CREATED).json(respuesta)
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

router.route('/likes/:id')
    .get(auth.comprobarToken, (req, res) => { 
        likesDAO.obtenerCantidadLikesQuack(req.params.id)
        .then(cantidadLikes => {
            res.status(OK).json(cantidadLikes)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/likescomprobar/:id')
    .get(auth.comprobarToken, (req, res) => { 
        likesDAO.comprobarLike(req.params.id, req.user.id)
        .then(respuesta => {
            res.status(OK).json(respuesta)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

function likeValido(like){
    return like.hasOwnProperty('idQuack')
}

module.exports = router