package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {
    LocalDate currentDate = LocalDate.now();


    public String dateGenerator(int date) {
        String format = LocalDate.now().plusDays(date).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return format;
    }


    @BeforeEach
    public void openLocalhost() {
        open("http://localhost:9999");
        $("[data-test-id='date'] [placeholder='Дата встречи']").sendKeys(Keys.CONTROL, "a" + Keys.DELETE);

    }

    @Test
    void successOrderCard() {

        $("[data-test-id='city'] [placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(dateGenerator(3));
        $("[data-test-id='name'] [name='name']").setValue("Карл Маркс");
        $("[data-test-id='phone'] [name='phone']").setValue("+79111111111");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + dateGenerator(3)), Duration.ofSeconds(15)).shouldBe(visible);

    }
    @Test
    void DatePlusFiveDays() {

        $("[data-test-id='city'] [placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(dateGenerator(5));
        $("[data-test-id='name'] [name='name']").setValue("Карл Маркс");
        $("[data-test-id='phone'] [name='phone']").setValue("+79111111111");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + dateGenerator(5)), Duration.ofSeconds(15)).shouldBe(visible);

    }
    @Test
    void datePlusOneDays() {

        $("[data-test-id='city'] [placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(dateGenerator(1));
        $("[data-test-id='name'] [name='name']").setValue("Карл Маркс");
        $("[data-test-id='phone'] [name='phone']").setValue("+79111111111");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $$(".input__sub").find(Condition.text("Неверная дата"));

    }

    @Test
    void cityNotInList() {

        $("[data-test-id='city'] [placeholder='Город']").setValue("Гатчина");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(dateGenerator(5));
        $("[data-test-id='name'] [name='name']").setValue("Карл Маркс");
        $("[data-test-id='phone'] [name='phone']").setValue("+79111111111");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $(".input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"));

    }

    @Test
    void nameInEnglish() {
        $("[data-test-id='city'] [placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(dateGenerator(5));
        $("[data-test-id='name'] [name='name']").setValue("Karl Marks");
        $("[data-test-id='phone'] [name='phone']").setValue("+79111111111");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $$(".input__inner").find(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }
    @Test
    void shortPhoneNumber (){
        $("[data-test-id='city'] [placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(dateGenerator(5));
        $("[data-test-id='name'] [name='name']").setValue("Карл Маркс");
        $("[data-test-id='phone'] [name='phone']").setValue("+7911111111");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $$(".input__inner").find(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void notAgreement (){
        $("[data-test-id='city'] [placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(dateGenerator(5));
        $("[data-test-id='name'] [name='name']").setValue("Карл Маркс");
        $("[data-test-id='phone'] [name='phone']").setValue("+79111111111");
        $(".button__content").click();
        $$(".checkbox__text").find(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }
    
    }
