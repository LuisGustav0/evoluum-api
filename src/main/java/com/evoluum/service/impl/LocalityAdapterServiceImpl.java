package com.evoluum.service.impl;

import com.evoluum.modal.Localidade;
import com.evoluum.resource.assembler.LocalityResponseAssembler;
import com.evoluum.resource.response.CityResponse;
import com.evoluum.resource.response.LocalityResponse;
import com.evoluum.service.BaseParserService;
import com.evoluum.service.LocalityAdapterService;
import com.evoluum.service.LocalityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalityAdapterServiceImpl implements LocalityAdapterService {

    private final LocalityService service;

    private final LocalityResponseAssembler assembler;

    private final BaseParserService baseParserService;

    public List<LocalityResponse> findAllLocalityResponse() {
        List<Localidade> listLocality = this.service.findAllLocality();

        return this.assembler.toCollectionModel(listLocality);
    }

    public OutputStream parserCSV(HttpServletResponse response) {
        response.setContentType("text/csv; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=locality.csv;");

        List<LocalityResponse> listLocalityResponse = findAllLocalityResponse();

        this.baseParserService.setResponse(response);
        this.baseParserService.setListLocalityResponse(listLocalityResponse);

        return this.baseParserService.parser();
    }

    public CityResponse searchCityIdByNameCity(String nameCity) {
        Long cityId = this.service.searchCityIdByNameCityOrThrow(nameCity);

        return CityResponse.builder().cidadeId(cityId).build();
    }
}
