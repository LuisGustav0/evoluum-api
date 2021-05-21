package com.evoluum.api.servicodadosibge.v1.localidades.estados.municipios.impl;

import com.evoluum.api.servicodadosibge.v1.localidades.estados.municipios.ApiMunicipioService;
import com.evoluum.core.config.property.AppProperty;
import com.evoluum.modal.Municipio;
import com.evoluum.exception.BusinessException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiMunicipioServiceImpl implements ApiMunicipioService {

    private final AppProperty appProperty;

    private final RestTemplate restTemplate;

    @Override
    @HystrixCommand
    @Cacheable(value = "findAllByUf")
    public List<Municipio> findAllByUf(String uf) {

        String URI = this.appProperty.getServicoDadosIbgeGovBr().getUriLocalidadesEstadosMunicipios(uf);

        log.info("ApiMunicipioServiceImpl.findAllByUf.uri: " + URI);

        try {
            Municipio[] listaEstado = this.restTemplate.getForObject(URI, Municipio[].class);

            return Optional.ofNullable(listaEstado).map(Arrays::asList).orElse(Collections.emptyList());
        } catch (RestClientException ex) {
            throw new BusinessException("IBGE_DATA_SERVICE_LOCALITY_MUNICIPIO", ex);
        }
    }
}
