package com.evoluum.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Municipio {

    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private Microrregiao microrregiao;

    public Mesorregiao getMesorregiao() {
        return Optional.ofNullable(this.microrregiao).map(Microrregiao::getMesorregiao).orElse(new Mesorregiao());
    }

    public Estado getEstado() {
        return Optional.ofNullable(this.microrregiao)
                       .map(Microrregiao::getMesorregiao)
                       .map(Mesorregiao::getEstado)
                       .orElse(new Estado());
    }

    public Regiao getRegiao() {
        return Optional.ofNullable(this.microrregiao)
                       .map(Microrregiao::getMesorregiao)
                       .map(Mesorregiao::getEstado)
                       .map(Estado::getRegiao)
                       .orElse(new Regiao());
    }
}
