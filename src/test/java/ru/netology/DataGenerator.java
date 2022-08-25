package ru.netology.delivery.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int days, String formatPattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(formatPattern));
    }
}