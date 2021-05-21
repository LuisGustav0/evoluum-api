package com.evoluum.resource.v1;

import com.evoluum.resource.response.CityResponse;
import com.evoluum.resource.response.LocalityResponse;
import com.evoluum.service.LocalityAdapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/estados/municipios")
public class LocalityResource {

    private final LocalityAdapterService service;

    @GetMapping("/json")
    public List<LocalityResponse> json() {
        return this.service.findAllLocalityResponse();
    }

    @GetMapping("/csv")
    public OutputStream csv(HttpServletResponse response) {
        return this.service.parserCSV(response);
    }

    @GetMapping("/{nomeCidade}")
    public CityResponse searchCityIdByNameCity(@PathVariable String nomeCidade) {
        return this.service.searchCityIdByNameCity(nomeCidade);
    }
}
