dbConnection = require('./dbConnection.js')

function obtenerContrasena(correo){
    return new Promise((resolve, reject) =>{
        dbConnection.query('call obtenerContrasena(?);', [correo] , (err, rows, fields) =>{
            if(err){
                reject(err)
            }
            resolve(rows[0][0])
        })
    })
}

function obtenerTodos(){
    return new Promise((resolve, reject) =>{
        dbConnection.query('call obtenerUsuarios();', (err, rows, fields) =>{
            if(err){
                reject(err)
            }
            resolve(rows[0])
        })
    })
}

function obtener(id){
    return new Promise((resolve, reject) =>{
        dbConnection.query('call obtenerUsuario(?);', [id], (err, rows, fields) =>{
            if(err){
                reject(err)
            }
            resolve(rows[0][0])
        })
    })
}

function agregar(usuario, hashedPass){
    return new Promise((resolve, reject) =>{
        dbConnection.query('call registrarUsuario(?, ?, ?, ?, ?, @Respuesta); SELECT @Respuesta as Respuesta;', 
        [usuario.correo, hashedPass, usuario.nombrePropio, usuario.nombreUsuario, usuario.fechaNacimiento] ,(err, rows, fields) =>{
            if(err){
                reject(err)
            }
            resolve(rows)
        })
    })
}

function actualizar(id, usuario){
    return new Promise((resolve, reject) =>{
        dbConnection.query('call actualizarUsuario(?, ?, ?, ?, ?, ?, ?);', 
        [id, usuario.correo, usuario.nombrePropio, usuario.nombreUsuario, usuario.fechaNacimiento, usuario.estado, usuario.tipo] ,(err, rows, fields) =>{
            if(err){
                reject(err)
            }
            resolve(rows)
        })
    })
}

function eliminar(id){
    return new Promise((resolve, reject) =>{
        dbConnection.query('call eliminarUsuario(?);', [id] ,(err, rows, fields) =>{
            if(err){
                reject(err)
            }
            resolve(rows)
        })
    })
}


module.exports = {obtenerContrasena, obtenerTodos, obtener, agregar, actualizar, eliminar }