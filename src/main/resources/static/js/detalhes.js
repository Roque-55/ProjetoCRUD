const url = "http://localhost:7000/";

$("#btnVoltar").click(function (e) {
    window.location.href = url + "html/listagem.html"
});

$("#guiaDadosPessoais").click(function (e) {
    limpaTela();
    $(this).addClass("active");
    $("#dadosPessoais").removeClass("hide");
});

$("#guiaEnderecos").click(function (e) {
    limpaTela();
    $(this).addClass("active");
    $("#enderecos").removeClass("hide");
});

$("#guiaCartoes").click(function (e) {
    limpaTela();
    $(this).addClass("active");
    $("#cartoes").removeClass("hide");
});

$("#editarDados").on("click", function (e) {
    $('#mdEditarDados').removeClass("hidden");
})

$('#btnEditarDados').on('click', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get("id");

    const clienteAtualizado = {
        nome: $('#nome').val(),
        dataNascimento: $('#dataNascimento').val(),
        cpf: $('#cpf').val(),
        genero: $('#genero').val(),
        email: $('#email').val(),
        ranking: $('#ranking').val(),
    };

    axios.patch(`/clientes/${id}`, clienteAtualizado)
        .then(response => {
            alert('Dados atualizados com sucesso!');
            recebeDados(id)
            $('#mdEditarDados').addClass("hidden");
        })
        .catch(error => {
            console.error('Erro ao atualizar os dados do cliente:', error);
            alert('Ocorreu um erro ao salvar os dados.');
        });
});

$('#btnCancelarEditarDados').on('click', function() {
    $('#mdEditarDados').addClass("hidden");
});

$("#novoEndereco").on("click", function (e){
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get("id");

    $("#nApelido").val("");
    $("#nCep").val("");
    $("#nLogradouro").val("");
    $("#nNumero").val("");
    $("#nBairro").val("");
    carregaPaisesNovo();
    $('#mdAdicionarEndereco').removeClass("hidden");

    $("#btnCadastrarEndereco").off("click").on("click", async function () {
        const endereco = {
            apelido: $("#nApelido").val(),
            cep: $("#nCep").val(),
            tipoEndereco: $("#nTipoEndereco").val(),
            tipoResidencia: $("#nTipoResidencia").val(),
            tipoLogradouro: $("#nTipoLogradouro").val(),
            logradouro: $("#nLogradouro").val(),
            numero: $("#nNumero").val(),
            bairro: $("#nBairro").val(),
            cidade: $("#nCidade").val(),
            observacoes: $("#nObservacoes").val(),
        }

        try {
            const response = await axios.put(`/enderecos/${id}`, endereco);

            // Fecha o modal e exibe mensagem de sucesso
            $('#mdAdicionarEndereco').addClass("hidden");
            alert(response.data); // Deve exibir "Endereço salvo com sucesso!"
            recebeEnderecos(id);
        } catch (error) {
            if (error.response && error.response.status === 400) {
                alert(error.response.data); // Exibe a mensagem de erro do servidor
            } else {
                console.error("Erro ao salvar o endereço:", error);
                alert("Erro ao salvar o endereço. Tente novamente mais tarde.");
            }
        }
    });

    $("#btnCancelarCadastrarEndereco").off("click").on("click", function () {
        $("#mdAdicionarEndereco").addClass("hidden");
    });

})

$("#novoCartao").on("click", function (e){
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get("id");

    $("#nomeImpresso").val("");
    $("#numeroCartao").val("");
    $("#cvv").val("");
    $("#bandeira").val("VISA");

    $('#mdAdicioanarCartao').removeClass("hidden");

    $("#btnAdicionarCartao").off("click").on("click", async function () {
        const cartao = {
            nomeImpresso: $("#nomeImpresso").val(),
            numero: $("#numeroCartao").val(),
            cvv: $("#cvv").val(),
            bandeiraCartao: $("#bandeira").val(),
            preferencial: false,
        }

        try {
            const response = await axios.put(`/cartoes/${id}`, cartao);
            // Fecha o modal e exibe mensagem de sucesso
            $('#mdAdicioanarCartao').addClass("hidden");
            alert(response.data);
            recebeCartoes(id);
        } catch (error) {
            if (error.response && error.response.status === 400) {
                alert(error.response.data); // Exibe a mensagem de erro do servidor
            } else {
                console.error("Erro ao salvar o cartao:", error);
                alert("Erro ao salvar o cartao. Tente novamente mais tarde.");
            }
        }
    });

    $("#btnCancelarCadastrarEndereco").off("click").on("click", function () {
        $("#mdAdicionarEndereco").addClass("hidden");
    });

})

