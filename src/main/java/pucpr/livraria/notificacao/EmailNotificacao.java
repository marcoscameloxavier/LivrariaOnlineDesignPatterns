package pucpr.livraria.notificacao;

import pucpr.livraria.entity.Cliente;

public class EmailNotificacao implements Notificacao{
    @Override
    public String enviar(String mensagem, Cliente cliente) {
        String mensagemEnviada = "Enviando email para " + cliente.getEmail() + ": " + mensagem;
        System.out.println(mensagemEnviada);
        return mensagemEnviada;
    }
}
