package com.evoluum.modal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Regiao {

    @EqualsAndHashCode.Include
    private Long id;

    private String nome;
}

