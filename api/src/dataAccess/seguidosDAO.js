const path = require('path');
const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

function crearSeguidos(idUsuarioSeguidor, idUsuarioSeguido, callback){
    dbConnection.query('call crearSeguidos(?, ?)', [idUsuarioSeguidor, idUsuarioSeguido], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows)
        }
    })
}

function obtenerSeguidores(idUsuarioSeguido, callback){
    dbConnection.query('call obtenerSeguidores(?)', [idUsuarioSeguido], (err, rows, fields)=>{
        if (err){
            return callback(err)
        }
        else {
            callback(null, rows[0])
        }
    })
}

function obtenerSeguidos(idUsuarioSeguidor, callback){
    dbConnection.query('call obtenerSeguidos(?)', [idUsuarioSeguidor], (err, rows, fields)=>{
        if (err){
            return callback(err)
        }
        else {
            callback(null, rows[0])
        }
    })
}

function eliminarSeguidos(idUsuarioSeguidor, idUsuarioSeguido, callback){
    dbConnection.query('call eliminarSeguidos(?,?)', [idUsuarioSeguidor, idUsuarioSeguido] , (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows[0])
        }
    })
}

module.exports = {crearSeguidos, obtenerSeguidores, obtenerSeguidos, eliminarSeguidos}