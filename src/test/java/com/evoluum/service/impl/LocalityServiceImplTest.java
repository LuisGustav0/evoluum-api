package com.evoluum.service.impl;

import com.evoluum.api.servicodadosibge.v1.localidades.estados.ApiEstadoService;
import com.evoluum.api.servicodadosibge.v1.localidades.estados.municipios.ApiMunicipioService;
import com.evoluum.exception.CityNotFoundException;
import com.evoluum.modal.Localidade;
import com.evoluum.modal.Municipio;
import com.evoluum.service.LocalityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LocalityServiceImplTest {

    private LocalityService service;

    @MockBean
    private ApiEstadoService apiEstadoService;

    @MockBean
    private ApiMunicipioService apiMunicipioService;

    @BeforeEach
    public void setUp() {
        this.service = new LocalityServiceImpl(this.apiEstadoService, this.apiMunicipioService);
    }

    private Municipio fakeMunicipio(Long id) {
        return Municipio.builder().id(id).build();
    }

    @Test
    @DisplayName("Deve retornar lista de localidade")
    public void findAllLocalityTest() {
        Municipio municipio = this.fakeMunicipio(1L);

        List<Municipio> listaMunicipio = Arrays.asList(municipio, municipio);

        when(this.apiMunicipioService.findAllByUf(anyString())).thenReturn(listaMunicipio);

        List<Localidade> listaLocalidade = this.service.findAllLocality();

        assertThat(listaLocalidade.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve retornar uma exception quando não encontrar a cidade")
    public void throwSearchCityIdByNameCityTest() {
        String nameCity = "goiania";

        Municipio municipio = this.fakeMunicipio(1L);

        List<Municipio> listaMunicipio = Arrays.asList(municipio, municipio);

        when(this.apiMunicipioService.findAllByUf(anyString())).thenReturn(listaMunicipio);

        Throwable exception = catchThrowable(() -> this.service.searchCityIdByNameCityOrThrow(nameCity));

        assertThat(exception).isInstanceOf(CityNotFoundException.class)
                .hasMessage(String.format("Não existe um cidade com o nome: %s", nameCity));
    }

    @Test
    @DisplayName("Deve retornar um id da cidade")
    public void searchCityIdByNameCityTest() {
        String nameCity = "goiania";

        Municipio municipio = this.fakeMunicipio(1L);

        List<Municipio> listaMunicipio = Arrays.asList(municipio, municipio);

        when(this.apiMunicipioService.findAllByUf(anyString())).thenReturn(listaMunicipio);

        Throwable exception = catchThrowable(() -> this.service.searchCityIdByNameCityOrThrow(nameCity));

        assertThat(exception).isInstanceOf(CityNotFoundException.class)
                .hasMessage(String.format("Não existe um cidade com o nome: %s", nameCity));
    }
}
