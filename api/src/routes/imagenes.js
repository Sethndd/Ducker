const express = require("express")
const multer = require("multer");
const path = require('path');
const { BAD_REQUEST, CREATED } = require("../util/httpResponseCodes");
const router = express.Router()

const dirRaiz = path.resolve(__dirname, '..')
const perfilDAO = require(path.join(dirRaiz, 'dataAccess', 'perfilDAO.js'))
const auth = require(path.join(dirRaiz,  '/util', 'auth.js'))

const storage = multer.diskStorage({
    destination: path.join(dirRaiz, '/imagenes'),
    filename: (req, file, callback) => {
        return callback(null, `${req.user.id}_${file.fieldname}_${Date.now()}${path.extname(file.originalname)}`)
    }
})

const uploadMulter = multer({
    storage: storage,
    limits: {
        fileSize: 3145728
    }
})

router.route('/imagenPerfil')
    .post(auth.comprobarToken, uploadMulter.single('perfil'), (req, res) => {
        perfil = {
            img: req.file.filename,
            banner: ''
        }
        perfilDAO.actualizar(req.user.id, perfil, (err, respuesta) =>{
            if(err){
                console.log(err)
                res.status(BAD_REQUEST).json(err)

            }
            else{
                res.status(CREATED).json({
                    Mensaje: req.file.filename
                })
            }
        })
    })

router.route('/imagenBanner')
    .post(auth.comprobarToken, uploadMulter.single('banner'), (req, res) => {
        perfil = {
            img: '',
            banner: req.file.filename
        }
        perfilDAO.actualizar(req.user.id, perfil, (err, respuesta) =>{
            if(err){
                console.log(err)
                res.status(BAD_REQUEST).json(err)

            }
            else{
                res.status(CREATED).json({
                    Mensaje: req.file.filename
                })
            }
        })
    })

router.route('/imagenQuack')
    .post(auth.comprobarToken, uploadMulter.single('quack'), (req, res) => {
        res.status(CREATED).json({
            Mensaje: req.file.filename
        })
    })

module.exports = router