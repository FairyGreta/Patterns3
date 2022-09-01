package ru.netology.delivery.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @AfterEach
    void clear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() throws InterruptedException{
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
//        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
//        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
//        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
//        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
//        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] span").click();
        $x("//*[contains(text(), 'Запланировать')]").click();
        $(".notification__content").shouldBe(visible).shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate));
        TimeUnit.SECONDS.sleep(1);
        $("[data-test-id='date'] input").doubleClick().sendKeys(firstMeetingDate);
        $(By.className("button")).click();
        $x("//span[contains(text(), 'Перепланировать')]").click();
        TimeUnit.SECONDS.sleep(1);
        $("[data-test-id='date'] input").doubleClick().sendKeys(secondMeetingDate);
        $(By.className("button")).click();
        $x("//span[contains(text(), 'Перепланировать')]").click();
        TimeUnit.SECONDS.sleep(1);
        $(".notification__content").shouldBe(visible).shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate));
    }
















}
