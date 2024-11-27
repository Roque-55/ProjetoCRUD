const url = "http://localhost:7000/";
const urlClientes = "http://localhost:7000/clientes";

$("#adicionarClienteBtn").on("click", function (e) {
    limpaCampos();
    $("#mdCadastrarCliente").removeClass("hidden");
})

$("#btnFinalizar").on("click", async function (e) {
    e.preventDefault(); // Evita o recarregamento da página

    const cliente = {
        nome: $("#nome").val(),
        dataNascimento: $("#dataNascimento").val(),
        cpf: $("#cpf").val(),
        genero: $("#genero").val(),
        email: $("#email").val(),
        ranking: $("#ranking").val(),
        senha: $("#senha").val(),
        confirmarSenha: $("#confirmaSenha").val()
    };

    try {
        // Envia os dados do cliente
        const response = await axios.put("/clientes/", cliente);

        // Extrai o ID do cliente da resposta
        const clienteId = response.data.split(": ")[1]; // Supondo que a mensagem seja "Cliente salvo com sucesso! ID: {id}"

        // Cria o objeto de telefone
        const telefone = {
            ddd: $("#ddd").val(),
            numero: $("#numeroTelefone").val(),
            tipo: $("#tipoTelefone").val(),
            clienteId: clienteId
        };

        // Cria os objetos de endereço
        const enderecoC = {
            apelido: $("#apelidoC").val(),
            cep: $("#cepC").val(),
            tipoEndereco: $("#tipoEnderecoC").val(),
            tipoResidencia: $("#tipoResidenciaC").val(),
            tipoLogradouro: $("#tipoLogradouroC").val(),
            logradouro: $("#logradouroC").val(),
            numero: $("#numeroC").val(),
            bairro: $("#bairroC").val(),
            cidade: $("#cidadeC").val(),
            observacoes: $("#observacoesC").val(),
        };

        const enderecoE = {
            apelido: $("#apelidoE").val(),
            cep: $("#cepE").val(),
            tipoEndereco: $("#tipoEnderecoE").val(),
            tipoResidencia: $("#tipoResidenciaE").val(),
            tipoLogradouro: $("#tipoLogradouroE").val(),
            logradouro: $("#logradouroE").val(),
            numero: $("#numeroE").val(),
            bairro: $("#bairroE").val(),
            cidade: $("#cidadeE").val(),
            observacoes: $("#observacoesE").val(),
        };

        // Envia os dados de telefone e endereços
        await axios.put("/telefones/", telefone);
        await axios.put(`/enderecos/${clienteId}`, enderecoC);
        await axios.put(`/enderecos/${clienteId}`, enderecoE);

        // Exibe mensagem de sucesso e fecha o modal
        alert("Cliente cadastrado com sucesso!");
        $("#mdAdicionarEnderecoE").addClass("hidden");
        carregarClientes();
    } catch (error) {
        if (error.response && error.response.status === 400) {
            alert(error.response.data); // Exibe a mensagem de erro do backend
        } else {
            console.error("Erro ao cadastrar o cliente:", error);
            alert("Erro ao cadastrar o cliente. Tente novamente mais tarde.");
        }
    }
});



function limpaCampos(){
    $("#nome").val("");
    $("#dataNascimento").val("");
    $("#cpf").val("");
    $("#genero").val("Feminino");
    $("#email").val("");
    $("#ranking").val("");
    $("#senha").val("");
    $("#confirmaSenha").val("");

    $("#ddd").val("");
    $("#numeroTelefone").val("");
    $("#tipoTelefone").val("Celular");

    $("#apelidoC").val("");
    $("#cepC").val("");
    $("#TipoEnderecoC").val("Cobranca");
    $("#TipoResidenciaC").val("Casa");
    $("#TipoLogradouroC").val("Rua");
    $("#logradouroC").val("");
    $("#numeroC").val("");
    $("#bairroC").val("");
    $("#observacoesC").val("");
    carregaPaisesC()

    $("#apelidoE").val("");
    $("#cepE").val("");
    $("#TipoEnderecoE").val("Cobranca");
    $("#TipoResidenciaE").val("Casa");
    $("#TipoLogradouroE").val("Rua");
    $("#logradouroE").val("");
    $("#numeroE").val("");
    $("#bairroE").val("");
    $("#observacoesE").val("");
    carregaPaisesE()
}

