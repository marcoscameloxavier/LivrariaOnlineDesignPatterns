document.addEventListener('DOMContentLoaded', () => {
    const interessesForm = document.getElementById('interessesForm');
    const atualizarButton = document.getElementById('atualizar');
    const emailsDiv = document.getElementById('emails');
    const livrosDiv = document.getElementById('livros');
    let eventSource;

    atualizarButton.addEventListener('click', () => {
        const autor = document.getElementById('autor').value;
        const genero = document.getElementById('genero').value;
        const cpf = '222.222.222-22'; // CPF fixo para simulação

        fetch(`/clientes/listaInteresses?cpf=${cpf}&autor=${autor}&genero=${genero}`, {
            method: 'POST'
        }).then(response => response.json())
            .then(data => {
                if (eventSource) {
                    eventSource.close();
                }
                iniciarSSE();
            });
    });

    function iniciarSSE() {
        const cpf = '222.222.222-22'; // CPF fixo para simulação
        eventSource = new EventSource(`/clientes/listaInteresses/${cpf}`);

        eventSource.onmessage = function(event) {
            const livros = JSON.parse(event.data);
            const emailDiv = document.createElement('div');
            emailDiv.classList.add('email');

            const emailImg = document.createElement('img');
            emailImg.src = 'img/logo.png'; // Caminho para o ícone
            emailImg.alt = 'Ícone da página';

            const emailContent = document.createElement('div');
            emailContent.classList.add('email-content');
            emailContent.innerText = 'Nova recomendação recebida';

            const emailTime = document.createElement('div');
            emailTime.classList.add('email-time');
            const now = new Date();
            emailTime.innerText = `${now.getHours()}:${('0' + now.getMinutes()).slice(-2)}`;

            emailDiv.appendChild(emailImg);
            emailDiv.appendChild(emailContent);
            emailDiv.appendChild(emailTime);

            emailDiv.addEventListener('click', () => {
                livrosDiv.innerHTML = '';
                livros.forEach(livro => {
                    const livroDiv = document.createElement('div');
                    livroDiv.classList.add('livro');
                    livroDiv.innerHTML = `
                        <img src="${livro.capa}" alt="Capa do livro">
                        <div>
                            <h4>${livro.titulo}</h4>
                            <p><strong>Preço:</strong> R$${livro.preco.toFixed(2)}</p>
                            <p><a href="${livro.previewLink}" target="_blank">Preview do Livro</a></p>
                        </div>
                    `;
                    livrosDiv.appendChild(livroDiv);
                });
            });

            emailsDiv.appendChild(emailDiv);
        };

        eventSource.onerror = function(event) {
            console.error('Erro na conexão SSE:', event);
            eventSource.close();
        };
    }

    iniciarSSE(); // Iniciar SSE ao carregar a página
});
