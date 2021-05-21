package com.evoluum.service.impl;

import com.evoluum.api.servicodadosibge.v1.localidades.estados.ApiEstadoService;
import com.evoluum.api.servicodadosibge.v1.localidades.estados.municipios.ApiMunicipioService;
import com.evoluum.exception.CityNotFoundException;
import com.evoluum.modal.Estado;
import com.evoluum.modal.Localidade;
import com.evoluum.modal.Municipio;
import com.evoluum.service.LocalityService;
import com.evoluum.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocalityServiceImpl implements LocalityService {

    private final ApiEstadoService apiEstadoService;

    private final ApiMunicipioService apiMunicipioService;

    private String getListStateByJoin(List<Estado> listaEstado) {
        return listaEstado.stream().map(Estado::getId).map(String::valueOf).collect(Collectors.joining("|"));
    }

    private List<Municipio> findAllMunicipioByAllEstado() {
        List<Estado> listaState = this.apiEstadoService.findAll();

        String listStateId = this.getListStateByJoin(listaState);

        return this.apiMunicipioService.findAllByUf(listStateId);
    }

    private Localidade cityToLocality(Municipio cidade) {
        return Localidade.builder()
                         .estado(cidade.getEstado())
                         .cidade(cidade)
                         .regiao(cidade.getRegiao())
                         .mesorregiao(cidade.getMesorregiao())
                         .build();
    }

    @Override
    public List<Localidade> findAllLocality() {
        List<Municipio> listaAllMunicipio = this.findAllMunicipioByAllEstado();

        return listaAllMunicipio.parallelStream().map(this::cityToLocality).collect(Collectors.toList());
    }

    private boolean filterNameCity(Municipio cidade, String nomeCidade) {
        String nomeCidadeSemAcentos = Optional.ofNullable(cidade)
                                              .map(Municipio::getNome)
                                              .map(StringUtil::removerAcentos)
                                              .orElse("");

        nomeCidade = Optional.ofNullable(nomeCidade).map(StringUtil::removerAcentos).orElse("");

        return nomeCidadeSemAcentos.equalsIgnoreCase(nomeCidade);
    }

    @Override
    public Long searchCityIdByNameCityOrThrow(String nameCity) {
        List<Localidade> listLocality = this.findAllLocality();

        return listLocality.parallelStream()
                           .map(Localidade::getCidade)
                           .filter(cidade -> this.filterNameCity(cidade, nameCity))
                           .map(Municipio::getId)
                           .findFirst()
                           .orElseThrow(() -> new CityNotFoundException(nameCity));
    }
}
