package com.ritainio.saucedemo.selenium.pom.checkout;

import com.ritainio.saucedemo.selenium.pom.TestCaseDto;
import com.univocity.parsers.annotations.Parsed;

public class CheckoutStepTwoDto extends TestCaseDto {
    //testCaseCode,testCaseDescription,userName,password,item,desc,price
    @Parsed(field = "userName", defaultNullRead = "")
    private String userName;

    @Parsed(field = "password", defaultNullRead = "")
    private String password;



    @Parsed(field = "index", defaultNullRead = "")
    private String index;

/*    @Parsed(field = "desc", defaultNullRead = "")
    private String desc;
    @Parsed(field = "price", defaultNullRead = "")
    private String price;*/


//,firstName,lastName,postalCode
@Parsed(field = "firstName", defaultNullRead = "")
private String firstName;

    @Parsed(field = "lastName", defaultNullRead = "")
    private String lastName;

    @Parsed(field = "postalCode", defaultNullRead = "")
    private String postalCode;
    @Parsed(field = "cart", defaultNullRead = "")
    private String cart;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
    public String getindex() { return index; }
/*
    public String getDesc(){ return desc; }

    public String getPrice() { return price; }*/
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public String getCart() {
        return cart;
    }
}
