let logoCompleta = false;

$("#menuLateral").click(function (e) {
    $(this).toggleClass("expanded");
    logoCompleta = !logoCompleta;
    $("#logo").attr("src", logoCompleta ? "../icons/LogoCompleto.svg" : "../icons/LogoCompacto.svg");
});

$("#sair").click(function (e) {
    e.stopPropagation();
    $("#md_sair").removeClass("hidden");

    $("#btnSair").off("click").click(function (e) {
        window.location.href = url
    });

    $("#btnCancelarSair").off("click").click(function (e) {
        $("#md_sair").addClass("hidden");
    });
});