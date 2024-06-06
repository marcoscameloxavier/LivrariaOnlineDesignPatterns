package pucpr.livraria.notificacao;

public class WhatsAppNotificacaoFactory extends NotificacaoFactory {
    @Override
    public Notificacao criarNotificacao() {
        return new WhatsAppNotificacao();
    }
}
