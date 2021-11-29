const express = require("express")
const { resolve } = require("path")
const path = require('path')
const router = express.Router()

const perfilDAO = require(path.join(resolve(__dirname, '..'), 'dataAccess', 'perfilDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))




    router.route('/perfil/:id')
    .get(auth.comprobarToken, (req, res) =>{
        var idUsuario = req.params.id
        perfilDAO.obtenerPerfil(idUsuario, (err, respuesta) =>{
            if (err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(201).json(respuesta)
        })
    })

    .patch(auth.comprobarToken, (req, res)=>{
        const perfil = req.body
        if(validarPerfil(perfil)){
            perfilDAO.actualizar( req.params.id, perfil.imgRuta, perfil.bannerRuta, (err, respuesta)=>{
                if (err){
                    console.log(err)
                    res.status(400).json(err)
                    return
                }
                res.status(201).json({Mensaje : 'Perfil actualizado!'})
            })
        }
        else {
            res.status(400).json({Mensaje : 'Faltan datos'})
        }
    })

function validarPerfil(perfil){
    var result = false
    if(perfil.hasOwnProperty('idUsuario')
        && perfil.hasOwnProperty('imgRuta')
        && perfil.hasOwnProperty('bannerRuta')){
        result = true
    } 
    return result
}

module.exports = router