$("#pais").on("change", async function () {
    await carregaEstados($("#pais").val());
})

$("#estado").on("change", function () {
    carregaCidades($("#estado").val())
})

$("#nPais").on("change", async function () {
    await carregaEstadosNovo($("#nPais").val());
})

$("#nEstado").on("change", function () {
    carregaCidadesNovo($("#nEstado").val())
})

async function carregaPaises(){
    if ($("#pais").length) {
        $("#pais").empty();
    }

    axios.get(`http://localhost:7000/paises`)
        .then(function (response){
            const paises = response.data;

            paises.forEach(pais => {
                const cdg = `<option value="${pais.id}">${pais.nome}</option>"`;
                $("#pais").append(cdg);
            })

            carregaEstados($("#pais").val());
        })
        .catch(function (error) {
            console.error("Erro ao carregar os paises:", error);
        });
}

async function carregaEstados(id) {
    id = id == null ? 0 : id;

    if ($("#estado").length) {
        $("#estado").empty();
    }

    await axios.get(`http://localhost:7000/estados/${id}`)
        .then(response => {
            const estados = response.data;

            estados.forEach(estado => {
                const cdg = `<option value="${estado.id}">${estado.nome}</option>"`;
                $("#estado").append(cdg);
            })

            carregaCidades($("#estado").val());
        })
        .catch(function (error) {
            console.error("Erro ao carregar os estados:", error);
        })
}

async function carregaCidades(id) {
    id = id == null ? 0 : id;

    if ($("#cidade").length) {
        $("#cidade").empty();
    }

    await axios.get(`http://localhost:7000/cidades/${id}`)
        .then(response => {
            const cidades = response.data;

            cidades.forEach(cidade => {
                const cdg = `<option value="${cidade.id}">${cidade.nome}</option>"`;
                $("#cidade").append(cdg);
            })
        })
        .catch(function (error) {
            console.error("Erro ao carregar as cidades:", error);
        })
}

async function carregaPaisesNovo(){
    if ($("#nPais").length) {
        $("#nPais").empty();
    }

    axios.get(`http://localhost:7000/paises`)
        .then(function (response){
            const paises = response.data;

            paises.forEach(pais => {
                const cdg = `<option value="${pais.id}">${pais.nome}</option>"`;
                $("#nPais").append(cdg);
            })

            carregaEstadosNovo($("#nPais").val());
        })
        .catch(function (error) {
            console.error("Erro ao carregar os paises:", error);
        });
}

async function carregaEstadosNovo(id) {
    id = id == null ? 0 : id;

    if ($("#nEstado").length) {
        $("#nEstado").empty();
    }

    await axios.get(`http://localhost:7000/estados/${id}`)
        .then(response => {
            const estados = response.data;

            estados.forEach(estado => {
                const cdg = `<option value="${estado.id}">${estado.nome}</option>"`;
                $("#nEstado").append(cdg);
            })

            carregaCidadesNovo($("#nEstado").val());
        })
        .catch(function (error) {
            console.error("Erro ao carregar os estados:", error);
        })
}

async function carregaCidadesNovo(id) {
    id = id == null ? 0 : id;

    if ($("#nCidade").length) {
        $("#nCidade").empty();
    }

    await axios.get(`http://localhost:7000/cidades/${id}`)
        .then(response => {
            const cidades = response.data;

            cidades.forEach(cidade => {
                const cdg = `<option value="${cidade.id}">${cidade.nome}</option>"`;
                $("#nCidade").append(cdg);
            })
        })
        .catch(function (error) {
            console.error("Erro ao carregar as cidades:", error);
        })
}

