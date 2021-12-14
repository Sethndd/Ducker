const path = require('path')
const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

//Funciones para la entidad Guardado

function crear(idQuack, idUsuario) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call crearQuackGuardado(?, ?)', [idQuack, idUsuario], (err, rows, _) => {
            if(err) {
                reject(err)
            }
            else {
                resolve(rows)
            }
        })
    }) 
}

function obtenerTodosGuardados(idUsuario) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerQuacksGuardados(?)', [idUsuario], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows)
            }
        })
    }) 
}

module.exports = {crear, obtenerTodosGuardados}