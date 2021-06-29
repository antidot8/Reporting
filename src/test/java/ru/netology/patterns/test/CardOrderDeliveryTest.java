package ru.netology.patterns.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.*;
import static ru.netology.patterns.data.DataGenerator.*;
import static ru.netology.patterns.data.DataGenerator.Generate.generateUser;


public class CardOrderDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var user = generateUser();
        var daysToAddForFirstMeeting = 3;
        var firstMeetingDate = generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 4;
        var secondMeetingDate = generateDate(daysToAddForSecondMeeting);
        $("[data-test-id=city] .input__control").setValue(user.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(SHIFT, HOME, DELETE));
        $("[data-test-id=date] .input__control").setValue(firstMeetingDate);
        $("[data-test-id=name] .input__control").setValue(user.getName());
        $("[data-test-id=phone] .input__control").setValue(user.getPhone());
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id='success-notification'] .icon_name_close").click();
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(SHIFT, HOME, DELETE));
        $("[data-test-id=date] .input__control").setValue(secondMeetingDate);
        $(".button").click();
        $("[data-test-id='replan-notification'] .button").click();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }
}