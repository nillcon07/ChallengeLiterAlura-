package com.ONE.literarura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase utilitaria para convertir JSON en objetos Java usando Jackson.
 */
public class ConvierteDatos {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Convierte un String JSON en una instancia de la clase indicada.
     *
     * @param json  String con el JSON de la respuesta
     * @param clase Clase a la que se desea convertir el JSON
     * @param <T>   Tipo genérico del objeto resultante
     * @return Objeto del tipo solicitado con los datos del JSON
     */
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir JSON: " + e.getMessage(), e);
        }
    }
}
