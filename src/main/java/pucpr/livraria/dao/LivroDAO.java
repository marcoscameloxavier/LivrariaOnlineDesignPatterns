package pucpr.livraria.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import pucpr.livraria.config.APIConfig;
import pucpr.livraria.entity.Livro;

public class LivroDAO {
    //executa as consultas
    private final APIConfig config;

    public LivroDAO() {
        this.config = APIConfig.getInstancia();
    }

    public List<Livro> buscarLivros(String query) {
        List<Livro> livros = new ArrayList<>();
        String urlString = config.getApiUrl() + "/volumes?q=" + query.replace(" ", "%20") + "&key=" + config.getApiKey();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                System.out.println("Erro: " + content.toString());
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                Scanner scanner = new Scanner(url.openStream());
                String inline = "";
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();

                // Parse the JSON response and convert it into a list of Livro objects
                JSONObject jsonResponse = new JSONObject(inline);
                JSONArray items = jsonResponse.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                    JSONObject saleInfo = item.getJSONObject("saleInfo");

                    Livro livro = new Livro();
                    livro.setTitulo(volumeInfo.getString("title"));
                    livro.setNumPaginas(volumeInfo.has("pageCount") ? volumeInfo.getInt("pageCount") : 0);

                    // Preencher demais atributos
                    livro.setAutor(volumeInfo.has("authors") ? volumeInfo.getJSONArray("authors").join(", ").replaceAll("\"", "") : null);
                    livro.setGenero(volumeInfo.has("categories") ? volumeInfo.getJSONArray("categories").join(", ").replaceAll("\"", "") : null);
                    livro.setSinopse(volumeInfo.has("description") ? volumeInfo.getString("description") : null);
                    livro.setEditora(volumeInfo.has("publisher") ? volumeInfo.getString("publisher") : null);

                    // Pegar o preço do livro
                    if (saleInfo.has("retailPrice")) {
                        JSONObject retailPrice = saleInfo.getJSONObject("retailPrice");
                        livro.setPreco(retailPrice.getDouble("amount"));
                    } else {
                        livro.setPreco(0.0);
                    }

                    livros.add(livro);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return livros;
    }
}
