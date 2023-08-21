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
    void successfulOrderCardTest() {

        $("[data-test-id='city'] [placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(dateGenerator(3));
        $("[data-test-id='name'] [name='name']").setValue("Иванов Иван");
        $("[data-test-id='phone'] [name='phone']").setValue("+79850000000");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + dateGenerator(3)), Duration.ofSeconds(15)).shouldBe(visible);

    }
    @Test
    void plannedDatePlusTenDaysTest() {

        $("[data-test-id='city'] [placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(dateGenerator(10));
        $("[data-test-id='name'] [name='name']").setValue("Иванов Иван");
        $("[data-test-id='phone'] [name='phone']").setValue("+79850000000");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + dateGenerator(10)), Duration.ofSeconds(15)).shouldBe(visible);

    }
    @Test
    void plannedDatePlusTwoDaysTest() {

        $("[data-test-id='city'] [placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(dateGenerator(2));
        $("[data-test-id='name'] [name='name']").setValue("Иванов Иван");
        $("[data-test-id='phone'] [name='phone']").setValue("+79850000000");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $$(".input__sub").find(Condition.text("Заказ на выбранную дату не возможен"));

    }

    @Test
    void cityNotOnTheListOfAdministrativeCentersTest() {

        $("[data-test-id='city'] [placeholder='Город']").setValue("Балашиха");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(dateGenerator(5));
        $("[data-test-id='name'] [name='name']").setValue("Иванов Иван");
        $("[data-test-id='phone'] [name='phone']").setValue("+79850000000");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $(".input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"));

    }

    @Test
    void nameInEnglishTest() {
        $("[data-test-id='city'] [placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(dateGenerator(5));
        $("[data-test-id='name'] [name='name']").setValue("Иванов Ivan");
        $("[data-test-id='phone'] [name='phone']").setValue("+79850000000");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $$(".input__inner").find(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }
    @Test
    void shorPhoneNumberTest (){
        $("[data-test-id='city'] [placeholder='Город']").setValue("Барнаул");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(dateGenerator(5));
        $("[data-test-id='name'] [name='name']").setValue("Иванов Иван");
        $("[data-test-id='phone'] [name='phone']").setValue("+7985000000");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $$(".input__inner").find(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void notCheckboxTest (){
        $("[data-test-id='city'] [placeholder='Город']").setValue("Барнаул");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(dateGenerator(5));
        $("[data-test-id='name'] [name='name']").setValue("Иванов Иван");
        $("[data-test-id='phone'] [name='phone']").setValue("+7985000000");
        $(".button__content").click();
        $$(".checkbox__text").find(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }
    @Test
    void cityNotTest() {

        $("[data-test-id='city'] [placeholder='Город']").setValue("");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(dateGenerator(5));
        $("[data-test-id='name'] [name='name']").setValue("Иванов Иван");
        $("[data-test-id='phone'] [name='phone']").setValue("+79850000000");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $(".input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }
    }
