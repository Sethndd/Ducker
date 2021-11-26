const express = require("express")
const { resolve } = require("path")
const path = require('path')
const router = express.Router()

const guardadosDAO = require(path.join(resolve(__dirname, '..'), 'dataAccess', 'guardadosDAO.js'))
const auth = require(path.join(path.resolve(__dirname, '..'),  '/util', 'auth.js'))


router.route('/guardados')
    .post(auth.comprobarToken, (req, res)=>{
        const idQuack = req.body
        if(validarIdQuack(idQuack)){
            guardadosDAO.crear(idQuack.idQuack, req.user.id, (err, respuesta)=>{
                if (err){
                    console.log(err)
                    res.status(400).json(err)
                    return
                }
                res.status(201).json({Mensaje : 'Quack guardado!'})
            })
        }
        else {
            res.status(400).json({Mensaje : 'Faltan datos'})
        }
    })

    .get(auth.comprobarToken, (req, res) =>{ 
        guardadosDAO.obtenerTodosGuardados(req.user.id, (err, rows, fields) =>{ 
            if(err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(200).json(rows[0])
        })
    })

    router.route('/guardados/:id')
    .delete(auth.comprobarToken, (req, res) =>{ 
        guardadosDAO.eliminar([req.params.id], (err, rows, fields) =>{ 
            
            if(err){
                console.log(err)
                res.status(400).json(err)
                return
            }
            res.status(200).json({Mensaje: 'Eliminado'})
        })
    });

function validarIdQuack(idQuack){
    var result = false
    if(idQuack.hasOwnProperty('idQuack')){
        result = true
    } 
    return result
}

module.exports = router