const express = require("express");
const path = require('path');
const router = express.Router();

const quackDAO = require(path.join(path.resolve(__dirname, '..'), 'dataAccess', 'quackDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))

router.route('/quacks')
    .post(auth.comprobarToken, (req, res) =>{
        const patronHashtag = /(#+[a-zA-Z0-9(_)]{1,})/g
        const quack = req.body

        if(quackValido(quack)){
            hasthags = quack.texto.match(patronHashtag)

            quackDAO.crear(req.user.id, quack, hasthags, (err, respuesta) =>{
                if(err){
                    console.log(err)
                    res.status(400).json(err)
                    return
                }
                res.status(201).json({Mensaje : 'Quack publicado!'})
            })
        }
        else{
            res.status(400).json({Mensaje : 'Faltan datos'})
        }
    })
    .get(auth.comprobarToken, (req, res) =>{
        quackDAO.obtenerTodos((err, respuesta) =>{
            if(err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })


router.route('/quacks/:id')
    .get(auth.comprobarToken, (req, res) => {
        quackDAO.obtenerPorID(req.params.id, (err, respuesta) =>{
            if(err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })
    .delete(auth.comprobarToken, (req, res) => {
        quackDAO.eliminar(req.params.id, (err, respuesta) =>{
            if(err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json({Mensaje: 'Quack eliminado'})
        })
    })

router.route('/quacks/usuario/:id')
    .get(auth.comprobarToken, (req, res) => {
        id = req.params.id

        if(id == 0){
            id = req.user.id
        }

        quackDAO.obtenerPorUsuario(id, (err, respuesta) =>{
            if(err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

router.route('/quacks/padre/:id')
    .get(auth.comprobarToken, (req, res) => {
        quackDAO.obtenerPadre(req.params.id, (err, respuesta) =>{
            if(err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

router.route('/quacks/padres/:id')
    .get(auth.comprobarToken, (req, res) => {
        quackDAO.obtenerPadres(req.params.id, (err, respuesta) =>{
            if(err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

router.route('/quacks/hijos/cantidad/:id')
    .get(auth.comprobarToken, (req, res) => {
        quackDAO.obtenerCantidadHijos(req.params.id, (err, respuesta) =>{
            if(err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

router.route('/quacks/hijos/:id')
    .get(auth.comprobarToken, (req, res) => {
        quackDAO.obtenerHijos(req.params.id, (err, respuesta) =>{
            if(err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

router.route('/quacksseguidos')
    .get(auth.comprobarToken, (req, res) => {
        quackDAO.obtenerSeguidos(req.user.id, (err, respuesta) =>{
            if(err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

router.route('/quackshashtag/:id')
    .get(auth.comprobarToken, (req, res) => {
        quackDAO.obtenerPorHashtag(req.params.id, (err, respuesta) => {
            if (err) {
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

function quackValido(quack){
    if(quack.hasOwnProperty('texto')
    && quack.hasOwnProperty('quackPadre')
    && quack.hasOwnProperty('idAdjunto')){
        return true
    }
    return false
}

module.exports = router