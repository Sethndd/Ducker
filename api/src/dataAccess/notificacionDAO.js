const path = require('path')
const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

function obtenerPorUsuario(idUsuario, callback){
    dbConnection.query('call obtenerNotificacionesPorUsuario(?)', [idUsuario], (err, rows, fiedls) => {
        if(err) {
            return callback(err)
        }
        else {
            callback(null, rows[0])
        }
    })
}

function crear(notificacion, callback){
    var params = [notificacion.idUsuario, notificacion.evento, notificacion.enlace, notificacion.visto]
    dbConnection.query('call crearNotificacion(?, ?, ?, ?)', params, (err, rows, fields) => {
        if (err){
            return callback(err)
        }
        else {
            callback(null, rows)
        }
    })
}

function visto(id, callback){
    dbConnection.query('call notificacionVista(?)', [id], (err, rows, fields) => {
        if (err) {
            return callback(err)
        }
        else {
            callback(null, rows)
        }
    })
}

module.exports = { obtenerPorUsuario, crear, visto}