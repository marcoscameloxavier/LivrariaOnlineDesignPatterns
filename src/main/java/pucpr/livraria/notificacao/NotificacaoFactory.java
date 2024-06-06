package pucpr.livraria.notificacao;

public abstract class NotificacaoFactory {
    public abstract Notificacao criarNotificacao();

    public static NotificacaoFactory getFactory(TipoNotificacao tipo) {
        switch (tipo) {
            case EMAIL:
                return new EmailNotificacaoFactory();
            case SMS:
                return new SMSNotificacaoFactory();
            case WHATSAPP:
                return new WhatsAppNotificacaoFactory();
            default:
                throw new IllegalArgumentException("Tipo de notificação não suportado.");
        }
    }
}
