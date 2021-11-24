const path = require('path');

const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

function crear(quack, callback){
    dbConnection.query('call crearQuack(?, ?, ?, ?)', [quack.idUsuario, quack.texto, quack.quackPadre, quack.idAdjunto], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows)
        }
    })
}

function obtenerTodos(callback){
    dbConnection.query('call obtenerQuacks();', (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows[0])
        }
    })
}

function obtenerPorID(id, callback){
    dbConnection.query('call obtenerQuack(?);', [id], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows[0][0])
        }
    })
}

function obtenerPorUsuario(idUsuario, callback){
    dbConnection.query('call obtenerQuacksUsuario(?)', [idUsuario], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows[0])
        }
    })
}

function eliminar(id, callback){
    dbConnection.query('call eliminarQuack(?)', [id] , (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows[0])
        }
    })
}

module.exports = {crear, obtenerTodos, obtenerPorID, obtenerPorUsuario, eliminar}