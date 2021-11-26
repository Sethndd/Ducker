const path = require('path')
const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

function crear(idQuack, idUsuario, callback){
    dbConnection.query('call crearQuackGuardado(?, ?)', [idQuack, idUsuario], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows)
        }
    })
}

function obtenerTodosGuardados(idUsuario, callback){
    dbConnection.query('call obtenerQuacksGuardados(?)', [idUsuario], (err, rows, fields)=>{
        if (err){
            return callback(err)
        }
        else {
            callback(null, rows)
        }
    })
}

function eliminar(id, callback){
    dbConnection.query('call eliminarQuackGuardado(?)', [id], callback)
}

module.exports = {crear, obtenerTodosGuardados, eliminar}