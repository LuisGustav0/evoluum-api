package com.evoluum.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalityResponse {

    private Long idEstado;

    private String siglaEstado;

    private String regiaoNome;

    private String nomeCidade;

    private String nomeMesorregiao;

    private String nomeFormatado;
}
