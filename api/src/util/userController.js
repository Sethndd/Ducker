const bcrypt = require('bcrypt');
const path = require('path');

const carpeta = path.resolve(__dirname, '..')
const usuarioDAO = require(path.join(carpeta,  '/dataAccess', 'usuarioDAO.js'))

// Funciones para el control de usuarios

function registrarUsuario(user) {
    return new Promise( (resolve, reject) => {
        bcrypt.hash(user.contrasena, 10)
        .then(hashedPass => {
            usuarioDAO.agregar(user, hashedPass)
        })
        .then(respuesta => {
            resolve(respuesta)
        })
        .catch(err => {
            console.log(err)
            reject(err)
        })
    }) 
}

function validarCredenciales(correo, contrasena){
    return new Promise( (resolve, reject) => {
        usuarioDAO.obtenerContrasena(correo)
        .then(respuesta => {
            if (respuesta && respuesta.hasOwnProperty('contrasena')) {
                bcrypt.compare(contrasena, respuesta.contrasena, (_, res) =>{
                    if(res){
                        resolve(respuesta)
                    }
                    else{
                        resolve(null)
                    }
                })
            }
            else{
                resolve(null)
            }
        })
        .catch(err => {
            console.log(err)
            reject(err)
        })
    }) 
}

module.exports =  {registrarUsuario, validarCredenciales}