function limpaTela(){
    $("#guiaDadosPessoais").removeClass("active");
    $("#guiaEnderecos").removeClass("active");
    $("#guiaCartoes").removeClass("active");
    $("#dadosPessoais").addClass("hide");
    $("#enderecos").addClass("hide");
    $("#cartoes").addClass("hide");
}

function recebeDados(id){
    axios.get(`http://localhost:7000/clientes/detalhes/${id}`) // Agora usa o path param diretamente
        .then(function (response) {
            const cliente = response.data;

            let dia = cliente.dataNascimento[2].toString();
            let mes = cliente.dataNascimento[1].toString();
            let ano = cliente.dataNascimento[0].toString();

            dia = dia.padStart(2, "0");
            mes = mes.padStart(2, "0");

            const dataNascimentoCompleta = ano + "-" + mes + "-" + dia;
            const dataNascimentoLimpa = dia + "/" + mes + "/" + ano;
            const cpfCompleto = cliente.cpf;
            const cpfLimpo = cpfCompleto.substring(0,3) + "." + cpfCompleto.substring(3,6) + "." + cpfCompleto.substring(6,9) + "-" + cpfCompleto.substring(9);

            $("#id").text(cliente.id);
            $("#lblNome").text(cliente.nome);
            $("#lblDataNascimento").text(dataNascimentoLimpa);
            $("#lblCpf").text(cpfLimpo);
            $("#lblGenero").text(cliente.genero);
            $("#lblEmail").text(cliente.email);
            $("#lblRanking").text(cliente.ranking);

            $("#nome").val(cliente.nome);
            $("#dataNascimento").val(dataNascimentoCompleta);
            $("#cpf").val(cliente.cpf);
            $("#genero").val(cliente.genero);
            $("#email").val(cliente.email);
            $("#ranking").val(cliente.ranking);
        })
        .catch(function (error) {
            console.error("Erro ao carregar os detalhes do cliente:", error);
        });

    axios.get(`http://localhost:7000/telefones/${id}`) // Agora usa o path param diretamente
        .then(function (response) {
            const telefone = response.data;

            const telefoneCompleto = telefone.numero;
            const telefoneLimpo = telefoneCompleto.substring(0, 5) + "-" + telefoneCompleto.substring(5);

            $("#lblTelefone").text( "(" + telefone.ddd + ") " + telefoneLimpo);
            $("#lblTipoTelefone").text(telefone.tipo);
        })
        .catch(function (error) {
            console.error("Erro ao carregar os detalhes do telefone:", error);
        });
}

