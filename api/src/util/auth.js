const jwt = require("jsonwebtoken");

function firmar(user, expiration, cb){

    if(typeof expiration === 'function'){
        jwt.sign({user}, 'password', expiration)
    }
    else{
        jwt.sign({user}, 'password' , {expiresIn: expiration} , cb)
    }
}

function comprobarToken(req, res, callback){
    const authToken = req.headers['auth'];

    if(typeof authToken !== 'undefined'){    
        jwt.verify(authToken, 'password', (err, datosUsuario) => {
            if(err){
                res.status(403).json({Mensaje: 'Token Invalido'})
            }
            else{
                req.user = datosUsuario.user
                callback()
            }
        })
    }
    else{
        res.status(403).json({Mensaje: 'Necesita proporcionar un token'})
    }
}


module.exports = {firmar, comprobarToken}