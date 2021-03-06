const multer = require("multer");

function errorMulter(err, req, res, next) {
    if (err instanceof multer.MulterError) {
        res.status(400).json({
            message: err.message
        })
    }
}

module.exports = {errorMulter} 