package com.evoluum.service;

import com.evoluum.resource.response.LocalityResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

public interface BaseParserService {

    void setResponse(HttpServletResponse response);

    void setListLocalityResponse(List<LocalityResponse> listLocalityResponse);

    OutputStream parser();
}
