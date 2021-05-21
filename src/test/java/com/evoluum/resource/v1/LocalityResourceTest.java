package com.evoluum.resource.v1;

import com.evoluum.exception.CityNotFoundException;
import com.evoluum.resource.response.CityResponse;
import com.evoluum.resource.response.LocalityResponse;
import com.evoluum.service.LocalityAdapterService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(LocalityResource.class)
@AutoConfigureMockMvc(addFilters = false)
public class LocalityResourceTest {

    static String LOCALITY_API = "/v1/estados/municipios";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocalityAdapterService service;

    @Test
    public void json_DeveRetornar200() throws Exception {
        String URI = LOCALITY_API + "/json";

        LocalityResponse response = LocalityResponse.builder().idEstado(1L).nomeCidade("Goiania").build();

        List<LocalityResponse> list = Arrays.asList(response, response);

        when(this.service.findAllLocalityResponse()).thenReturn(list);

        MockHttpServletRequestBuilder request = get(URI).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].idEstado").value(1L))
                .andExpect(jsonPath("$[0].nomeCidade").value("Goiania"));
    }

    @Test
    public void csv_DeveRetornar200() throws Exception {
        String URI = LOCALITY_API + "/csv";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void searchCityIdByNameCity_DeveRetornar404() throws Exception {
        String nomeCidade = "goiania";

        String URI = LOCALITY_API + "/" + nomeCidade;

        when(this.service.searchCityIdByNameCity(anyString())).thenThrow(new CityNotFoundException(nomeCidade));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    public void searchCityIdByNameCity_DeveRetornar200() throws Exception {
        String nomeCidade = "goiania";

        String URI = LOCALITY_API + "/" + nomeCidade;

        CityResponse response = CityResponse.builder().cidadeId(1L).build();

        when(this.service.searchCityIdByNameCity(anyString())).thenReturn(response);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }
}
