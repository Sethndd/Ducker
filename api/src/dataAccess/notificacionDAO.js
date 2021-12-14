const path = require('path')
const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

// Funciones para la entidad Notificacion

function obtenerPorUsuario(idUsuario) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerNotificacionesPorUsuario(?)', [idUsuario], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[0])
            }
        })
    }) 
}

function crear(notificacion) {
    return new Promise( (resolve, reject) => {
        var params = [notificacion.idUsuario, notificacion.evento, notificacion.enlace, notificacion.visto]
        dbConnection.query('call crearNotificacion(?, ?, ?, ?)', params, (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows)
            }
        })
    }) 
}

function visto(id) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call notificacionVista(?)', [id], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows)
            }
        })
    }) 
}

module.exports = { obtenerPorUsuario, crear, visto}