package pucpr.livraria.notificacao;

import pucpr.livraria.entity.Cliente;

public interface Notificacao {
    void enviar(String mensagem, Cliente cliente);
}