const path = require('path');
const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

// Funciones para la entidad Seguido

function crearSeguidos(idUsuarioSeguidor, idUsuarioSeguido) {
    return new Promise( (resolve, reject) =>{
        dbConnection.query('call crearSeguidos(?, ?, @Mensaje); Select @Mensaje as Mensaje', [idUsuarioSeguidor, idUsuarioSeguido], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[1][0])
            }
        })
    })
    
}

function obtenerSeguidores(idUsuarioSeguido) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerSeguidores(?)', [idUsuarioSeguido], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[0])
            }
        })
    })
}

function obtenerSeguidos(idUsuarioSeguidor) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerSeguidos(?)', [idUsuarioSeguidor], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[0])
            }
        })
    }) 
}

function obtenerNumeroSeguidos(idUsuario) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerNumeroSeguidos(?)', [idUsuario], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[0][0])
            }
        })
    })
}

function obtenerNumeroSeguidores(idUsuario) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerNumeroSeguidores(?)', [idUsuario], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[0][0])
            }
        })
    })
}

function comprobarSeguido(idUsuarioSeguidor, idUsuarioSeguido) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call comprobarSeguido(?, ?, @Mensaje); Select @Mensaje as Mensaje', [idUsuarioSeguidor, idUsuarioSeguido], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[1][0])
            }
        })
    }) 
}

module.exports = {crearSeguidos, obtenerSeguidores, obtenerSeguidos, obtenerNumeroSeguidos, obtenerNumeroSeguidores, comprobarSeguido}