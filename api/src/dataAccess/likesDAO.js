const path = require('path');
const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

// Funciones para la entidad Like

function crearLikes(idQuack, idUsuario) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call crearLike(?, ?, @mensaje); Select @mensaje as Mensaje', [idQuack, idUsuario], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else{
                resolve(rows[1][0])
            }
        })
    }) 
}

function obtenerCantidadLikesQuack(idQuack) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerCantidadLikesQuack(?)', [idQuack], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[0][0])
            }
        })
    }) 
}


function comprobarLike(idQuack, idUsuario) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call comprobarLike(?, ?, @mensaje); Select @mensaje as Mensaje', [idQuack, idUsuario], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[1][0])
            }
        })
    }) 
}

module.exports = {crearLikes, obtenerCantidadLikesQuack, comprobarLike}