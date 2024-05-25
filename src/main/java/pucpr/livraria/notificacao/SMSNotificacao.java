package pucpr.livraria.notificacao;

import pucpr.livraria.entity.Cliente;

public class SMSNotificacao implements Notificacao{
    @Override
    public void enviar(String mensagem, Cliente cliente) {
        System.out.println("Enviando SMS para " + cliente.getTelefone() + ": " + mensagem);
    }
}
