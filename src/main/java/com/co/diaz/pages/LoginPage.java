package com.co.diaz.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    // Selectores de los elementos del DOM
    private By myAccountDropdown = By.cssSelector("#top-links > ul > li.dropdown");
    private By loginOption = By.cssSelector("#top-links > ul > li.dropdown.open > ul > li:nth-child(2)");
    private By emailInput = By.cssSelector("#input-email");
    private By passwordInput = By.cssSelector("#input-password");
    private By loginButton = By.cssSelector("#content > div > div:nth-child(2) > div > form > input");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Métodos para interactuar con los elementos
    public void openLoginPage() {
        driver.findElement(myAccountDropdown).click();
        driver.findElement(loginOption).click();
    }

    public void login(String email, String password) {
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
    }
}
