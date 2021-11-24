const bcrypt = require('bcrypt');
dbConnection = require('./dbConnection.js')

function getUser(correo){
    return new Promise((resolve, reject) =>{
        dbConnection.query('call obtenerContrasena(?);', [correo] , (err, rows, fields) =>{
            if(err){
                reject(err)
            }
            resolve(rows[0][0])
        })
    })
}

function insertUser(user, hashedPass){
    return new Promise((resolve, reject) =>{
        dbConnection.query('call registrarUsuario(?, ?, ?, ?, ?, @Respuesta); SELECT @Respuesta as Respuesta;', 
        [user.correo, hashedPass, user.nombrePropio, user.nombreUsuario, user.fechaNacimiento] ,(err, rows, fields) =>{
            if(err){
                reject(err)
            }
            resolve(rows[1][0])
        })
    })
}

function registrarUsuario(user, cb){
    bcrypt.hash(user.contrasena, 10)
    .then(hashedPass => insertUser(user, hashedPass))
    .then(respuesta => {
        cb(null, respuesta)
    })
    .catch(err => {
        console.log(err)
        cb(err)
    })
}

function validarCredenciales(correo, contrasena, callback){
    getUser(correo)
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