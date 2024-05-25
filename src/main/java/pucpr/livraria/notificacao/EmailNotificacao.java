package pucpr.livraria.notificacao;

import pucpr.livraria.entity.Cliente;

public class EmailNotificacao implements Notificacao{
    @Override
    public void enviar(String mensagem, Cliente cliente) {
        System.out.println("Enviando email para " + cliente.getEmail() + ": " + mensagem);
    }
}
