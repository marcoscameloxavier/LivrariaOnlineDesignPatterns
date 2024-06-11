package pucpr.livraria.strategy;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pucpr.livraria.entity.Pedido;

@JsonDeserialize(using = EntregaStrategyDeserializer.class)
public interface EntregaStrategy {
    double calcularCustoEnvio(Pedido pedido);
}
