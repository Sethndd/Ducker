const express = require('express');
const multer = require("multer");
const path = require("path");
const cors = require('cors');

const app = express();

app.set('port', process.env.PORT || 1807);

app.use(cors());

// storage engine 
const storage = multer.diskStorage({
    destination: path.join(__dirname, '/imagenes'),
    filename: (req, file, cb) => {
        return cb(null, `${file.fieldname}_${Date.now()}${path.extname(file.originalname)}`)
    }
})

const uploadMulter = multer({
    storage: storage,
    limits: {
        fileSize: 3145728
    }
})

//routes
app.use('/imagenes', express.static(path.join(__dirname, '/imagenes')));

app.post("/subir", uploadMulter.single('imagen'), (req, res) => {
    res.status(200).json({
        file: `/imagenes/${req.file.filename}`
    })
})

function errHandler(err, req, res, next) {
    if (err instanceof multer.MulterError) {
        res.status(400).json({
            message: err.message
        })
    }
}

app.use(errHandler);


//Starting app
app.listen(app.get('port'), () => {
    console.log('Server listening port', app.get('port'))
})