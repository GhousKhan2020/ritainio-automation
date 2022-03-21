package com.ritainio.saucedemo.automation.checkout;

import com.ritainio.saucedemo.automation.DataProviderUtils;
import com.ritainio.saucedemo.automation.SauceDemoTest;
import com.ritainio.saucedemo.selenium.pom.checkout.CheckoutStepOneDto;
import com.ritainio.saucedemo.selenium.pom.checkout.CheckoutStepOnePage;
import com.ritainio.saucedemo.selenium.pom.login.LoginPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckoutStepOnePageTest extends SauceDemoTest {
    private static String FILE_NAME = "checkout/checkoutStepOne.csv";
    private LoginPage loginPage;
    private CheckoutStepOnePage checkoutStepOnePage;

    @Override
    public void init() {
        loginPage = createPomInstance(LoginPage.class);
        checkoutStepOnePage = createPomInstance(CheckoutStepOnePage.class);
    }

    @DataProvider(name = "getCheckoutStepOne")
    public static Object[][] getCheckoutStepOne(Method testMethod) {
        String testCaseCode = testMethod.getAnnotation(Test.class).testName();

        return DataProviderUtils.getDataObjectArray(CheckoutStepOneDto.class, FILE_NAME, testCaseCode);
    }

    public void goToCheckoutStepOneFormAndFillUp(CheckoutStepOneDto checkoutStepOneDto) {
        try {
        loginPage.open()
                .setUserName(checkoutStepOneDto.getUserName())
                .setPassword(checkoutStepOneDto.getPassword())
                .login();

            Thread.sleep(2000);


        checkoutStepOnePage.open()
                           .setFirstName(checkoutStepOneDto.getFirstName())
                           .setLastName(checkoutStepOneDto.getLastName())
                           .setZipCode(checkoutStepOneDto.getZipCode())
                           .goNext();
            Thread.sleep(2000);
        }
         catch (InterruptedException e) {
             e.printStackTrace();
         }
    }

    @Test(testName = "TC-1", description = "First Name is required", dataProvider = "getCheckoutStepOne")
    public void testFirstNameNotEmpty(CheckoutStepOneDto checkoutStepOneDto) {
        goToCheckoutStepOneFormAndFillUp(checkoutStepOneDto);
        assertThat(checkoutStepOnePage.isErrorMsgExist(checkoutStepOneDto.getErrorMessage())).isTrue();
    }

    @Test(testName = "TC-2", description = "Last Name is required", dataProvider = "getCheckoutStepOne")
    public void testLastNameNotEmpty(CheckoutStepOneDto checkoutStepOneDto) {
        goToCheckoutStepOneFormAndFillUp(checkoutStepOneDto);
        assertThat(checkoutStepOnePage.isErrorMsgExist(checkoutStepOneDto.getErrorMessage())).isTrue();
    }

    @Test(testName = "TC-3", description = "Postal Code is required", dataProvider = "getCheckoutStepOne")
    public void testPostalCodeNotEmpty(CheckoutStepOneDto checkoutStepOneDto) {
        goToCheckoutStepOneFormAndFillUp(checkoutStepOneDto);

        assertThat(checkoutStepOnePage.isErrorMsgExist(checkoutStepOneDto.getErrorMessage())).isTrue();
    }
}
