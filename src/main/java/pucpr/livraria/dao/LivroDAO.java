package pucpr.livraria.dao;

import pucpr.livraria.config.APIConfig;
import pucpr.livraria.entity.Livro;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class LivroDAO {
    //executa as consultas
    private final APIConfig config;

    public LivroDAO() {
        this.config = APIConfig.getInstancia();
    }

    public List<Livro> buscarLivros(String query) {
        List<Livro> livros = new ArrayList<>();
        String urlString = config.getApiUrl() + "/volumes?q=" + query + "&key=" + config.getApiKey();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                Scanner scanner = new Scanner(url.openStream());
                String inline = "";
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();

                // Parse the JSON response and convert it into a list of Livro objects
                // Aqui você precisa adicionar o código para analisar a resposta JSON e popular a lista de livros

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return livros;
    }
}
