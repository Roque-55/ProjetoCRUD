const url = "http://localhost:7000/"

$("#btnEntrar").click(async function () {
    var email= $("#txtEmail").val()
    var senha= $("#txtSenha").val()


    await axios.post(url + 'login', {
        email:email,
        password:senha
    }).then(function (response) {
        window.location.href = url + "html/listagem.html";
    }).catch((error) => {
        if (error.response?.status === 401) {
            alert("Erro: Credenciais inválidas.");
        } else {
            alert("Erro desconhecido.");
        }
    });

});

let senhaVisivel = false;

$("#btnVerSenha").click(function (e) {
    e.preventDefault();

    senhaVisivel = !senhaVisivel;

    // Alterna o tipo do campo entre "password" e "text"
    $("#txtSenha").attr("type", senhaVisivel ? "text" : "password");
    $("#iconeSenha").attr("src", senhaVisivel ? "../icons/OlhoSelecionado.svg" : "../icons/Olho.svg");
});
  