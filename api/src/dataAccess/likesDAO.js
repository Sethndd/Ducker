const path = require('path');
const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

function crearLikes(idQuack, idUsuario, callback){
    dbConnection.query('call crearLike(?, ?, @mensaje); Select @mensaje as Mensaje', [idQuack, idUsuario], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows[1][0])
        }
    })
}

function obtenerCantidadLikesQuack(idQuack, callback){
    dbConnection.query('call obtenerCantidadLikesQuack(?)', [idQuack], (err, rows, fields)=>{
        if (err){
            return callback(err)
        }
        else {
            callback(null, rows[0])
        }
    })
}


function comprobarLike(idQuack, idUsuario, callback){
    dbConnection.query('call comprobarLike(?, ?, @mensaje); Select @mensaje as Mensaje', [idQuack, idUsuario], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows[1][0])
        }
    })
}

module.exports = {crearLikes, obtenerCantidadLikesQuack, comprobarLike}