var express = require('express');
var app = express();
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const multer= require('multer');
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const uri='mongodb+srv://thondph16247:tho1234@cluster0.hn0wpto.mongodb.net/comic';


mongoose.connect(uri,{
    useNewUrlParser: true,
    useUnifiedTopology: true,
});

const db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function () {
  console.log('Connected to MongoDB Atlas');
});

const registrationSchema = new mongoose.Schema({
    username: String,
    password: String,
  });

  const Registration = mongoose.model('Registration', registrationSchema);

// Xử lý yêu cầu đăng ký từ ứng dụng Android
app.post('/register', async (req, res) => {
  try {
    const { username, password } = req.body;

    // Tạo một đối tượng Registration từ dữ liệu nhận được
    const newRegistration = new Registration({ username, password });

    // Lưu thông tin đăng ký vào MongoDB Atlas
    await newRegistration.save();

    res.status(200).send('Đăng ký thành công!');
  } catch (err) {
    console.error(err);
    res.status(500).send('Đăng ký thất bại!');
  }
});


/// đăng nhập

const userSchema = new mongoose.Schema({
    username: String,
    password: String,
  });
  
  const User = mongoose.model('User', userSchema);

// Middleware để xử lý dữ liệu từ body request
app.post('/login', async (req, res) => {
    const { username, password } = req.body;
  
    try {
      // Kiểm tra xem thông tin đăng nhập có trong MongoDB hay không
      const user = await Registration.findOne({ username, password });
  
      if (user) {
        // Thành công: Trả về mã thành công (200) và thông tin người dùng
        res.status(200).json({ message: 'Đăng nhập thành công', user });
      } else {
        // Thất bại: Trả về mã lỗi (401) và thông báo lỗi
        res.status(401).json({ message: 'Đăng nhập thất bại' });
      }
    } catch (error) {
      res.status(500).json({ message: 'Lỗi server' });
    }
});



  //==========================================


var apicomic= require('./Api/apiComic');
app.use('/api/comic',apicomic);



app.listen(8000,function() {
    console.log("Server is running on port 8000");
});