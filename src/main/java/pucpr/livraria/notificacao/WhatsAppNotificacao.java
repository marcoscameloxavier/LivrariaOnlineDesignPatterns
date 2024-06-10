package pucpr.livraria.notificacao;

import pucpr.livraria.entity.Cliente;

public class WhatsAppNotificacao implements Notificacao{
    @Override
    public String enviar(String mensagem, Cliente cliente) {
        String mensagemEnviada = "Enviando WhatsApp para " + cliente.getTelefone() + ": " + mensagem;
        System.out.println(mensagemEnviada);
        return mensagemEnviada;
    }
}
