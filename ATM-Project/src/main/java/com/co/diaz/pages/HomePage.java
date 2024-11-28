package com.co.diaz.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;

    // Selector
    private By accountName = By.cssSelector("a[title='My Account']");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Método para validar que el usuario ha iniciado sesión
    public boolean isUserLoggedIn(String expectedName) {
        String actualName = driver.findElement(accountName).getText();
        return actualName.contains(expectedName);
    }
}
