package com.evoluum.service;

import com.evoluum.resource.response.CityResponse;
import com.evoluum.resource.response.LocalityResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

public interface LocalityAdapterService {

    List<LocalityResponse> findAllLocalityResponse();

    OutputStream parserCSV(HttpServletResponse response);

    CityResponse searchCityIdByNameCity(String nameCity);
}
