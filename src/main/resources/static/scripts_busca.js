document.addEventListener('DOMContentLoaded', () => {
    const searchButton = document.getElementById('searchButton');
    const searchInput = document.getElementById('searchInput');
    const searchType = document.getElementById('searchType');
    const livrosContainer = document.getElementById('livrosContainer');

    // Função para buscar e exibir livros
    const buscarLivros = (query, type) => {
        document.getElementById("loading").style.display = "block";
        fetch(`/livros/${type}?${type}=${query}`)
            .then(response => response.json())
            .then(livros => {
                livrosContainer.innerHTML = '';
                livros.forEach(livro => {
                    const livroCard = document.createElement('div');
                    livroCard.classList.add('livro-card');
                    livroCard.innerHTML = `
                        <div class="livro-capa">
                        <img src="${livro.capa}" alt="Capa do livro">
                        </div>
                        <h2>${livro.titulo}</h2>
                        <p><strong>Autor:</strong> ${livro.autor}</p>
                        <p><strong>Gênero:</strong> ${livro.genero}</p>
                        <p><strong>Editora:</strong> ${livro.editora}</p>
                        <p><strong>Publicado em:</strong> ${livro.dataPublicacao}</p>
                        <p><strong>Páginas:</strong> ${livro.numPaginas}</p>
                        <p><strong>Preço:</strong> R$${livro.preco.toFixed(2)}</p>
                        <div class="sinopse">${livro.sinopse}</div>
                        <p><a href="${livro.previewLink}" target="_blank">Preview do Livro</a></p>
                    `;
                    livrosContainer.appendChild(livroCard);
                });
            });
        document.getElementById("loading").style.display = "none";
    };

    // Buscar livros ao clicar no botão de busca
    searchButton.addEventListener('click', () => {
        const query = searchInput.value;
        const type = searchType.value;
        if (query) {
            buscarLivros(query, type);
        }
    });

    // Carregar livros de título "Design Patterns" ao abrir a página
    buscarLivros("Engenharia de Software", "titulo");
});
