package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class ElementsTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldChooseCityFromTheList() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").doubleClick().sendKeys("Ма");
        form.$(".menu-item__control").find(byText("Майкоп"));
        form.$("[data-test-id=city] input").equals("Майкоп");

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
        $$(".calendar__day")
                .find(Condition.text(String.valueOf(LocalDate.now().plusWeeks(1).getDayOfMonth())))
                .click();
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".notification_visible").shouldBe(appear, Duration.ofSeconds(16));
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"))));

    }
}
