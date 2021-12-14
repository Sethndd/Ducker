const { rejects } = require('assert')
const path = require('path')
const dbConnection = require(path.join(__dirname, 'dbConnection.js'))

// Funciones para la entidad Hashtag

function crear(idQuack, lista){
    return new Promise( (resolve, reject) => {
        lista.forEach(hashtag => {
            dbConnection.query('call crearHashtag(?, ?)', [idQuack, hashtag], (err, rows, _) =>{
                if(err){
                    reject(err)
                }
                else{
                    resolve(rows)
                }
            })
        })
    }) 
}

function obtenerPorQuack(idQuack){
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerHashtagPorQuack(?)', [idQuack], (err, rows, _)=>{
            if (err){
                rejects(err)
            }
            else {
                resolve(rows[0])
            }
        })
    }) 
}

function obtenerPorHashtag(hashtag) {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerHashtagPorHashtag(?)', [hashtag], (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[0])
            }
        })
    }) 
}

function obtenerPopulares() {
    return new Promise( (resolve, reject) => {
        dbConnection.query('call obtenerHashtagsPopulares()', (err, rows, _) => {
            if (err) {
                reject(err)
            }
            else {
                resolve(rows[0])
            }
        })
    }) 
}

module.exports = {crear, obtenerPorQuack, obtenerPorHashtag, obtenerPopulares}