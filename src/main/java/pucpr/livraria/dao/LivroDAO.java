package pucpr.livraria.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import pucpr.livraria.config.APIConfig;
import pucpr.livraria.entity.Livro;

import javax.net.ssl.*;

public class LivroDAO {
    private final APIConfig config;

    public LivroDAO() {
        this.config = APIConfig.getInstancia();
        ignorarCertificadosSSL();  // Chamar o método para ignorar a verificação de certificados SSL, se necessário
    }

    private void ignorarCertificadosSSL() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = (hostname, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Livro> buscarLivros(String query) {
        return buscarLivrosPorQuery(query.replace(" ", "%20"));
    }

    public List<Livro> buscarLivrosPorAutor(String autor) {
        String query = "inauthor:" + autor.replace(" ", "%20");
        return buscarLivrosPorQuery(query);
    }

    public List<Livro> buscarLivrosPorTitulo(String titulo) {
        String query = "intitle:" + titulo.replace(" ", "%20");
        return buscarLivrosPorQuery(query);
    }

    public List<Livro> buscarLivrosPorGenero(String genero) {
        String query = "subject:" + genero.replace(" ", "%20");
        return buscarLivrosPorQuery(query);
    }

    private List<Livro> buscarLivrosPorQuery(String query) {
        List<Livro> livros = new ArrayList<>();
        String urlString = config.getApiUrl() + "/volumes?q=" + query + "&maxResults=10&filter=partial&key=" + config.getApiKey();

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
                    livro.setDataPublicacao(volumeInfo.has("publishedDate") ? volumeInfo.getString("publishedDate") : null);
                    livro.setEditora(volumeInfo.has("publisher") ? volumeInfo.getString("publisher") : null);
                    livro.setPreviewLink(volumeInfo.has("previewLink") ? volumeInfo.getString("previewLink") : null);

                    // Pegar o preço do livro
                    if (saleInfo.has("retailPrice")) {
                        JSONObject retailPrice = saleInfo.getJSONObject("retailPrice");
                        livro.setPreco(retailPrice.getDouble("amount"));
                    } else {
                        livro.setPreco(10.90);
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