function recebeCartoes(id){
    axios.get(`http://localhost:7000/cartoes/${id}`)
        .then(function (response) {
            const cartoes = response.data;
            const $div = $("#listaCartoes");
            $("#listaCartoes").empty()

            cartoes.forEach(cartao => {
                const cdgBtnPrefe = `<button class="acaoBtn btnPrefereCartao" data-id="${cartao.id}">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="19" height="13" viewBox="0 0 19 13" fill="none">
                                                    <path d="M17.5 1L6.5 12L1.5 7" stroke="#008000" stroke-width="2" stroke-linecap="round"
                                                    stroke-linejoin="round"/>
                                                </svg>
                                            </button>`;
                const btnPrefere = cartao.preferencial ? "" : cdgBtnPrefe;
                const ctn = `
                <div class="objeto">
                    <div class="linha">
                        <div class="infos">
                            Preferencial
                            <label>${cartao.preferencial ? "Sim" : "Não"}</label>
                        </div>
                        <div class="botoes">
                            ${btnPrefere}                            
                            <button class="acaoBtn btnExcluirCartao" data-id="${cartao.id}">
                                <svg xmlns="http://www.w3.org/2000/svg" width="21" height="21" viewBox="0 0 21 21" fill="none">
                                    <path d="M7.5 1.47906H13.5M1.5 4.47906H19.5M17.5 4.47906L16.8 14.9991C16.8403 16.013 16.67 17.0242 16.3 17.9691C15.9987 18.4943 15.5455 18.9161 15 19.179C14.0339 19.4852 13.0147 19.5874 12.007 19.4791H8.98901C7.98134 19.5874 6.96211 19.4852 5.99597 19.179C5.45045 18.9161 4.99726 18.4943 4.69598 17.9691C4.32593 17.0242 4.15568 16.013 4.19598 14.9991L3.5 4.47906M8.5 8.97906V13.9791M12.5 8.97906V13.9791" stroke="#A4161A" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                </svg>
                            </button>
                        </div>                        
                    </div>
                    <div class="linha">
                        <div class="infos">
                            Nome impresso
                            <label>${cartao.nomeImpresso}</label>
                        </div>
                        <div class="infos">
                            Número do cartão
                            <label>${cartao.numero}</label>
                        </div>
                    </div>
                    <div class="linha">
                        <div class="infos">
                            CVV
                            <label>${cartao.cvv}</label>
                        </div>
                        <div class="infos">
                            Bandeira do cartão
                            <label>${cartao.bandeiraCartao}</label>
                        </div>
                    </div>
                </div>
            `;
                $div.append(ctn);
            });
        })
        .catch(function (error) {
            console.error("Erro ao carregar os cartões do cliente:", error);
        });
}

