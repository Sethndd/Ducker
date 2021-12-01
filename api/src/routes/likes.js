const express = require("express")
const { resolve } = require("path")
const path = require('path')
const router = express.Router()

const likesDAO = require(path.join(resolve(__dirname, '..'), 'dataAccess', 'likesDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))


router.route('/likes')
    .post(auth.comprobarToken, (req, res)=>{
        const like = req.body
        if(likeValido(like)){
            likesDAO.crearLikes(like.idQuack, req.user.id, (err, respuesta)=>{
                if (err){
                    console.log(err)
                    res.status(400).json(err)
                    return
                }
                res.status(201).json(respuesta)
            })
        }
        else {
            res.status(400).json({Mensaje : 'Faltan datos'})
        }
    })

router.route('/likes/:id')
    .get(auth.comprobarToken, (req, res) =>{ 
        likesDAO.obtenerCantidadLikesQuack(req.params.id, (err, rows, fields) =>{ 
            if(err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(200).json(rows[0])
        })
    })

router.route('/likescomprobar/:id')
    .get(auth.comprobarToken, (req, res) =>{ 
        likesDAO.comprobarLike(req.params.id, req.user.id, (err, respuesta)=>{
            if (err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

function likeValido(like){
    var result = false
    if(like.hasOwnProperty('idQuack')){
        result = true
    } 
    return result
}

module.exports = router