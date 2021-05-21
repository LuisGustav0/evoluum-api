package com.evoluum.util;

import java.text.Normalizer;

public class StringUtil {

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
