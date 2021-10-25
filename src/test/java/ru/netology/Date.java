package ru.netology;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {
    public static LocalDate dateNow = LocalDate.now();

    public static String getDate(int amount) {
        String date = dateNow.plusDays(amount).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        return date;
    }

    public static int weeksToAdd;
    public static LocalDate datePlusWeeks;


    public static LocalDate getDatePlusWeeks() {
        Date.datePlusWeeks = dateNow.plusWeeks(weeksToAdd);
        return datePlusWeeks;
    }

    public static String getDateForCalendar(int weeksToAdd) {
        Date.weeksToAdd = weeksToAdd;
        String date = String.valueOf(getDatePlusWeeks().getDayOfMonth());
        return date;

    }
}
