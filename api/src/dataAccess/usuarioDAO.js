const path = require('path');
const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

// Funciones para la entidad Usuario

function obtenerContrasena(correo) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerContrasena(?);', [correo] , (err, rows, _) => {
            if (err) {
                reject(err)
            }
            resolve(rows[0][0])
        })
    })
}

function obtenerTodos() {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerUsuarios();', (err, rows, _) => {
            if (err) {
                reject(err)
            }
            resolve(rows[0])
        })
    })
}

function obtener(id) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerUsuario(?);', [id], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            resolve(rows[0][0])
        })
    })
}

function agregar(usuario, hashedPass) {
    return new Promise( (resolve, reject) =>{
        dbConnection.query('call registrarUsuario(?, ?, ?, ?, ?, @Respuesta); SELECT @Respuesta as Respuesta;', 
        [usuario.correo, hashedPass, usuario.nombrePropio, usuario.nombreUsuario, usuario.fechaNacimiento] ,(err, rows, _) => {
            if (err) {
                reject(err)
            }
            resolve(rows)
        })
    })
}

function actualizar(id, usuario) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call actualizarUsuario(?, ?, ?, ?, ?, ?, ?);', 
        [id, usuario.correo, usuario.nombrePropio, usuario.nombreUsuario, usuario.fechaNacimiento, usuario.estado, usuario.tipo] ,(err, rows, _) => {
            if (err) {
                reject(err)
            }
            resolve(rows)
        })
    })
}

function crearAdmin(id){
    return new Promise((resolve, reject) =>{
        dbConnection.query('call admin(?);', [id] ,(err, rows, _) =>{
            if(err){
                reject(err)
            }
            resolve(rows)
        })
    })
}

function eliminar(id) {
    return new Promise((resolve, reject) => {
        dbConnection.query('call eliminarUsuario(?);', [id] ,(err, rows, _) => {
            if (err) {
                reject(err)
            }
            resolve(rows)
        })
    })
}


module.exports = {obtenerContrasena, obtenerTodos, obtener, agregar, actualizar, eliminar, crearAdmin}