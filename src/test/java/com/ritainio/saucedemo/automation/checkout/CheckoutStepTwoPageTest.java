package com.ritainio.saucedemo.automation.checkout;

import com.ritainio.saucedemo.automation.DataProviderUtils;
import com.ritainio.saucedemo.automation.SauceDemoTest;
import com.ritainio.saucedemo.selenium.pom.checkout.CheckoutStepOneDto;
import com.ritainio.saucedemo.selenium.pom.checkout.CheckoutStepOnePage;
import com.ritainio.saucedemo.selenium.pom.checkout.CheckoutStepTwoDto;
import com.ritainio.saucedemo.selenium.pom.checkout.CheckoutStepTwoPage;
import com.ritainio.saucedemo.selenium.pom.login.LoginPage;
import com.ritainio.saucedemo.selenium.pom.product.ProductPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckoutStepTwoPageTest extends SauceDemoTest {
    private static String FILE_NAME = "checkout/checkoutStepTwo.csv";
    private LoginPage loginPage;
    private ProductPage productPage;
    private CheckoutStepTwoPage checkoutStepTwoPage;
    private CheckoutStepOnePage checkoutStepOnePage;


    @Override
    public void init() {
        loginPage = createPomInstance(LoginPage.class);
        productPage = createPomInstance(ProductPage.class);

        checkoutStepOnePage = createPomInstance(CheckoutStepOnePage.class);
        checkoutStepTwoPage = createPomInstance(CheckoutStepTwoPage.class);
    }

    @DataProvider(name = "getCheckoutStepTwo")
    public static Object[][] getCheckoutStepTwo(Method testMethod) {
        String testCaseCode = testMethod.getAnnotation(Test.class).testName();

        return DataProviderUtils.getDataObjectArray(CheckoutStepTwoDto.class, FILE_NAME, testCaseCode);
    }

    public void fillOutStepOnePageAndPressNext(String firstName, String lastName, String postalCode){
        try {
            checkoutStepOnePage.open();
            checkoutStepOnePage.setFirstName(firstName);
            Thread.sleep(1000);
            checkoutStepOnePage.setLastName(lastName);
            Thread.sleep(1000);
            checkoutStepOnePage.setZipCode(postalCode);
            Thread.sleep(1000);
            checkoutStepOnePage.goNext();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void goToCheckoutComplete(CheckoutStepTwoDto checkoutStepTwoDto) {
        try {
            loginPage.open()
                    .setUserName(checkoutStepTwoDto.getUserName())
                    .setPassword(checkoutStepTwoDto.getPassword())
                    .login();

            Thread.sleep(2000);
            productPage.addProductAtIndexOf(Integer.parseInt(checkoutStepTwoDto.getindex())
                    ,checkoutStepTwoDto.getCart());
        /*    fillOutStepOnePageAndPressNext
                    (checkoutStepTwoDto.getFirstName(),
                            checkoutStepTwoDto.getLastName(),checkoutStepTwoDto.getPostalCode());*/

            checkoutStepOnePage.open()
                    .setFirstName(checkoutStepTwoDto.getFirstName())
                    .setLastName(checkoutStepTwoDto.getLastName())
                    .setZipCode(checkoutStepTwoDto.getPostalCode())
                    .goNext();
            assertThat(checkoutStepTwoPage.checkProduct("1",
                    "Sauce Labs Backpack",
                    "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection."
            ,"$29.99")).isTrue();
          /*  checkoutStepOnePage.open()
                    .setFirstName(checkoutStepOnePage.)
                    .setLastName(checkoutStepOnePage.getLastName())
                    .setZipCode(checkoutStepOnePage.getZipCode())
                    .goNext();*/
            assertThat(checkoutStepTwoPage.checkTotal(32.39)).isTrue();
            Thread.sleep(1000);
            checkoutStepTwoPage.clickFinish();
            Thread.sleep(1000);
            assertThat(checkoutStepTwoPage.checkPonyExpressImage()).isTrue();

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

 /*   @Test(testName = "TC-2", description = "Last Name is required", dataProvider = "getCheckoutStepOne")
    public void testLastNameNotEmpty(CheckoutStepOneDto checkoutStepOneDto) {
        goToCheckoutStepOneFormAndFillUp(checkoutStepOneDto);*/
    @Test(testName = "TC-1", description = "Successful Checkout Backpack", dataProvider = "getCheckoutStepTwo")
    public void testPurchaseScenario(CheckoutStepTwoDto checkoutStepTwoDto) {
        goToCheckoutComplete(checkoutStepTwoDto);
        // goToCheckoutStepOneFormAndFillUp(checkoutStepOneDto);

        //assertThat(checkoutStepOnePage.isErrorMsgExist(checkoutStepOneDto.getErrorMessage())).isTrue();
    }
}
