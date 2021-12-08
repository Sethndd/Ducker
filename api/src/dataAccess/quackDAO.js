const path = require('path');

const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

function crear(idUsuario, quack, listaHastag, callback){
    dbConnection.query('call crearQuack(?, ?, ?, ?, @idQuack); SELECT @idQuack as id',
        [idUsuario, quack.texto, quack.quackPadre, quack.idAdjunto], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            id = rows[1][0].id
            listaHastag.forEach(hashtag => {
                dbConnection.query('call crearHashtag(?, ?)', [id, hashtag], (err, rows, fields) =>{
                    if(err){
                        return callback(err)
                    }
                })
            })

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

function obtenerCantidadHijos(id, callback){
    dbConnection.query('call obtenerCantidadHijosQuack(?);', [id], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows[0][0])
        }
    })
}

function obtenerSeguidos(id, callback){
    dbConnection.query('call obtenerQuacksSeguidos(?);', [id], (err, rows, fields) =>{
        if(err){
            return callback(err)
        }
        else{
            callback(null, rows[0])
        }
    })
}

module.exports = {crear, obtenerTodos, obtenerPorID, obtenerPorUsuario, eliminar, obtenerPadre, obtenerPadres, obtenerHijos, obtenerSeguidos, obtenerCantidadHijos}