$("#btnCancelarCadastro").on("click", function (e) {
    $("#mdCadastrarCliente").addClass("hidden");
})

$("#btnVoltar2").on("click", function (e) {
    $("#mdCadastrarCliente").removeClass("hidden");
    $("#mdAdicionarEnderecoC").addClass("hidden");
})

$("#btnVoltar3").on("click", function (e) {
    $("#mdAdicionarEnderecoC").removeClass("hidden");
    $("#mdAdicionarEnderecoE").addClass("hidden");
})

$("#btnProximo1").on("click", function (e) {
    $("#mdCadastrarCliente").addClass("hidden");
    $("#mdAdicionarEnderecoC").removeClass("hidden");
})

$("#btnProximo2").on("click", function (e) {
    $("#mdAdicionarEnderecoC").addClass("hidden");
    $("#mdAdicionarEnderecoE").removeClass("hidden");
})

$("#paisC").on("change", async function () {
    await carregaEstadosC($("#paisC").val());
});

$("#paisE").on("change", async function () {
    await carregaEstadosE($("#paisE").val());
});

$("#estadoC").on("change", function () {
    carregaCidadesC($("#estadoC").val())
})

$("#estadoE").on("change", function () {
    carregaCidadesE($("#estadoE").val())
})

async function carregaPaisesC(){
    if ($("#paisC").length) {
        $("#paisC").empty();
    }

    axios.get(`http://localhost:7000/paises`)
        .then(function (response){
            const paises = response.data;

            paises.forEach(pais => {
                const cdg = `<option value="${pais.id}">${pais.nome}</option>"`;
                $("#paisC").append(cdg);
            })

            carregaEstadosC($("#paisC").val());
        })
        .catch(function (error) {
            console.error("Erro ao carregar os paises:", error);
        });
}

async function carregaPaisesE(){
    if ($("#paisE").length) {
        $("#paisE").empty();
    }

    axios.get(`http://localhost:7000/paises`)
        .then(function (response){
            const paises = response.data;

            paises.forEach(pais => {
                const cdg = `<option value="${pais.id}">${pais.nome}</option>"`;
                $("#paisE").append(cdg);
            })

            carregaEstadosE($("#paisE").val());
        })
        .catch(function (error) {
            console.error("Erro ao carregar os paises:", error);
        });
}

async function carregaEstadosC(id) {
    id = id == null ? 0 : id;

    if ($("#estadoC").length) {
        $("#estadoC").empty();
    }

    await axios.get(`http://localhost:7000/estados/${id}`)
        .then(response => {
            const estados = response.data;
            estados.forEach(estado => {
                const cdg = `<option value="${estado.id}">${estado.nome}</option>"`;
                $("#estadoC").append(cdg);
            })
            carregaCidadesC($("#estadoC").val());
        })
        .catch(function (error) {
            console.error("Erro ao carregar os estados:", error);
        })
}

async function carregaEstadosE(id) {
    id = id == null ? 0 : id;

    if ($("#estadoE").length) {
        $("#estadoE").empty();
    }

    await axios.get(`http://localhost:7000/estados/${id}`)
        .then(response => {
            const estados = response.data;
            estados.forEach(estado => {
                const cdg = `<option value="${estado.id}">${estado.nome}</option>"`;
                $("#estadoE").append(cdg);
            })
            carregaCidadesE($("#estadoE").val());
        })
        .catch(function (error) {
            console.error("Erro ao carregar os estados:", error);
        })
}

async function carregaCidadesC(id) {
    id = id == null ? 0 : id;

    if ($("#cidadeC").length) {
        $("#cidadeC").empty();
    }

    await axios.get(`http://localhost:7000/cidades/${id}`)
        .then(response => {
            const cidades = response.data;

            cidades.forEach(cidade => {
                const cdg = `<option value="${cidade.id}">${cidade.nome}</option>"`;
                $("#cidadeC").append(cdg);
            })
        })
        .catch(function (error) {
            console.error("Erro ao carregar as cidades:", error);
        })
}

