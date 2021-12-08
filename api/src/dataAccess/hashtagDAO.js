const path = require('path')
const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

function crear(idQuack, lista, callback){
    lista.forEach(hashtag => {
        dbConnection.query('call crearHashtag(?, ?)', [idQuack, hashtag], (err, rows, fields) =>{
            if(err){
                return callback(err)
            }
            else{
                callback(null, rows)
            }
        })
    })
}

function obtenerPorQuack(idQuack, callback){
    dbConnection.query('call obtenerHashtagPorQuack(?)', [idQuack], (err, rows, fields)=>{
        if (err){
            return callback(err)
        }
        else {
            callback(null, rows[0])
        }
    })
}

function obtenerPorHashtag(hashtag, callback){
    dbConnection.query('call obtenerHashtagPorHashtag(?)', [hashtag], (err, rows, fields)=>{
        if (err){
            return callback(err)
        }
        else {
            return callback(null, rows[0])
        }
    })
}

function obtenerPopulares(callback){
    dbConnection.query('call obtenerHashtagsPopulares()', (err, rows, fields)=>{
        if (err){
            return callback(err)
        }
        else {
            callback(null, rows[0])
        }
    })
}

module.exports = {crear, obtenerPorQuack, obtenerPorHashtag, obtenerPopulares}