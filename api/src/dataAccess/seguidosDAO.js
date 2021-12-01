const path = require('path');
const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

function crearSeguidos(idUsuarioSeguidor, idUsuarioSeguido, callback){
    dbConnection.query('call crearSeguidos(?, ?, @Mensaje); Select @Mensaje as Mensaje', [idUsuarioSeguidor, idUsuarioSeguido], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows[1][0])
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

function obtenerNumeroSeguidos(idUsuario, callback){
    dbConnection.query('call obtenerNumeroSeguidos(?)', [idUsuario], (err, rows, fields)=>{
        if (err){
            return callback(err)
        }
        else {
            callback(null, rows[0][0])
        }
    })
}

function obtenerNumeroSeguidores(idUsuario, callback){
    dbConnection.query('call obtenerNumeroSeguidores(?)', [idUsuario], (err, rows, fields)=>{
        if (err){
            return callback(err)
        }
        else {
            callback(null, rows[0][0])
        }
    })
}

function comprobarSeguido(idUsuarioSeguidor, idUsuarioSeguido, callback){
    dbConnection.query('call comprobarSeguido(?, ?, @Mensaje); Select @Mensaje as Mensaje', [idUsuarioSeguidor, idUsuarioSeguido], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows[1][0])
        }
    })
}

module.exports = {crearSeguidos, obtenerSeguidores, obtenerSeguidos, obtenerNumeroSeguidos, obtenerNumeroSeguidores, comprobarSeguido}