package com.evoluum.exception;

public class CityNotFoundException extends EntityNotFoundException {

    public CityNotFoundException(String nameCity) {
        super("CITY_NOT_FOUND", String.format("Não existe um cidade com o nome: %s", nameCity));
    }
}
