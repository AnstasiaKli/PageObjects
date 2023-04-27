package ru.netology.web.test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyFromCard2ToCard1() {
        open("http://localhost:9999");

        int amount = 200;
        String cardNumber = "5559 0000 0000 0002";

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        int card1BalanceBeforeTransfer = dashboardPage.getCardBalance(1);
        int card2BalanceBeforeTransfer = dashboardPage.getCardBalance(2);

        var transferPage = dashboardPage.cardTopUp(1);
        transferPage.cardTopUp(String.valueOf(amount), DataHelper.Card2().getCardNumber());
        dashboardPage.updateButton();

        int card1BalanceAfterTransfer = dashboardPage.getCardBalance(1);
        int card2BalanceAfterTransfer = dashboardPage.getCardBalance(2);

        Assertions.assertEquals(card1BalanceBeforeTransfer + amount, card1BalanceAfterTransfer);
        Assertions.assertEquals(card2BalanceBeforeTransfer - amount, card2BalanceAfterTransfer);
    }

    @Test
    void shouldTransferMoneyFromCard1ToCard2() {
        open("http://localhost:9999");

        int amount = 200;

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        int card1BalanceBeforeTransfer = dashboardPage.getCardBalance(1);
        int card2BalanceBeforeTransfer = dashboardPage.getCardBalance(2);

        var transferPage = dashboardPage.cardTopUp(2);
        transferPage.cardTopUp(String.valueOf(amount), DataHelper.Card1().getCardNumber());
        dashboardPage.updateButton();

        int card1BalanceAfterTransfer = dashboardPage.getCardBalance(1);
        int card2BalanceAfterTransfer = dashboardPage.getCardBalance(2);

        Assertions.assertEquals(card1BalanceBeforeTransfer - amount, card1BalanceAfterTransfer);
        Assertions.assertEquals(card2BalanceBeforeTransfer + amount, card2BalanceAfterTransfer);
    }

    @Test
    void shouldNotTransferMoneyIfAmountOverBalance() {
        open("http://localhost:9999");

        int amount = 20000;

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var transferPage = dashboardPage.cardTopUp(1);
<<<<<<< HEAD
        transferPage.incorrectAmount(String.valueOf(amount), DataHelper.Card2().getCardNumber());
        $("[data-test-id=error-notification]").should(appear);
=======
        transferPage.cardTopUp(String.valueOf(amount), DataHelper.Card2().getCardNumber());
        dashboardPage.updateButton();

        int card1BalanceAfterTransfer = dashboardPage.getCardBalance(1);
        int card2BalanceAfterTransfer = dashboardPage.getCardBalance(2);

        Assertions.assertEquals(card1BalanceBeforeTransfer + amount, card1BalanceAfterTransfer);
        Assertions.assertEquals(card2BalanceBeforeTransfer - amount, card2BalanceAfterTransfer);
>>>>>>> c29010d4c26d551da33acec6be86c88896331a0e
    }
}
