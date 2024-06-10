package pucpr.livraria.notificacao;

import pucpr.livraria.entity.Cliente;

public class NotificacaoRequest {
    private TipoNotificacao tipoNotificacao;
    private String mensagem;
    private Cliente cliente;

    // Getters and Setters
    public TipoNotificacao getTipoNotificacao() {
        return tipoNotificacao;
    }

    public void setTipoNotificacao(TipoNotificacao tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
