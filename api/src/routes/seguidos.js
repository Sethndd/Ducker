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
                res.status(201).json({Mensaje : 'Seguidos guardado!'})
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
    .delete(auth.comprobarToken, (req, res) =>{ 
        seguidosDAO.eliminarSeguidos(req.user.id, req.params.id, (err, rows, fields) =>{ 
            if(err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(200).json({Mensaje: 'Eliminado'})
        })
    })
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

function seguidosValido(seguidos){
    var result = false
    if(seguidos.hasOwnProperty('idSeguidor')
    && seguidos.hasOwnProperty('idSeguido')){
        result = true
    } 
    return result
}

module.exports = router