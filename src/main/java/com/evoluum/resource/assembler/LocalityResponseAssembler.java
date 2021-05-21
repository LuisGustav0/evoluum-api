package com.evoluum.resource.assembler;

import com.evoluum.modal.Localidade;
import com.evoluum.resource.response.LocalityResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LocalityResponseAssembler {

    private final ModelMapper modelMapper;

    public LocalityResponse toModel(Localidade localidade) {
        return this.modelMapper.map(localidade, LocalityResponse.class);
    }

    public List<LocalityResponse> toCollectionModel(List<Localidade> lista) {
        return lista.stream().map(this::toModel).collect(Collectors.toList());
    }
}
