package pucpr.livraria.notificacao;

public interface  NotificacaoFactory {
    Notificacao criarNotificacao(TipoNotificacao tipo);
}
