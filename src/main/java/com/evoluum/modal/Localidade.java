package com.evoluum.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Localidade {

    private Estado estado;

    private Regiao regiao;

    private Municipio cidade;

    private Mesorregiao mesorregiao;

    public String getCityAndState() {
        String nameCity = Optional.ofNullable(cidade).map(Municipio::getNome).orElse("");
        String siglaState = Optional.ofNullable(estado).map(Estado::getSigla).orElse("");

        return nameCity + "/" + siglaState;
    }
}
