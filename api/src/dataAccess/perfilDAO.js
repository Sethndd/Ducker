const path = require('path')
const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

// Funciones para la entidad Perfil

function actualizar(idUsuario, perfil) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call actualizarPerfil(?, ?, ?)', [idUsuario, perfil.imgRuta, perfil.bannerRuta], (err, rows, _) => {
            if(err) {
                reject(err)
            }
            else{
                resolve(rows)
            }
        })
    }) 
}

function obtenerPerfil(idUsuario) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerPerfil(?)', [idUsuario], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[0][0])
            }
        })
    }) 
}


module.exports = {actualizar, obtenerPerfil}