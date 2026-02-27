package com.ONE.literarura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosRespuestaLibros(
        @JsonAlias("count") Integer total,
        @JsonAlias("next") String siguiente,
        @JsonAlias("previous") String anterior,
        @JsonAlias("results") List<DatosLibro> resultados
) {
}
