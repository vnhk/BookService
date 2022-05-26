package com.example.bookservicewebapp.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidatorHelper {
    public static boolean doesTextStartsWith(String text, String sequence) {
        return text.startsWith(sequence);
    }
}
