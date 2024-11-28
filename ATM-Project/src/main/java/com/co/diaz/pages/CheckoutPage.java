package com.co.diaz.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckoutPage {
    private WebDriver driver;

    // Selectores existentes
    private By searchBox = By.name("search");
    private By searchButton = By.cssSelector("button.btn.btn-default.btn-lg");
    private By searchResults = By.xpath("//div[@class='product-thumb']");
    private By shoppingCartLink = By.cssSelector("a[title='Shopping Cart']");
    private By checkoutButton = By.cssSelector("a.btn.btn-primary");
    private By confirmOrderButton = By.id("button-confirm");

    // Selectores para los detalles de facturación
    private By firstNameInput = By.id("input-payment-firstname");
    private By lastNameInput = By.id("input-payment-lastname");
    private By companyInput = By.id("input-payment-company");
    private By address1Input = By.id("input-payment-address-1");
    private By address2Input = By.id("input-payment-address-2");
    private By cityInput = By.id("input-payment-city");
    private By postCodeInput = By.id("input-payment-postcode");
    private By countryDropdown = By.id("input-payment-country");
    private By regionDropdown = By.id("input-payment-zone");
    private By continueBillingButton = By.id("button-payment-address");

    // Selector para el botón "Continue" en Delivery Details
    private By continueDeliveryButton = By.id("button-shipping-address");

    // Selector para el área de comentarios en Delivery Details
    private By deliveryCommentsArea = By.name("comment");

    // Constructor
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    // Método para buscar un producto
    public void searchProduct(String productName) {
        WebElement searchInput = driver.findElement(searchBox);
        searchInput.clear();
        searchInput.sendKeys(productName);
        driver.findElement(searchButton).click(); // Hacer clic en el botón de búsqueda
    }

    // Método para agregar un producto al carrito
    public void addToCart(String productName, int quantity) {
        List<WebElement> products = driver.findElements(searchResults);
        for (WebElement product : products) {
            if (product.getText().contains(productName)) {
                WebElement addToCartButton = product.findElement(By.xpath(".//button[contains(@onclick, 'cart.add')]"));
                for (int i = 0; i < quantity; i++) {
                    addToCartButton.click();
                }
                System.out.println("Producto agregado al carrito: " + productName);
                return;
            }
        }
        System.err.println("Producto no encontrado: " + productName);
    }

    // Método para ir al carrito
    public void viewCart() {
        driver.findElement(shoppingCartLink).click();
    }

    // Método para proceder al checkout
    public void checkout() {
        driver.findElement(checkoutButton).click();
    }

    // Método para completar el checkout
    public void completeCheckout() {
        driver.findElement(confirmOrderButton).click();
        System.out.println("Orden confirmada.");
    }

    // Método para llenar los detalles de facturación
    public void fillBillingDetails(String firstName, String lastName, String company, String address1,
                                   String address2, String city, String postCode, String country, String region) {
        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(companyInput).sendKeys(company);
        driver.findElement(address1Input).sendKeys(address1);
        driver.findElement(address2Input).sendKeys(address2);
        driver.findElement(cityInput).sendKeys(city);
        driver.findElement(postCodeInput).sendKeys(postCode);

        // Seleccionar país
        WebElement countrySelectElement = driver.findElement(countryDropdown);
        Select countrySelect = new Select(countrySelectElement);
        countrySelect.selectByVisibleText(country);

        // Seleccionar región/estado
        WebElement regionSelectElement = driver.findElement(regionDropdown);
        Select regionSelect = new Select(regionSelectElement);
        regionSelect.selectByVisibleText(region);
    }
    public void clickContinueDeliveryDetails() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

            // Espera explícita para que el botón sea clickeable
            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(continueDeliveryButton));

            // Desplázate al botón si no está visible
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);

            // Verifica que no esté en estado "Loading..." antes de hacer clic
            while ("Loading...".equals(continueButton.getAttribute("value"))) {
                Thread.sleep(500); // Espera medio segundo
            }

            // Clic en el botón
            continueButton.click();
            System.out.println("Hiciste clic en 'Continue' en Delivery Details.");
        } catch (Exception e) {
            System.err.println("Error al hacer clic en 'Continue' en Delivery Details: " + e.getMessage());
        }
    }

    // Método para continuar después de llenar los detalles de facturación
    public void clickContinueBillingDetails() {
        driver.findElement(continueBillingButton).click();
    }

    // Método para rellenar comentarios y hacer clic en "Continue" en Delivery Details
    public void fillAndSubmitDeliveryDetails(String comments) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Espera a que el área de comentarios esté visible
            WebElement commentArea = wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryCommentsArea));

            // Limpia y rellena el área de comentarios con texto de ejemplo
            commentArea.clear();
            commentArea.sendKeys(comments);
            System.out.println("Texto de ejemplo ingresado en el área de comentarios.");

            // Espera explícita para que el botón "Continue" sea clickeable
            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(continueDeliveryButton));

            // Desplázate al botón si no está visible
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);

            // Verifica que no esté en estado "Loading..." antes de hacer clic
            while ("Loading...".equals(continueButton.getAttribute("value"))) {
                Thread.sleep(500); // Espera medio segundo
            }

            // Clic en el botón "Continue"
            continueButton.click();
            System.out.println("Hiciste clic en 'Continue' después de ingresar los comentarios.");
        } catch (Exception e) {
            System.err.println("Error al completar los detalles de entrega: " + e.getMessage());
        }
    }
}
