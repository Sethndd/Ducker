const path = require('path');
const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

function crearLikes(idQuack, idUsuario, callback){
    dbConnection.query('call crearLike(?, ?)', [idQuack, idUsuario], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows)
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

function eliminarLikes(id, callback){
    dbConnection.query('call eliminarLike(?)', [id] , (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows[0])
        }
    })
}

module.exports = {crearLikes, obtenerCantidadLikesQuack, eliminarLikes}