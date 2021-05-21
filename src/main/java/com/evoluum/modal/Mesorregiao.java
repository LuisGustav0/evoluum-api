package com.evoluum.modal;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Mesorregiao {

    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    @JsonProperty("UF")
    private Estado estado;
}
