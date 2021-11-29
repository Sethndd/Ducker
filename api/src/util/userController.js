const bcrypt = require('bcrypt');
const path = require('path');

const carpeta = path.resolve(__dirname, '..')
const usuarioDAO = require(path.join(carpeta,  '/dataAccess', 'usuarioDAO.js'))

function registrarUsuario(user, cb){
    bcrypt.hash(user.contrasena, 10)
    .then(hashedPass => usuarioDAO.agregar(user, hashedPass))
    .then(respuesta => {
        cb(null, respuesta)
    })
    .catch(err => {
        console.log(err)
        cb(err)
    })
}

function validarCredenciales(correo, contrasena, callback){
    usuarioDAO.obtenerContrasena(correo)
    .then(respuesta => {
        if(respuesta && respuesta.hasOwnProperty('contrasena')){
            bcrypt.compare(contrasena, respuesta.contrasena, (err, res) =>{
                if(res){
                    callback(null, respuesta)
                }
                else{
                    callback(null, null)
                }
            })
        }
        else{
            callback(null, null)
        }
    })
    .catch(err => {
        console.log(err)
        cb(err)
    })
}

module.exports =  {registrarUsuario, validarCredenciales}