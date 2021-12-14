const express = require("express")
const { resolve } = require("path")
const path = require('path')
const { BAD_REQUEST, OK } = require("../util/httpResponseCodes")
const router = express.Router()

const hashtagDAO = require(path.join(resolve(__dirname, '..'), 'dataAccess', 'hashtagDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))

// Rutas para la entidad Hashtag

router.route('/hashtags')
    .get(auth.comprobarToken, (_, res) => {
        hashtagDAO.obtenerPopulares()
        .then(hashtagsPopulares => {
            res.status(OK).json(hashtagsPopulares)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/hashtags/quack/:idQuack')
    .get(auth.comprobarToken, (req, res) => {
        var idQuack = req.params.idQuack
        hashtagDAO.obtenerPorQuack(idQuack)
        .then(hashtag => {
            res.status(OK).json(hashtag)
        })
        .catch(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

router.route('/hashtags/hashtag/:hashtag')
    .get(auth.comprobarToken, (req, res) => {
        var hashtag = req.params.hashtag
        hashtagDAO.obtenerPorHashtag(hashtag)
        .then(respuesta => {
            res.status(OK).json(respuesta)
        })
        .cath(err => {
            console.log(err)
            res.status(BAD_REQUEST).json(err)
        })
    })

module.exports = router