package com.evoluum.service;

import com.evoluum.exception.BusinessException;
import com.evoluum.exception.CityNotFoundException;
import com.evoluum.resource.assembler.LocalityResponseAssembler;
import com.evoluum.resource.response.CityResponse;
import com.evoluum.resource.response.LocalityResponse;
import com.evoluum.service.impl.CSVParserServiceImpl;
import com.evoluum.service.impl.LocalityAdapterServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LocalityAdapterServiceTest {

    private LocalityAdapterService service;

    @MockBean
    private LocalityService localityService;

    @MockBean
    private LocalityResponseAssembler assembler;

    @MockBean
    private CSVParserServiceImpl csvParser;

    @BeforeEach
    public void setUp() {
        this.service = new LocalityAdapterServiceImpl(this.localityService, this.assembler, this.csvParser);
    }

    @Test
    @DisplayName("Deve retornar lista de localidade")
    public void findAllLocalityTest() {
        LocalityResponse locality = LocalityResponse.builder().build();

        List<LocalityResponse> listLocality = Arrays.asList(locality, locality);

        when(this.assembler.toCollectionModel(any())).thenReturn(listLocality);

        List<LocalityResponse> response = this.service.findAllLocalityResponse();

        assertThat(response.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve retornar lista de localidade CSV")
    public void parserCSVTest() {
        LocalityResponse locality = LocalityResponse.builder().build();
        List<LocalityResponse> listLocality = Arrays.asList(locality, locality);

        when(this.assembler.toCollectionModel(any())).thenReturn(listLocality);

        HttpServletResponse response = mock(HttpServletResponse.class);
        OutputStream outStream = mock(OutputStream.class);

        when(this.csvParser.parser()).thenReturn(outStream);

        OutputStream outputStream = this.service.parserCSV(response);

        assertThat(outputStream).isNotNull();
    }

    @Test
    @DisplayName("Deve retornar id da cidade com filtro nome cidade")
    public void searchCityIdByNameCityTest() {
        when(this.localityService.searchCityIdByNameCityOrThrow(anyString())).thenReturn(1L);

        CityResponse response = this.service.searchCityIdByNameCity("goiania");

        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("Deve retornar exception ao não encontrar uma cidade")
    public void throwSearchCityIdByNameCityTest() {
        String nameCity = "goiania";

        when(this.localityService.searchCityIdByNameCityOrThrow(anyString()))
                .thenThrow(new CityNotFoundException(nameCity));

        Throwable exception = catchThrowable(() -> this.service.searchCityIdByNameCity(nameCity));

        assertThat(exception)
                .isInstanceOf(CityNotFoundException.class)
                .hasMessage(String.format("Não existe um cidade com o nome: %s", nameCity));
    }
}
