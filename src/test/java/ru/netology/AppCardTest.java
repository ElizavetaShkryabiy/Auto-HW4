package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AppCardTest {
    String date = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd/MM/YYYY"));


    @Test
    public void shouldTestHappyPath() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(String.valueOf(date));
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".notification_visible").shouldBe(appear, Duration.ofSeconds(16));
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на 25.10.2021"));

    }


    @Test
    public void shouldReturnErrorWhenCityNotInList() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Мытищи");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(String.valueOf(date));
        form.$("[data-test-id=name] input").setValue("Василий Пупкин");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=city] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    public void shouldReturnErrorWhenCityNotInKirilitsa() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Moscow");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(String.valueOf(date));
        form.$("[data-test-id=name] input").setValue("Василий Пупкин");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=city] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    public void shouldReturnErrorWhenNameNotInKirilitsa() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(String.valueOf(date));
        form.$("[data-test-id=name] input").setValue("Vasya");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=name] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void shouldReturnErrorWhenWrongFormatOfTel() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(String.valueOf(date));
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("89271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=phone] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    public void shouldReturnErrorWhenAgreementEmpty() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(String.valueOf(date));
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]");
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=agreement]").shouldBe(appear);
    }

    @Test
    public void shouldReturnErrorWhenWrongFormatOfDate() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue("80.56.8532");
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(appear)
                .shouldHave(exactText("Неверно введена дата"));

    }


}
