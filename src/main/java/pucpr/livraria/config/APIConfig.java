package pucpr.livraria.config;

public class APIConfig {
    //SINGLETON - conexão com a API de livros do Google, chave da API,
    private static APIConfig instancia;
    private String apiKey;
    private String apiUrl;

    private APIConfig() {
        // Configurações iniciais
        this.apiKey = "AIzaSyAzPMMGofkOJx-0Fb8uoutZV7apJKYCHqg";
        this.apiUrl = "https://www.googleapis.com/books/v1";
    }

    public static synchronized APIConfig getInstancia() {
        if (instancia == null) {
            instancia = new APIConfig();
        }
        return instancia;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }
}
