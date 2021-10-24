package ru.netology;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {
    public static String getDate(int amount) {
        String date = LocalDate.now().plusDays(amount).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        return date;
    }



}
