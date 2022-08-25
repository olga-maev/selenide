package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryCardTest {
    @BeforeEach
    public void setForm() {
//        Configuration.headless = true;
        open("http://localhost:9999/");

    }

    @Test
    void shouldAllValidValue()  {

        $("[data-test-id=city] input").setValue("ка");
        $$("menu-item__control").filter(exactText("Казань")).forEach(SelenideElement::click);
        String planningDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79031234567");
        $("[data-test-id = agreement]").click();
        $(By.className("button")).click();

        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        $("[data-test-id=notification").shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    void shouldSetCityUser() {
        String planningDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+79031234567");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

        $("[data-test-id=notification").shouldBe(visible, Duration.ofSeconds(17));
    }

    @Test
    void shouldDoubleNameUser() {
        String planningDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Иван");
        $("[data-test-id=phone] input").setValue("+79031234567");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

        $("[data-test-id=notification").shouldBe(visible, Duration.ofSeconds(17));
    }

    @Test
    void shouldAdd10Days() {
        String planningDate = LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+79031234567");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

        $("[data-test-id=notification").shouldBe(visible, Duration.ofSeconds(17));
    }

        @Test
    void shouldEnglishName() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Пролор Ghjgjhgj");
        $("[data-test-id=phone] input").setValue("+79031234567");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = $("[data-test-id='name'].input_invalid .input__sub").getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldErrorPhone() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Иванов иван");
        $("[data-test-id=phone] input").setValue("+790312567");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = $("[data-test-id='phone'].input_invalid .input__sub").getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldEmptyClickAgreement() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Иванов иван");
        $("[data-test-id=phone] input").setValue("+74564564545");
        $(By.className("button")).click();

        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных";
        String actual = $("[data-test-id='agreement'].input_invalid").getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldEmptyCity() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Иванов иван");
        $("[data-test-id=phone] input").setValue("+74564564545");
        $(By.className("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = $("[data-test-id='city'].input_invalid").getText().trim();
        assertEquals(expected, actual);
    }
    @Test
    void shouldNumberInName() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Пролор 123");
        $("[data-test-id=phone] input").setValue("+79031234567");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = $("[data-test-id='name'].input_invalid .input__sub").getText().trim();
        assertEquals(expected, actual);
    }
    @Test
    void shouldEmptyName() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=phone] input").setValue("+74564564545");
        $(By.className("button")).click();

        $x("//*[contains(text(),'обязательно')]").shouldBe(visible);
    }
    @Test
    void shouldEmptyPhone() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Иванов иван");
        $(By.className("button")).click();

        $x("//*[contains(text(),'обязательно')]").shouldBe(visible);
    }
    @Test
    void shouldEmptyForm() {
        $(By.className("button")).click();
        $x("//*[contains(text(),'обязательно')]").shouldBe(visible);
    }
    @Test
    void shouldCityListAndOpenCalendar() {
        String myCity = "Абакан";
        $("[data-test-id=city] input").setValue("ка");
        $$(".menu-item__control").filter(exactText(myCity)).forEach(SelenideElement::click);
        $(".icon").click();
        if (ru.netology.delivery.data.DataGenerator.generateDate(7, "M").equals(ru.netology.delivery.data.DataGenerator.generateDate(0, "M"))) {
            ElementsCollection dates = $$(".calendar__day");
            for (SelenideElement element : dates) {
                if (element.getText().equals(ru.netology.delivery.data.DataGenerator.generateDate(7, "d"))) {
                    element.click();
                }
            }
        } else {
            $(By.cssSelector("[data-step=\"1\"]")).click();
            ElementsCollection dates = $$(".calendar__day");
            for (SelenideElement element : dates) {
                if (element.getText().equals(ru.netology.delivery.data.DataGenerator.generateDate(7, "d"))) {
                    element.click();
                }
            }
        }
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+79031234567");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + ru.netology.delivery.data.DataGenerator.generateDate(7, "dd.MM.yyyy")), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

}
