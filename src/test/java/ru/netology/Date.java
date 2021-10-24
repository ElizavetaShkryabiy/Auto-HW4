package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Date {
    public static String getDate(int amount) {
        String date = LocalDate.now().plusDays(amount).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        return date;
    }

    public static LocalDate getDatePlusWeeks(int amount) {
        LocalDate date = LocalDate.now().plusWeeks(amount);
        return date;
    }

    public static String getFullDateInCalendar(int amount) {
        String date = getDatePlusWeeks(amount).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        return date;
    }

    public static String getDateForCalendar(int amount) {
        String date = String.valueOf(getDatePlusWeeks(amount).getDayOfMonth());
        return date;
    }


    public static String getMonthPlusWeeks(int amount) {
        String date = String.valueOf(LocalDate.now().plusWeeks(amount).getMonth());
        return date;
    }

    public static String getMonth() {
        String date = String.valueOf(LocalDate.now().getMonth());
        return date;
    }

    public static void findDate() {
        $$(".calendar__day")
                .find(Condition.text(Date.getDateForCalendar(2)))
                .click();
    }

    public static void findPage() {
        SelenideElement form = $(".form");
        String dateOfMeeting = Date.getMonthPlusWeeks(2);
        String today = Date.getMonth();
        if (today != dateOfMeeting) {
            $$("[data-step]")
                    .last()
                    .click();
            Date.findDate();
        } else {
            Date.findDate();
        }
    }
}
