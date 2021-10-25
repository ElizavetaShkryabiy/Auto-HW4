package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElementsTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldChooseCityFromTheList() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").doubleClick().sendKeys("Ма");
        $$(".menu-item__control").find(Condition.text("Майкоп")).click();
        String text = form.$("[data-test-id=city] input").getValue();
        assertEquals("Майкоп", text);
    }

    @Test
    public void shouldNotChooseCityFromTheListWhenThereIsNone() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").doubleClick().sendKeys("Мате");
        form.$(".menu").shouldNotBe(visible);

    }

    @Test
    public void shouldChooseDateFromCalendar1WeekFromNow() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$(".input__icon").click();
        String dayOfMeetingInWeeks = Date.getDateForCalendar(1);

        String monthOfMeeting = String.valueOf(Date.datePlusWeeks.getMonth());
        String todaysMonth = String.valueOf(Date.dateNow.getMonth());
        if (todaysMonth != monthOfMeeting) {
            $$("[data-step]")
                    .last()
                    .click();
            $$(".calendar__day")
                    .find(Condition.text(dayOfMeetingInWeeks))
                    .click();
        } else {
            $$(".calendar__day")
                    .find(Condition.text(dayOfMeetingInWeeks))
                    .click();
        }

        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".notification_visible").shouldBe(appear, Duration.ofSeconds(16));
        $("[data-test-id=notification]")
                .shouldHave(text("Встреча успешно забронирована на "
                        + Date.datePlusWeeks.format(DateTimeFormatter.ofPattern("dd.MM.YYYY"))));

    }
}
