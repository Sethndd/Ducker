const path = require('path');
const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

// Funciones para la entidad Quack

function crear(idUsuario, quack, listaHastag) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call crearQuack(?, ?, ?, ?, @idQuack); SELECT @idQuack as id',
            [idUsuario, quack.texto, quack.quackPadre, quack.idAdjunto], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                id = rows[1][0].id
                if(listaHastag){
                    listaHastag.forEach(hashtag => {
                        dbConnection.query('call crearHashtag(?, ?)', [id, hashtag], (err, rows, _) => {
                            if (err) {
                                reject(err)
                            }
                        })
                    })
                }
                resolve(rows)
            }
        })
    }) 
}

function obtenerTodos() { 
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerQuacks();', (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[0])
            }
        })
    }) 
}

function obtenerPorID(id) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerQuack(?);', [id], (err, rows, _) => {
            if (err) { 
                reject(err)
            }
            else {
                resolve(rows[0][0])
            }
        })
    }) 
}

function obtenerPorUsuario(idUsuario) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerQuacksUsuario(?)', [idUsuario], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[0])
            }
        })
    }) 
}

function eliminar(id) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call eliminarQuack(?)', [id] , (err, rows, _) => {
            if(err) {
                reject(err)
            }
            else {
                resolve(rows[0])
            }
        })
    }) 
}

function obtenerPadre(id) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerQuackPadre(?);', [id], (err, rows, _) => {
            if(err) {
                reject(err)
            }
            else { 
                resolve(rows[0][0])
            }
        })
    }) 
}

function obtenerPadres(id) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerQuacksPadres(?);', [id], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[0])
            }
        })
    }) 
}

function obtenerHijos(id) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerQuacksHijos(?);', [id], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[0])
            }
        })
    }) 
}

function obtenerCantidadHijos(id) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerCantidadHijosQuack(?);', [id], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else{
                resolve(rows[0][0])
            }
        })
    }) 
}

function obtenerSeguidos(id) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerQuacksSeguidos(?);', [id], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[0])
            }
        })
    }) 
}

function obtenerPorHashtag(idHashtag) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerQuacksPorHashtag(?)', [idHashtag], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            resolve(rows[0])
        })
    }) 
}

module.exports = {crear, obtenerTodos, obtenerPorID, obtenerPorUsuario, eliminar, obtenerPadre, obtenerPadres, obtenerHijos, obtenerSeguidos, obtenerCantidadHijos, obtenerPorHashtag}