package com.evoluum.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class ReflectionUtil {

    public static Field[] getListField(Class<?> clazz) {
        return Optional.ofNullable(clazz).map(Class::getDeclaredFields).orElse(new Field[]{});
    }

    public static List<String> getListFieldByClass(Class<?> clazz) {
        Field[] listField = getListField(clazz);

        return Arrays.stream(listField).map(Field::getName).collect(Collectors.toList());
    }

    public static Object getValueByField(Field field, Object object) {
        try {
            field.setAccessible(true);

            return field.get(object);
        } catch (IllegalAccessException ex) {
            log.error("ReflectionUtil.getValueByField - Field não encontrado", ex);
        }
        return "";
    }
}
