const url = "http://localhost:7000/"

$("#btnEntrar").click(async function () {
    console.log("Clique detectado");

        var email= $("#txtEmail").val()
        var senha= $("#txtSenha").val()
        

        await axios.post(url + 'login', {
            email:email,
            password:senha
        }).then(function (response) {
            alert("Usuario Logado com sucesso")
        }).catch(function (error) {
            alert(error);
        });
        
});
  