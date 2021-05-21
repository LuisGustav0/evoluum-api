package com.evoluum.api.servicodadosibge.v1.localidades.estados.municipios;

import com.evoluum.modal.Municipio;

import java.util.List;

public interface ApiMunicipioService {

    List<Municipio> findAllByUf(String uf);
}
