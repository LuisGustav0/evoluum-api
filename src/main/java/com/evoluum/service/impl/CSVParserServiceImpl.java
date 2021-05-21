package com.evoluum.service.impl;

import com.evoluum.exception.CSVParserException;
import com.evoluum.resource.response.LocalityResponse;
import com.evoluum.service.BaseParserService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.evoluum.util.ReflectionUtil.getListField;
import static com.evoluum.util.ReflectionUtil.getListFieldByClass;
import static com.evoluum.util.ReflectionUtil.getValueByField;

@Slf4j
@Service
public class CSVParserServiceImpl implements BaseParserService {

    @Setter
    private List<LocalityResponse> listLocalityResponse;

    @Setter
    private HttpServletResponse response;

    private void addHeader(StringBuilder csv) {
        List<String> listaHeader = getListFieldByClass(LocalityResponse.class);

        csv.append(String.join(";", listaHeader));
        csv.append(System.lineSeparator());
    }

    private void addRow(StringBuilder csv, LocalityResponse estadoMunicipioModel) {
        StringBuilder lineBuilder = new StringBuilder();

        Field[] listaField = getListField(LocalityResponse.class);

        for (Field field : listaField) {
            Object value = getValueByField(field, estadoMunicipioModel);

            lineBuilder.append(value).append(";");
        }

        String lineFinal = lineBuilder.substring(0, lineBuilder.length() - 1);

        csv.append(lineFinal.trim());
        csv.append(System.lineSeparator());
    }

    private void addListaRow(StringBuilder csv) {
        this.getListLocalityResponse().forEach(estadoMunicipio -> this.addRow(csv, estadoMunicipio));
    }

    @Override
    public OutputStream parser() {
        log.info("CSVParserServiceImpl.parser - converter conteúdo para CSV");

        try {
            StringBuilder csv = new StringBuilder();

            OutputStream outputStream = response.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

            this.addHeader(csv);
            this.addListaRow(csv);

            writer.write(csv.toString());
            writer.flush();
            writer.close();

            log.info("CSVParserServiceImpl.parser - finalizado");

            return outputStream;
        } catch (Exception ex) {
            log.error("CSVParserServiceImpl.parser - Ocorreu um erro ao converter conteúdo para CSV", ex);

            throw new CSVParserException(ex);
        }
    }

    public List<LocalityResponse> getListLocalityResponse() {
        return Optional.ofNullable(this.listLocalityResponse).orElse(Collections.emptyList());
    }
}
