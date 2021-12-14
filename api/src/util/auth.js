const jwt = require("jsonwebtoken");
const path = require('path');
const { FORBIDEN } = require("./httpResponseCodes");
require(path.join(__dirname, '..', 'util', 'httpResponseCodes.js'))

// Funciones para autenticar al usuario

function firmar(user, expiration, callback) {
    if (typeof expiration === 'function') {
        jwt.sign({user}, 'password', expiration)
    }
    else {
        jwt.sign({user}, 'password' , {expiresIn: expiration} , callback)
    }
}

function comprobarToken(req, res, callback) {
    const authToken = req.headers['auth'];

    if (typeof authToken !== 'undefined') {    
        jwt.verify(authToken, 'password', (err, datosUsuario) => {
            if (err) {
                res.status(FORBIDEN).json({Mensaje: 'Token Invalido'})
            }
            else {
                req.user = datosUsuario.user
                callback()
            }
        })
    }
    else {
        res.status(FORBIDEN).json({Mensaje: 'Necesita proporcionar un token'})
    }
}

function comprobarNivelToken(req, res, callback) {
    //ToDo: Niveles, Admin o usuario normal
}

module.exports = {firmar, comprobarToken, comprobarNivelToken}