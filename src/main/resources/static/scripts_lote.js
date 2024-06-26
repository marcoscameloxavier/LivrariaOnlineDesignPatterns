document.addEventListener('DOMContentLoaded', () => {
    const searchButton = document.getElementById('searchButton');
    const criarPedidoButton = document.getElementById('criarPedidoButton');
    const searchInput = document.getElementById('searchInput');
    const livrosContainer = document.getElementById('livrosContainer');
    const resultadoPedido = document.getElementById('resultadoPedido');
    const dadosClienteContainer = document.getElementById('dadosCliente');
    const resultadoProcessamento = document.getElementById('resultadoProcessamento');
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

        pedidos.forEach(pedido => {
            fetch('/addPedidoFila', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(pedido)
            })
                .then(response => response.json())
                .then(pedido_retorno => {
                    const pesoTotal = (pedido_retorno.peso / 1000).toFixed(2);
                    resultadoPedido.innerHTML += `<p>Pedido de ID = ${pedido_retorno.id} adicionado à fila de processamento. Peso total dos itens do pedido: ${pesoTotal} kg</p>`;
                })
                .catch(error => {
                    console.error('Erro ao criar pedido:', error);
                    resultadoPedido.innerHTML = `<p>Erro ao criar pedido.</p>`;
                });
        });
    });

    const scrollToBottom = (element) => {
        element.scrollTop = element.scrollHeight;
    };

    // Usar MutationObserver para observar mudanças na div
    const observer = new MutationObserver(() => scrollToBottom(resultadoProcessamento));
    observer.observe(resultadoProcessamento, { childList: true });

    const connectSSE = () => {
        const eventSource = new EventSource('/pedidos/processados');
        eventSource.onmessage = function(event) {
            if (event.data !== "") {
                resultadoProcessamento.innerHTML += `<p>${event.data}</p>`;
            }
        };
        eventSource.onerror = function(err) {
            console.error('Erro na conexão SSE:', err);
            eventSource.close();
            setTimeout(connectSSE, 3000); // Tentar reconectar após 3 segundos
        };
    };

    // Conectar ao SSE ao carregar a página
    connectSSE();
});
