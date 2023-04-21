package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;



public class DashboardPage {

    private SelenideElement heading = $("[data-test-id=dashboard]");
    public DashboardPage() {
        heading.shouldBe(visible);
    }
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private static SelenideElement card1 = $("[data-test-id=92df3f1c-a033-48e6-8390-206f6b1f56c0]");
    private static SelenideElement card2 = $("[data-test-id=0f3f5c2a-249e-4c3d-8287-09f7a039391d]");



    public static DashboardPage moneyTransfer(DataHelper.Card cardFrom, DataHelper.Card cardTo, int sum) {
    $("[data-test-id='" + cardTo.getId() + "'] button").click();
        $("[data-test-id='amount'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='amount'] input").setValue(String.valueOf(sum));
        $("[data-test-id='from'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='from'] input").setValue(cardFrom.getCardNumber());
        $("[data-test-id='action-transfer']").click();
        return new DashboardPage();
    }

    public int getCardBalance(DataHelper.Card checkBalanceCard) {
        val text = cards.findBy(text(checkBalanceCard.getCardNumber().substring(14, 19))).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

}
