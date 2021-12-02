const express = require("express")
const { resolve } = require("path")
const path = require('path')
const router = express.Router()

const seguidosDAO = require(path.join(resolve(__dirname, '..'), 'dataAccess', 'seguidosDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))


router.route('/seguidos')
    .post(auth.comprobarToken, (req, res)=>{
        const seguidos = req.body
        if(seguidosValido(seguidos)){
            seguidosDAO.crearSeguidos(req.user.id, seguidos.idSeguido, (err, respuesta)=>{
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
    .get(auth.comprobarToken, (req, res) =>{
        var idUsuarioSeguidor = req.user.id
        seguidosDAO.obtenerSeguidos(idUsuarioSeguidor, (err, respuesta) =>{
            if (err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

router.route('/seguidores')
    .get(auth.comprobarToken, (req, res) =>{
        var idUsuarioSeguido = req.user.id
        seguidosDAO.obtenerSeguidores(idUsuarioSeguido, (err, respuesta) =>{
            if (err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

router.route('/seguidos/:id')
    .get(auth.comprobarToken, (req, res) =>{
        var idUsuarioSeguidor = req.params.id
        seguidosDAO.obtenerSeguidos(idUsuarioSeguidor, (err, respuesta) =>{
            if (err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

router.route('/seguidores/:id')
    .get(auth.comprobarToken, (req, res) =>{
        var idUsuarioSeguido = req.params.id
        seguidosDAO.obtenerSeguidores(idUsuarioSeguido, (err, respuesta) =>{
            if (err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

router.route('/seguidores/cantidad/:id')
    .get(auth.comprobarToken, (req, res) =>{
        var id = req.params.id
        if (id == 0){
            id = req.user.id
        }

        seguidosDAO.obtenerNumeroSeguidores(id, (err, respuesta) =>{
            if (err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

router.route('/seguidos/cantidad/:id')
    .get(auth.comprobarToken, (req, res) =>{
        var id = req.params.id
        if (id == 0){
            id = req.user.id
        }

        seguidosDAO.obtenerNumeroSeguidos(id, (err, respuesta) =>{
            if (err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

router.route('/seguidoscomprobar')
    .get(auth.comprobarToken, (req, res) =>{
        const seguidos = req.body
        if(seguidosValido(seguidos)){
            seguidosDAO.comprobarSeguido(seguidos.idSeguidor, seguidos.idSeguido, (err, respuesta)=>{
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


function seguidosValido(seguidos){
    var result = false
    if(seguidos.hasOwnProperty('idSeguidor')
    && seguidos.hasOwnProperty('idSeguido')){
        result = true
    } 
    return result
}

module.exports = router