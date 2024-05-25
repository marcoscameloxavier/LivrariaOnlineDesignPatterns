package pucpr.livraria.notificacao;

public class ConcreteNotificacaoFactory implements NotificacaoFactory {
    @Override
    public Notificacao criarNotificacao(TipoNotificacao tipo) {
        switch (tipo) {
            case EMAIL:
                return new EmailNotificacao();
            case SMS:
                return new SMSNotificacao();
            case WHATSAPP:
                return new WhatsAppNotificacao();
            default:
                throw new IllegalArgumentException("Tipo de notificação não suportado.");
        }
    }
}