function recebeEnderecos(id){
    axios.get(`http://localhost:7000/enderecos/${id}`)
        .then(function (response) {
            const enderecos = response.data;
            const $div = $("#listaEnderecos");
            $("#listaEnderecos").empty();

            enderecos.forEach(endereco => {
                let cepCompleto = endereco.cep;
                let cepLimpo = cepCompleto.substring(0, 5) + "-" + cepCompleto.substring(5);
                let observacoes = endereco.observacoes == null ? " " : endereco.observacoes;
                let tipoEndereco = endereco.tipoEndereco == "Cobranca" ? "Cobrança" : endereco.tipoEndereco;

                const end =  `
                        <div class="objeto">
                            <div class="linha">
                                <div class="infos">
                                    Apelido
                                    <label>${endereco.apelido}</label>
                                </div>
                                <div class="botoes">
                                    <button class="acaoBtn btnEditarEndereco" data-id="${endereco.id}">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" viewBox="0 0 23 23" fill="none">
                                            <path d="M10.4712 3.60368H6.3062C5.24982 3.48501 4.18028 3.59565 3.17054 3.92798C2.6111 4.21365 2.15612 4.6686 1.87045 5.22804C1.53815 6.23779 1.42754 7.30732 1.54618 8.3637V16.6938C1.42754 17.7502 1.53815 18.8197 1.87045 19.8294C2.15612 20.3889 2.6111 20.8438 3.17054 21.1295C4.18028 21.4618 5.24982 21.5725 6.3062 21.4538H14.6363C15.6927 21.5725 16.7622 21.4618 17.7719 21.1295C18.3314 20.8438 18.7864 20.3889 19.072 19.8294C19.4043 18.8197 19.5149 17.7502 19.3963 16.6938V12.5287M7.49622 15.5038H9.15726C9.47663 15.5215 9.79699 15.5033 10.1123 15.4492C10.315 15.4008 10.5088 15.3204 10.6864 15.2112C10.9477 15.0266 11.1872 14.813 11.4005 14.5746L20.8838 5.09119C21.2783 4.69667 21.5 4.1616 21.5 3.60368C21.5 3.04575 21.2783 2.51068 20.8838 2.11616C20.4893 1.72165 19.9542 1.5 19.3963 1.5C18.8384 1.5 18.3033 1.72165 17.9088 2.11616L8.42543 11.5995C8.18692 11.8127 7.97335 12.0523 7.78878 12.3136C7.67955 12.4912 7.5992 12.6849 7.55076 12.8877C7.49673 13.203 7.47844 13.5234 7.49622 13.8427V15.5038Z" stroke="#01497C" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        </svg>
                                    </button>
                                    
                                    <button class="acaoBtn btnExcluirEndereco" data-id="${endereco.id}">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="21" height="21" viewBox="0 0 21 21" fill="none">
                                            <path d="M7.5 1.47906H13.5M1.5 4.47906H19.5M17.5 4.47906L16.8 14.9991C16.8403 16.013 16.67 17.0242 16.3 17.9691C15.9987 18.4943 15.5455 18.9161 15 19.179C14.0339 19.4852 13.0147 19.5874 12.007 19.4791H8.98901C7.98134 19.5874 6.96211 19.4852 5.99597 19.179C5.45045 18.9161 4.99726 18.4943 4.69598 17.9691C4.32593 17.0242 4.15568 16.013 4.19598 14.9991L3.5 4.47906M8.5 8.97906V13.9791M12.5 8.97906V13.9791" stroke="#A4161A" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        </svg>
                                    </button>                                   
                                </div>                        
                            </div>
                        
                            <div class="linha">
                                <div class="infos">
                                    Cep
                                    <label>${cepLimpo}</label>
                                </div>
                                <div class="infos">
                                    Tipo de endereço
                                    <label>${tipoEndereco}</label>
                                </div>                     
                            </div>
                            
                            <div class="linha">
                                <div class="infos">
                                    Tipo de Residência
                                    <label>${endereco.tipoResidencia}</label>
                                </div>
                                <div class="infos">
                                    Tipo de logradouro
                                    <label>${endereco.tipoLogradouro}</label>
                                </div>                   
                            </div>
                            
                            <div class="linha">
                                <div class="infos">
                                    Logradouro
                                    <label>${endereco.logradouro}</label>
                                </div>
                                <div class="infos">
                                    Número
                                    <label>${endereco.numero}</label>
                                </div>                   
                            </div>
                            
                            <div class="linha">
                                <div class="infos">
                                    Bairro
                                    <label>${endereco.bairro}</label>
                                </div>
                                <div class="infos">
                                    Cidade
                                    <label>${endereco.cidade.nome}</label>
                                </div>                   
                            </div>
                            
                            <div class="linha">
                                <div class="infos">
                                    Estado
                                    <label>${endereco.cidade.estado.nome}</label>
                                </div>
                                <div class="infos">
                                    Pais
                                    <label>${endereco.cidade.estado.pais.nome}</label>
                                </div>                   
                            </div>
                            
                            <div class="infos">
                                    Observações
                                    <label>${observacoes}</label>
                            </div>
                                                        
                        </div>
                    `;
                $div.append(end);
            })
        })
        .catch(function (error) {
            console.error("Erro ao carregar os enderecos do cliente:", error);
        })
}

