const express = require("express")
const { resolve } = require("path")
const path = require('path')
const router = express.Router()

const hashtagDAO = require(path.join(resolve(__dirname, '..'), 'dataAccess', 'hashtagDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))

router.route('/hashtags')
    .post(auth.comprobarToken, (req, res)=>{
        const hashtag = req.body
        if(validarHashtag(hashtag)){
            hashtagDAO.crear(hashtag, (err, respuesta)=>{
                if (err){
                    console.log(err)
                    res.status(400).json(err)
                    return
                }
                res.status(201).json({Mensaje : 'Hashtag creado!'})
            })
        }
        else {
            res.status(400).json({Mensaje : 'Faltan datos'})
        }
    })
    .get(auth.comprobarToken, (req, res) => {
        hashtagDAO.obtenerPopulares((err, respuesta) => {
            if (err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

router.route('/hashtags/quack/:idQuack')
    .get(auth.comprobarToken, (req, res) =>{
        var idQuack = req.params.idQuack
        hashtagDAO.obtenerPorQuack(idQuack, (err, respuesta) =>{
            if (err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

router.route('/hashtags/hashtag/:hashtag')
    .get(auth.comprobarToken, (req, res) =>{
        var hashtag = req.params.hashtag
        hashtagDAO.obtenerPorHashtag(hashtag, (err, respuesta) =>{
            if (err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

function validarHashtag(hashtag){
    var result = false
    if(hashtag.hasOwnProperty('idQuack')
    && hashtag.hasOwnProperty('hashtag')){
        result = true
    } 
    return result
}

module.exports = router