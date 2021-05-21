package com.evoluum.api.servicodadosibge.v1.localidades.estados.impl;

import com.evoluum.api.servicodadosibge.v1.localidades.estados.ApiEstadoService;
import com.evoluum.core.config.property.AppProperty;
import com.evoluum.modal.Estado;
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
public class ApiEstadoServiceImpl implements ApiEstadoService {

    private final AppProperty appProperty;

    private final RestTemplate restTemplate;

    @Override
    @HystrixCommand
    @Cacheable(value = "findAllEstado")
    public List<Estado> findAll() {

        String URI = this.appProperty.getServicoDadosIbgeGovBr().getUriLocalidadesEstados();

        log.info("ApiEstadoServiceImpl.findAll.uri: " + URI);

        try {
            Estado[] listaEstado = this.restTemplate.getForObject(URI, Estado[].class);

            return Optional.ofNullable(listaEstado).map(Arrays::asList).orElse(Collections.emptyList());
        } catch (RestClientException ex) {
            throw new BusinessException("IBGE_DATA_SERVICE_LOCALITY_STATE", ex);
        }
    }
}
