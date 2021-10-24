package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AppCardTest {
    String date = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));


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
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + date));

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
    public void shouldReturnErrorWhenCityIsEmpty() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(String.valueOf(date));
        form.$("[data-test-id=name] input").setValue("Василий Пупкин");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=city] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Поле обязательно для заполнения"));

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
    public void shouldReturnErrorWhenNameWithSigns() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(String.valueOf(date));
        form.$("[data-test-id=name] input").setValue("Вася@");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=name] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void shouldReturnErrorWhenWrongFormatOfTelWithoutPlus() {
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
    public void shouldReturnErrorWhenWrongFormatOfTelWith10Numbers() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(String.valueOf(date));
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("+9271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=phone] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    public void shouldReturnErrorWhenWrongFormatOfTelWith12Numbers() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(String.valueOf(date));
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("+927110012233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=phone] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    public void shouldReturnErrorWhenTelIsEmpty() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(String.valueOf(date));
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $(".input_invalid[data-test-id=phone] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Поле обязательно для заполнения"));

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

    @Test
    public void shouldReturnErrorWhenDateIsEmpty() {
        open("http://localhost:9999");
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
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(appear)
                .shouldHave(exactText("Заказ на выбранную дату невозможен"));

    }

    @Test
    public void shouldReturnErrorWhenDateOfMeetingIsYesterday() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Майкоп");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
        form.$("[data-test-id=name] input").setValue("Вася");
        form.$("[data-test-id=phone] input").setValue("+79271112233");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(appear)
                .shouldHave(exactText("Заказ на выбранную дату невозможен"));

    }

    //
//
    @Test
    public void shouldChooseCityFromTheList() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").doubleClick().sendKeys("Ма");
        form.$(".menu-item__control").find(byText("Майкоп"));
        form.$("[data-test-id=city] input").equals("Майкоп");

    }

    @Test
    public void shouldNotChooseCityFromTheListWhenThereIsNone() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").doubleClick().sendKeys("Мате");
        form.$(".menu").shouldNotBe(visible);

    }

    @Test
    public void shouldChooseDateFromCalendar1WeekFromNow() {
        open("http://localhost:9999");
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
