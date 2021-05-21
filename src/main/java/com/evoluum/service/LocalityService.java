package com.evoluum.service;

import com.evoluum.modal.Localidade;

import java.util.List;

public interface LocalityService {

    List<Localidade> findAllLocality();

    Long searchCityIdByNameCityOrThrow(String nomeCidade);
}
