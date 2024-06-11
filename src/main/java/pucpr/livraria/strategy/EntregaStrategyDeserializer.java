package pucpr.livraria.strategy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class EntregaStrategyDeserializer extends JsonDeserializer<EntregaStrategy> {

    @Override
    public EntregaStrategy deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String tipo = node.get("tipo").asText();

        switch (tipo) {
            case "ECONOMICA":
                return new EntregaEconomica();
            case "RAPIDA":
                return new EntregaRapida();
            case "SEDEX":
                return new EntregaSedex();
            default:
                throw new IllegalArgumentException("Tipo de entrega desconhecido: " + tipo);
        }
    }
}
