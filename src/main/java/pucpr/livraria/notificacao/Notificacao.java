package pucpr.livraria.notificacao;

import pucpr.livraria.entity.Cliente;

public interface Notificacao {
    String enviar(String mensagem, Cliente cliente);
}