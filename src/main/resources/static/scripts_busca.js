document.addEventListener('DOMContentLoaded', () => {
    const searchButton = document.getElementById('searchButton');
    const searchInput = document.getElementById('searchInput');
    const searchType = document.getElementById('searchType');
    const livrosContainer = document.getElementById('livrosContainer');

    searchButton.addEventListener('click', () => {
        const query = searchInput.value;
        const type = searchType.value;
        if (query) {
            fetch(`/livros/${type}?${type}=${query}`)
                .then(response => response.json())
                .then(livros => {
                    livrosContainer.innerHTML = '';
                    livros.forEach(livro => {
                        const livroCard = document.createElement('div');
                        livroCard.classList.add('livro-card');
                        livroCard.innerHTML = `
                            <img src="${livro.capa}" alt="Capa do livro">
                            <h2>${livro.titulo}</h2>
                            <p><strong>Autor:</strong> ${livro.autor}</p>
                            <p><strong>Gênero:</strong> ${livro.genero}</p>
                            <p><strong>Editora:</strong> ${livro.editora}</p>
                            <p><strong>Publicado em:</strong> ${livro.dataPublicacao}</p>
                            <p><strong>Páginas:</strong> ${livro.numPaginas}</p>
                            <p><strong>Preço:</strong> R$${livro.preco.toFixed(2)}</p>
                            <p>${livro.sinopse}</p>
                            <p><a href="${livro.previewLink}" target="_blank">Preview do Livro</a></p>
                        `;
                        livrosContainer.appendChild(livroCard);
                    });
                });
        }
    });
});
