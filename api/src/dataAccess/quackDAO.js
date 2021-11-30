const path = require('path');

const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

function crear(idUsuario, quack, callback){
    dbConnection.query('call crearQuack(?, ?, ?, ?)', [idUsuario, quack.texto, quack.quackPadre, quack.idAdjunto], (err, rows, fields) =>{
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

function obtenerPadre(id, callback){
    dbConnection.query('call obtenerQuackPadre(?);', [id], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows[0][0])
        }
    })
}

function obtenerPadres(id, callback){
    dbConnection.query('call obtenerQuacksPadres(?);', [id], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows[0])
        }
    })
}

function obtenerHijos(id, callback){
    dbConnection.query('call obtenerQuacksHijos(?);', [id], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows[0])
        }
    })
}

module.exports = {crear, obtenerTodos, obtenerPorID, obtenerPorUsuario, eliminar, obtenerPadre, obtenerPadres, obtenerHijos}