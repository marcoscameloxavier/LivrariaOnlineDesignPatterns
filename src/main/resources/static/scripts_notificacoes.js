document.addEventListener('DOMContentLoaded', () => {
    const sendButton = document.getElementById('sendButton');
    const tipoNotificacao = document.getElementById('tipoNotificacao');
    const mensagem = document.getElementById('mensagem');
    const resultado = document.getElementById('resultado');

    const nomeCliente = document.getElementById('nomeCliente');
    const emailCliente = document.getElementById('emailCliente');
    const telefoneCliente = document.getElementById('telefoneCliente');
    const enderecoCliente = document.getElementById('enderecoCliente');
    const cpfCliente = document.getElementById('cpfCliente');
    const cepCliente = document.getElementById('cepCliente');

    sendButton.addEventListener('click', () => {
        const tipo = tipoNotificacao.value;
        const msg = mensagem.value;

        if (msg) {
            const cliente = {
                nome: nomeCliente.value,
                email: emailCliente.value,
                telefone: telefoneCliente.value,
                endereco: enderecoCliente.value,
                cpf: cpfCliente.value,
                cep: cepCliente.value
            };

            const requestBody = {
                tipoNotificacao: tipo,
                mensagem: msg,
                cliente: cliente
            };

            fetch('/enviarNotificacao', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            })
                .then(response => response.json())
                .then(data => {
                    resultado.innerHTML = `<p>${data.resultado}</p>`;
                })
                .catch(error => {
                    console.error('Erro ao enviar notificação:', error);
                    resultado.innerHTML = `<p>Erro ao enviar notificação.</p>`;
                });
        }
    });
});
