package pucpr.livraria.notificacao;

public abstract class NotificacaoFactory {
    public abstract Notificacao criarNotificacao(TipoNotificacao tipo);
}
