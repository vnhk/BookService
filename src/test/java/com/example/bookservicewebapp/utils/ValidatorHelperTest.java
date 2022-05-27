package com.example.bookservicewebapp.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorHelperTest {
    @Test
    public void doesTextStartsWithTestTrue() {
        assertTrue(ValidatorHelper.doesTextStartsWith("Abc", "A"));
        assertTrue(ValidatorHelper.doesTextStartsWith("Abcd", "Abc"));
        assertTrue(ValidatorHelper.doesTextStartsWith("Abcd", "Abcd"));
        assertTrue(ValidatorHelper.doesTextStartsWith("", ""));
    }

    @Test
    public void doesTextStartsWithTestFalse() {
        assertFalse(ValidatorHelper.doesTextStartsWith("", "A"));
        assertFalse(ValidatorHelper.doesTextStartsWith("B", "A"));
        assertFalse(ValidatorHelper.doesTextStartsWith("Abc", "Abcd"));
    }
}
