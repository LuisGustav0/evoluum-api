package com.evoluum.core.config.modelmapper;

import com.evoluum.modal.Localidade;
import com.evoluum.resource.response.LocalityResponse;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    private void localityToLocalityResponse(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Localidade.class, LocalityResponse.class)
                   .addMapping(Localidade::getCityAndState, LocalityResponse::setNomeFormatado);
    }

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        this.localityToLocalityResponse(modelMapper);

        return modelMapper;
    }
}
