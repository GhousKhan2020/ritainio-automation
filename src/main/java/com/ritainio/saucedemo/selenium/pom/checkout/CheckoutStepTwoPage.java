package com.ritainio.saucedemo.selenium.pom.checkout;

import com.ritainio.saucedemo.selenium.pom.SauceDemoPom;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.ritainio.saucedemo.util.Globals.RITAIN_IO_SAUCE_DEMO_CHECKOUT_STEP_TWO_PAGE_URL;

import java.util.List;

public class CheckoutStepTwoPage extends SauceDemoPom {
    @FindBy(className = "cart_item")
    private List<WebElement> cartProductsList;

    @FindBy(className = "summary_subtotal_label")
    private WebElement subtotalLabel;

    @FindBy(className = "pony_express")
    private WebElement ponyExpreessImage;
    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    @FindBy(id="finish")
    private WebElement finish;

    public void open() {
        driver.get(RITAIN_IO_SAUCE_DEMO_CHECKOUT_STEP_TWO_PAGE_URL);
    }

    public double getSubtotal() {
        return Double.parseDouble(subtotalLabel.getText().substring(13));
    }

    public double getTax() {
        return Double.parseDouble(taxLabel.getText().substring(6));
    }

    public double getTotal() {
        return Double.parseDouble(totalLabel.getText().substring(8));
    }

    public String productQuantityAtIndexOf(int index)
    {
        return cartProductsList.get(index).findElement(By.className("cart_quantity")).getText();
    }
    public String productNameAtIndexOf(int index)
    {
        return cartProductsList.get(index).findElement(By.className("inventory_item_name")).getText();
    }
    public String productDescriptionAtIndexOf(int index)
    {
        return cartProductsList.get(index).findElement(By.className("inventory_item_desc")).getText();
    }
    public String productPriceAtIndexOf(int index)
    {
        return cartProductsList.get(index).findElement(By.className("inventory_item_price")).getText();
    }
    public Boolean checkPonyExpressImage(){
        ///static/media/pony-express.46394a5d.png
        if(ponyExpreessImage.isDisplayed() &&
                ponyExpreessImage.isEnabled() &&
                ponyExpreessImage.getAttribute("src")
                        .equals("https://www.saucedemo.com/static/media/pony-express.46394a5d.png")&&
                ponyExpreessImage.getTagName().equals("img")
        )
        {
            return  true;
        }
        else
            return false;
    }
    //inventory_item_price

    public Boolean checkProduct(String quanity, String name, String description, String price)
    {
        int index = 0 ;
        for (WebElement elem: cartProductsList) {
            String productQuantity = productQuantityAtIndexOf(index);
            String productName = productNameAtIndexOf(index);
            String productDescription = productDescriptionAtIndexOf(index);
            String productPrice = productPriceAtIndexOf(index);
            if (productName.equals(name) &&
                    productDescription.equals(description)
                    && productPrice.equals(price)
                    && productQuantity.equals(quanity)) {
                return true;
            }

        }
        return false;
    }
    public void clickFinish(){
        try {

            Thread.sleep(1000);
        finish.click();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public Boolean checkTotal(double expectedAmount){
        double actualAmount= calculateTotal()+ getTax();
        if (actualAmount==expectedAmount)
            return true;
        else
            return false;
    }

    public double calculateTotal() {
        double totalPrice = 0.0;
        for (WebElement elem: cartProductsList) {
            double currentPrice = Double.parseDouble(elem.findElement(By.className("item_pricebar")).getText().substring(1));
            totalPrice += currentPrice;
        }
        return totalPrice;
    }
}
