package com.ONE.literarura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Clase responsable de realizar solicitudes HTTP a APIs externas.
 * Utiliza las clases HttpClient, HttpRequest y HttpResponse de Java 11+.
 */
public class ConsumoAPI {

    /**
     * Realiza una solicitud GET a la URL indicada y retorna el cuerpo
     * de la respuesta como String (JSON).
     *
     * @param url Dirección de la API a consultar
     * @return Cuerpo de la respuesta HTTP como String
     */
    public String obtenerDatos(String url) {
        // 1. Construir el cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // 2. Construir la solicitud HTTP (GET por defecto)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // 3. Enviar la solicitud y capturar la respuesta
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al consumir la API: " + e.getMessage(), e);
        }

        // 4. Retornar el cuerpo de la respuesta (JSON)
        return response.body();
    }
}
