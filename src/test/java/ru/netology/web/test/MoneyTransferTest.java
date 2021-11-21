package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.getFirstCardNum;
import static ru.netology.web.data.DataHelper.getSecondCardNub;

class MoneyTransferTest {

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = getFirstCardNum();
        var secondCardInfo = getSecondCardNub();
        int pay = 1500;
        var expectedBalanceOneCard = dashboardPage.getOneCardBalance() - pay;
        var expectedBalanceTwoCard = dashboardPage.getTwoCardBalance() + pay;
        var transferPage = dashboardPage.selectCardToReplenishment(secondCardInfo);
        dashboardPage = transferPage.makeTransfer(String.valueOf(pay), firstCardInfo);
        var actualBalanceOneCard = dashboardPage.getOneCardBalance();
        var actualBalanceTwoCard = dashboardPage.getTwoCardBalance();
        assertEquals(expectedBalanceOneCard, actualBalanceOneCard);
        assertEquals(expectedBalanceTwoCard, actualBalanceTwoCard);


    }

}

