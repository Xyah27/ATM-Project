package com.co.diaz.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private WebDriver driver;

    // Selectores
    private By searchBox = By.cssSelector("#search > input");
    private By searchButton = By.cssSelector("#search > span > button");
    public By addToCartMac = By.xpath("//*[@id='content']/div[3]/div[1]/div/div[2]/div[2]/button[1]");
    public By addToCartIphone = By.xpath("//*[@id='content']/div[3]/div/div/div[2]/div[2]/button[1]");
    private By viewCartButton = By.cssSelector("#cart > button");
    private By checkoutButton = By.cssSelector("#cart > ul > li:nth-child(2) > div > p > a:nth-child(2) > strong");
    private By continueButton1 = By.cssSelector("#collapse-payment-address > div > form > div.buttons.clearfix > div");
    private By continueButton2 = By.cssSelector("#button-shipping-address");
    private By continueButton3 = By.cssSelector("#button-shipping-method");
    private By checkbox = By.cssSelector("#collapse-payment-method > div > div.buttons > div > input[type=checkbox]:nth-child(2)");
    private By paymentButton = By.cssSelector("#button-payment-method");

    // Constructor
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    // Métodos para acciones en el flujo
    public void searchProduct(String productName) {
        driver.findElement(searchBox).sendKeys(productName);
        driver.findElement(searchButton).click();
    }

    public void addToCart(By addToCartButton, int quantity) {
        for (int i = 0; i < quantity; i++) {
            driver.findElement(addToCartButton).click();
        }
    }

    public void viewCart() {
        driver.findElement(viewCartButton).click();
    }

    public void checkout() {
        driver.findElement(checkoutButton).click();
    }

    public void completeCheckout() {
        driver.findElement(continueButton1).click();
        driver.findElement(continueButton2).click();
        driver.findElement(continueButton3).click();
        driver.findElement(checkbox).click();
        driver.findElement(paymentButton).click();
    }
}