async function carregaCidadesE(id) {
    id = id == null ? 0 : id;

    if ($("#cidadeE").length) {
        $("#cidadeE").empty();
    }

    await axios.get(`http://localhost:7000/cidades/${id}`)
        .then(response => {
            const cidades = response.data;

            cidades.forEach(cidade => {
                const cdg = `<option value="${cidade.id}">${cidade.nome}</option>"`;
                $("#cidadeE").append(cdg);
            })
        })
        .catch(function (error) {
            console.error("Erro ao carregar as cidades:", error);
        })
}

function carregarClientes() {
    var pesquisa = $("#txt-pesquisa").val();
    const pesquisaUrl = `${urlClientes}/${pesquisa}`;

    axios.get(pesquisaUrl)
        .then(function (response) {
            const clientes = response.data; // A resposta vem como um array de objetos
            const $tbody = $("tbody");
            $("tbody").empty(); // Limpa a tabela antes de adicionar os novos dados

            // Preenche a tabela com os clientes
            clientes.forEach(cliente => {
                const switchClass = cliente.status ? "active" : "inactive";
                let cpfCompleto = cliente.cpf;
                let cpfLimpo = cpfCompleto.substring(0,3) + "." + cpfCompleto.substring(3,6) + "." + cpfCompleto.substring(6,9) + "-" + cpfCompleto.substring(9);

                const linha = `
                        <tr>
                            <td class="cln-id">${cliente.id}</td>
                            <td class="cln-nome">${cliente.nome}</td>
                            <td class="cln-cpf">${cpfLimpo}</td>
                            <td class="cln-email">${cliente.email}</td>
                            <td class="acoes">
                                <button class="delete-btn" data-id="${cliente.id}">
                                    <img src="../icons/Lixo.svg" alt="Lixo" width="20" height="20">
                                </button>
                                <button class="view-btn" data-id="${cliente.id}">
                                    <img src="../icons/Olho.svg" alt="Olho" width="25">
                                </button>
                                <button class="switch-btn ${switchClass}" data-id="${cliente.id}">
                                    <div class="bolaSwitch"></div>
                                </button>
                            </td>
                        </tr>
                    `;
                $tbody.append(linha);
            });
        })
        .catch(function (error) {
            console.error("Erro ao carregar os clientes:", error);
        });
}

$(document).ready(function () {
    carregarClientes();
    let timeout;

    $("#txt-pesquisa").on("input", function () {
        clearTimeout(timeout);
        timeout = setTimeout(function () {
            carregarClientes();
        }, 300);
    })


    carregarClientes();

    $("tbody").on("click", ".delete-btn", function (e) {
        $("#md_excluir").removeClass("hidden");

        const id = $(this).data("id");
        const linha = $(this);
        const deleteUrl = `${urlClientes}/${id}`;
        $("#btnExcluir").off("click").on("click", async function () {
            try {
                const response = await axios.delete(deleteUrl);
                console.log("Cliente excluído com sucesso");
                linha.closest("tr").remove();
                $("#md_excluir").addClass("hidden");
            } catch (error) {
                console.error("Erro ao excluir o cliente:", error);
            }
        });

        $("#btnCancelarExcluir").off("click").on("click", function () {
            $("#md_excluir").addClass("hidden");
        });
    });


    $("tbody").on("click", ".switch-btn ", async function () {
        const id = $(this).data("id");
        const $btn = $(this);
        const isActive = $btn.hasClass("active");

        try {
            await axios.patch(`${urlClientes}/${id}/status`, !isActive);
            if (isActive) {
                $btn.removeClass("active").addClass("inactive");
            } else {
                $btn.removeClass("inactive").addClass("active");
            }

            console.log(`Cliente ${id} foi ${!isActive ? "ativado" : "inativado"}.`);
        } catch (error) {
            console.error("Erro ao atualizar o status do cliente:", error);
        }
    });

    $("tbody").on("click", "tr", function (e) {
        // Verifica se o alvo do clique é um botão ou um elemento filho dele
        if ($(e.target).hasClass("switch-btn") || $(e.target).closest(".switch-btn").length > 0) {
            return; // Impede a execução do redirecionamento
        }

        if ($(e.target).hasClass("delete-btn") || $(e.target).closest(".delete-btn").length > 0) {
            return; // Impede a execução do redirecionamento
        }

        const id = $(this).find(".view-btn").data("id");
        if (id) {
            window.location.href = `detalhes.html?id=${id}`;
        }
    });
});


