const express = require("express");
const path = require('path');
const router = express.Router();

const quackDAO = require(path.join(path.resolve(__dirname, '..'), 'dataAccess', 'quackDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))

router.route('/quacks')
    .post(auth.comprobarToken, (req, res) =>{
        const quack = req.body
        if(quackValido(quack)){
            quackDAO.crear(req.user.id, quack, (err, respuesta) =>{
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
        quackDAO.obtenerPorUsuario(req.params.id, (err, respuesta) =>{
            if(err){
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