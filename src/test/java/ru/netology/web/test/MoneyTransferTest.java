package ru.netology.web.test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.web.data.DataHelper.getCard1;
import static ru.netology.web.data.DataHelper.getCard2;


public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        open("http://localhost:9999");

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int card1Balance = dashboardPage.getCardBalance(getCard1(authInfo));
        int card2Balance = dashboardPage.getCardBalance(getCard2(authInfo));
        int amount = 7000;
        DashboardPage.moneyTransfer(DataHelper.getCard1(authInfo), DataHelper.getCard2(authInfo), amount);

        var dashboardPageAfterTransfer = new DashboardPage();
        dashboardPageAfterTransfer.getCardBalance(getCard1(authInfo));
        dashboardPageAfterTransfer.getCardBalance(getCard2(authInfo));

        Assertions.assertEquals(card1Balance - amount, dashboardPageAfterTransfer.getCardBalance(getCard1(authInfo)));
        Assertions.assertEquals(card2Balance + amount, dashboardPageAfterTransfer.getCardBalance(getCard2(authInfo)));

    }

    @Test
    void shouldNotTransferMoneyIfAmountOverBalance() {
        open("http://localhost:9999");

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int card1Balance = dashboardPage.getCardBalance(getCard1(authInfo));
        int card2Balance = dashboardPage.getCardBalance(getCard2(authInfo));
        int amount = 20000;
        DashboardPage.moneyTransfer(DataHelper.getCard1(authInfo), DataHelper.getCard2(authInfo), amount);

        var dashboardPageAfterTransfer = new DashboardPage();
        dashboardPageAfterTransfer.getCardBalance(getCard1(authInfo));
        dashboardPageAfterTransfer.getCardBalance(getCard2(authInfo));

        Assertions.assertEquals(card1Balance - amount, dashboardPageAfterTransfer.getCardBalance(getCard1(authInfo)));
        Assertions.assertEquals(card2Balance + amount, dashboardPageAfterTransfer.getCardBalance(getCard2(authInfo)));

    }
}