$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get("id"); // Pega o ID do cliente da URL

    if (id) {
        recebeDados(id);
        recebeCartoes(id);
        recebeEnderecos(id);
        carregaPaises();


        $("#listaCartoes").on("click", ".btnExcluirCartao", function (e) {
            e.preventDefault(); // Previne comportamentos padrão do botão

            $("#mdExcluirCartao").removeClass("hidden");

            const id = $(this).data("id"); // Pega o ID do cartão
            const $cartao = $(this).closest(".objeto"); // Seleciona o cartão correspondente

            // Configura o botão de confirmar exclusão no modal
            $("#btnExcluirCartao").off("click").on("click", async function () {
                try {
                    await axios.delete(`http://localhost:7000/cartoes/${id}`);
                    console.log("Cartão excluído com sucesso");
                    $cartao.remove(); // Remove o cartão correspondente da lista
                    $("#mdExcluirCartao").addClass("hidden"); // Esconde o modal
                } catch (error) {
                    console.error("Erro ao excluir o cartão:", error);
                }
            });

            // Configura o botão de cancelar no modal
            $("#btnCancelarExcluirCartao").off("click").on("click", function () {
                $("#mdExcluirCartao").addClass("hidden"); // Apenas esconde o modal
            });
        });

        $("#listaCartoes").on("click", ".btnPrefereCartao", async function (e) {
            e.preventDefault();
            const idCtn = $(this).data("id");
            try {
                await axios.patch(`http://localhost:7000/cartoes/${idCtn}/preferencial`);
                console.log("Cartão selecionado com sucesso");
                recebeCartoes(id);
            } catch (error) {
                console.error("Erro ao selecionar o cartão:", error);
            }
        });

        $("#listaEnderecos").on("click", ".btnEditarEndereco", async function (e) {
            e.preventDefault();
            const id = $(this).data("id");

            try {
                await axios.get(`http://localhost:7000/enderecos/seleciona/${id}`)
                    .then(response => {
                        const endereco = response.data;
                        $("#apelido").val(endereco.apelido);
                        $("#cep").val(endereco.cep);
                        $("#tipoEndereco").val(endereco.tipoEndereco);
                        $("#tipoResidencia").val(endereco.tipoResidencia);
                        $("#tipoLogradouro").val(endereco.tipoLogradouro);
                        $("#logradouro").val(endereco.logradouro);
                        $("#numero").val(endereco.numero);
                        $("#bairro").val(endereco.bairro);
                        $("#pais").val(endereco.cidade.estado.pais.id);
                        $("#estado").val(endereco.cidade.estado.id);
                        carregaCidades($("#estado").val())
                        $("#cidade").val(endereco.cidade.id);
                    })
            } catch (error) {
                console.error("Erro ao editar o endereco:", error);
            }

            $("#mdEditarEndereco").removeClass("hidden");

            $("#btnEditarEndereco").off("click").on("click", async function () {
                const enderecoAtualizado = {
                    apelido: $("#apelido").val(),
                    cep: $("#cep").val(),
                    tipoEndereco: $("#tipoEndereco").val(),
                    tipoResidencia: $("#tipoResidencia").val(),
                    tipoLogradouro: $("#tipoLogradouro").val(),
                    logradouro: $("#logradouro").val(),
                    numero: $("#numero").val(),
                    bairro: $("#bairro").val(),
                    cidade: $("#cidade").val(),
                    observacoes: $("#observacoes").val(),
                }

                try {
                    await axios.patch(`/enderecos/${id}`, enderecoAtualizado)
                        .then(response => {
                            alert('Endereco atualizado com sucesso!');
                            recebeEnderecos(id)
                            $('#mdEditarEndereco').addClass("hidden");
                        })
                } catch (error) {
                    console.error("Erro ao editar o endereco:", error);
                }
            });

            // Configura o botão de cancelar no modal
            $("#btnCancelarEditarEndereco").off("click").on("click", function () {
                $("#mdEditarEndereco").addClass("hidden"); // Apenas esconde o modal
            });
        });

        $("#listaEnderecos").on("click", ".btnExcluirEndereco", function (e) {
            e.preventDefault();

            $("#mdExcluirEndereco").removeClass("hidden");

            const id = $(this).data("id"); // Pega o ID do cartão
            const $endereco = $(this).closest(".objeto"); // Seleciona o cartão correspondente

            // Configura o botão de confirmar exclusão no modal
            $("#btnExcluirEndereco").off("click").on("click", async function () {
                try {
                    await axios.delete(`http://localhost:7000/enderecos/${id}`);
                    console.log("Cartão excluído com sucesso");
                    $endereco.remove(); // Remove o cartão correspondente da lista
                    $("#mdExcluirEndereco").addClass("hidden"); // Esconde o modal
                } catch (error) {
                    console.error("Erro ao excluir o endereço:", error);
                }
            });

            // Configura o botão de cancelar no modal
            $("#btnCancelarExcluirEndereco").off("click").on("click", function () {
                $("#mdExcluirEndereco").addClass("hidden"); // Apenas esconde o modal
            });
        });


    } else {
        console.error("Nenhum ID foi fornecido na URL.");
    }
});
