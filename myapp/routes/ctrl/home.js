module.exports = {
    output : {
        index : (req,res)=>{res.render('index')},
        main : (req,res)=>{res.render('main')},
        login : (req,res)=>{res.render('login')}
    }
}