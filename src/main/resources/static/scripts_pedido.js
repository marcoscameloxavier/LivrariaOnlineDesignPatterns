document.addEventListener('DOMContentLoaded', () => {
    const searchButton = document.getElementById('searchButton');
    const criarPedidoButton = document.getElementById('criarPedidoButton');
    const processarPedidoButton = document.getElementById('processarPedidoButton');
    const searchInput = document.getElementById('searchInput');
    const tipoEntrega = document.getElementById('tipoEntrega');
    const livrosContainer = document.getElementById('livrosContainer');
    const resultadoPedido = document.getElementById('resultadoPedido');
    let livrosSelecionados = [];

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
        const tipo = tipoEntrega.value;
        const cpf = "123.456.789-00"; // CPF fixo para demonstração

        if (livrosSelecionados.length > 0) {
            const entrega = { tipo: tipo };
            const requestBody = {
                cpf: cpf,
                livros: livrosSelecionados,
                entrega: entrega
            };

            fetch('/enviarPedido', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            })
                .then(response => response.json())
                .then(pedido => {
                    resultadoPedido.innerHTML = `<p>Pedido criado com sucesso. ID do pedido: ${pedido.id}</p>`;
                })
                .catch(error => {
                    console.error('Erro ao criar pedido:', error);
                    resultadoPedido.innerHTML = `<p>Erro ao criar pedido.</p>`;
                });
        }
    });

    processarPedidoButton.addEventListener('click', () => {
        fetch('/processarPedido', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ cpf: "111.111.111-11" })
        })
            .then(response => response.json())
            .then(data => {
                resultadoPedido.innerHTML += `<p>${data.resultado}</p>`;
            })
            .catch(error => {
                console.error('Erro ao processar pedido:', error);
                resultadoPedido.innerHTML += `<p>Erro ao processar pedido.</p>`;
            });
    });
});
