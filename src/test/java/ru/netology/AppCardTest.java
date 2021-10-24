package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
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

public class AppCardTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldTestHappyPath() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(Date.getDate(4));
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".notification_visible").shouldBe(appear, Duration.ofSeconds(16));
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + Date.getDate(4)));

    }


    @Test
    public void shouldReturnErrorWhenCityNotInList() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Мытищи");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(Date.getDate(4));
        form.$("[data-test-id=name] input").setValue("Василий Пупкин");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=city] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    public void shouldReturnErrorWhenCityNotInKirilitsa() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Moscow");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(Date.getDate(4));
        form.$("[data-test-id=name] input").setValue("Василий Пупкин");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=city] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    public void shouldReturnErrorWhenCityIsEmpty() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(Date.getDate(4));
        form.$("[data-test-id=name] input").setValue("Василий Пупкин");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=city] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    public void shouldReturnErrorWhenNameNotInKirilitsa() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(Date.getDate(4));
        form.$("[data-test-id=name] input").setValue("Vasya");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=name] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void shouldReturnErrorWhenNameWithSigns() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(Date.getDate(4));
        form.$("[data-test-id=name] input").setValue("Вася@");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=name] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void shouldReturnErrorWhenWrongFormatOfTelWithoutPlus() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(Date.getDate(4));
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("89271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=phone] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    public void shouldReturnErrorWhenWrongFormatOfTelWith10Numbers() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(Date.getDate(4));
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("+9271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=phone] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    public void shouldReturnErrorWhenWrongFormatOfTelWith12Numbers() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(Date.getDate(4));
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("+927110012233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=phone] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    public void shouldReturnErrorWhenTelIsEmpty() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(Date.getDate(4));
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=phone] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    public void shouldReturnErrorWhenAgreementEmpty() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(Date.getDate(4));
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]");
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=agreement]").shouldBe(appear);
    }

    @Test
    public void shouldReturnErrorWhenWrongFormatOfDate() {
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

    @Test
    public void shouldReturnErrorWhenDateIsEmpty() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue("");
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(appear)
                .shouldHave(exactText("Неверно введена дата"));

    }


    @Test
    public void shouldReturnErrorWhenDateOfMeetingIsToday() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(Date.getDate(0));
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(appear)
                .shouldHave(exactText("Заказ на выбранную дату невозможен"));

    }

    @Test
    public void shouldReturnErrorWhenDateOfMeetingIsYesterday() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(Date.getDate(-1));
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(appear)
                .shouldHave(exactText("Заказ на выбранную дату невозможен"));

    }


}
