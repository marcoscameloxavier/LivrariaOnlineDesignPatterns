package pucpr.livraria.notificacao;

import pucpr.livraria.entity.Cliente;

public class WhatsAppNotificacao implements Notificacao{
    @Override
    public void enviar(String mensagem, Cliente cliente) {
        System.out.println("Enviando WhatsApp para " + cliente.getTelefone() + ": " + mensagem);
    }
}
