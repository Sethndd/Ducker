const express = require("express")
const { resolve } = require("path")
const path = require('path')
const router = express.Router()

const seguidosDAO = require(path.join(resolve(__dirname, '..'), 'dataAccess', 'seguidosDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))


router.route('/seguir/:id')
    .post(auth.comprobarToken, (req, res)=>{
        seguidosDAO.crearSeguidos(req.user.id, req.params.id, (err, respuesta)=>{
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
        var id = req.params.id

        if(id == 0){
            id = req.user.id
        }

        seguidosDAO.obtenerSeguidores(id, (err, respuesta) =>{
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
        var id = req.params.id

        if(id == 0){
            id = req.user.id
        }

        seguidosDAO.obtenerSeguidos(id, (err, respuesta) =>{
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

router.route('/seguidoscomprobar/:id')
    .get(auth.comprobarToken, (req, res) =>{
        seguidosDAO.comprobarSeguido(req.user.id, req.params.id, (err, respuesta)=>{
            if (err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

module.exports = router