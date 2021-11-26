dbConnection = require('./dbConnection.js')

function obtener(correo){
    return new Promise((resolve, reject) =>{
        dbConnection.query('call obtenerContrasena(?);', [correo] , (err, rows, fields) =>{
            if(err){
                reject(err)
            }
            resolve(rows[0][0])
        })
    })
}

function agregar(user, hashedPass){
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

module.exports = {agregar, obtener}