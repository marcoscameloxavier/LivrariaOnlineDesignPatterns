document.addEventListener('DOMContentLoaded', () => {
    const searchButton = document.getElementById('searchButton');
    const criarPedidoButton = document.getElementById('criarPedidoButton');
    const processarPedidoButton = document.getElementById('processarPedidoButton');
    const searchInput = document.getElementById('searchInput');
    const livrosContainer = document.getElementById('livrosContainer');
    const resultadoPedido = document.getElementById('resultadoPedido');
    const dadosClienteContainer = document.getElementById('dadosCliente');
    let livrosSelecionados = [];

    const cpf = "123.456.789-00"; // CPF fixo para demonstração

    // Buscar e exibir dados do cliente ao carregar a página
    fetch(`/clientes/cpf?cpf=${cpf}`)
        .then(response => response.json())
        .then(cliente => {
            dadosClienteContainer.innerHTML = `
                <p><strong>Nome:</strong> ${cliente.nome}</p>
                <p><strong>Email:</strong> ${cliente.email}</p>
                <p><strong>Telefone:</strong> ${cliente.telefone}</p>
                <p><strong>Endereço:</strong> ${cliente.endereco}</p>
                <p><strong>CPF:</strong> ${cliente.cpf}</p>
            `;
        })
        .catch(error => {
            console.error('Erro ao buscar dados do cliente:', error);
            dadosClienteContainer.innerHTML = `<p>Erro ao buscar dados do cliente.</p>`;
        });

    searchButton.addEventListener('click', () => {
        const query = searchInput.value;
        if (query) {
            fetch(`/livros/${query}`)
                .then(response => response.json())
                .then(livros => {
                    livrosContainer.innerHTML = '';
                    livrosSelecionados = livros;
                    livros.forEach(livro => {
                        const livroCard = document.createElement('div');
                        livroCard.classList.add('livro-card');
                        livroCard.innerHTML = `
                            <h3>${livro.titulo}</h3>
                            <p>Preço: R$${livro.preco.toFixed(2)} com ${livro.numPaginas} páginas</p>
                        `;
                        livrosContainer.appendChild(livroCard);
                    });
                });
        }
    });

    criarPedidoButton.addEventListener('click', () => {
        const tiposEntrega = ["ECONOMICA", "RAPIDA", "SEDEX"];
        const pedidos = livrosSelecionados.map(livro => {
            const tipoEntrega = tiposEntrega[Math.floor(Math.random() * tiposEntrega.length)];
            return {
                cpf: cpf,
                livros: [livro],
                entrega: { tipo: tipoEntrega }
            };
        });

        const pedidoPromises = pedidos.map(pedido => {
            return fetch('/addPedidoFila', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(pedido)
            })
                .then(response => response.json())
                .then(pedido_retorno => {
                    const pesoTotal = (pedido_retorno.peso / 1000).toFixed(2);
                    resultadoPedido.innerHTML += `<p>Pedido de ID = ${pedido_retorno.id} criado com sucesso e adicionado à fila de processamento. Peso total dos itens do pedido: ${pesoTotal} kg</p>`;
                })
                .catch(error => {
                    console.error('Erro ao criar pedido:', error);
                    resultadoPedido.innerHTML = `<p>Erro ao criar pedido.</p>`;
                });
        });

        // Usar Promise.all para garantir que todas as promessas de pedidos sejam resolvidas antes de processar
        Promise.all(pedidoPromises)
            .then(() => {
                console.log('Todos os pedidos foram criados e adicionados à fila.');
                // Habilitar o botão de processar pedidos após criar e adicionar todos os pedidos
                processarPedidoButton.disabled = false;
            })
            .catch(error => {
                console.error('Erro ao criar e adicionar pedidos à fila:', error);
                resultadoPedido.innerHTML += `<p>Erro ao criar e adicionar pedidos à fila.</p>`;
            });
    });

    processarPedidoButton.addEventListener('click', () => {
        fetch('/processarPedidos', {
            method: 'POST'
        })
            .then(response => response.text())
            .then(message => {
                resultadoPedido.innerHTML += `<p>${message}</p>`;
            })
            .catch(error => {
                console.error('Erro ao processar pedidos:', error);
                resultadoPedido.innerHTML += `<p>Erro ao processar pedidos.</p>`;
            });
    });

    // Inicialmente, o botão de processar pedidos deve estar desativado
    processarPedidoButton.disabled = true;
});
