package ru.netology.testmode.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.UserInfo;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.*;

public class AuthTest {
    @BeforeEach
    void setupTest() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSendForm() {
        UserInfo activeData = activeUser();
        $("[data-test-id='login'] input").setValue(activeData.getLogin());
        $("[data-test-id='password'] input").setValue(activeData.getPassword());
        $("[data-test-id='action-login']").click();
        $(".heading").shouldHave(text("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldSendFormWithBlockedUser() {
        UserInfo blockedData = blockedUser();
        $("[data-test-id='login'] input").setValue(blockedData.getLogin());
        $("[data-test-id='password'] input").setValue(blockedData.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Ошибка! Пользователь заблокирован")).shouldBe(visible);
    }

    @Test
    void shouldSendFormWithInvalidLogin() {
        String invalidLoginData = generateLogin();
        UserInfo activeData = activeUser();
        $("[data-test-id=login] input").setValue(invalidLoginData);
        $("[data-test-id='password'] input").setValue(activeData.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldSendFormWithInvalidPassword() {
        String invalidPasswordData = generatePassword();
        UserInfo activeData = activeUser();
        $("[data-test-id='login'] input").setValue(activeData.getLogin());
        $("[data-test-id=password] input").setValue(invalidPasswordData);
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }
}