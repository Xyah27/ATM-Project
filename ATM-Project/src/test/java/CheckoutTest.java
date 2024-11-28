import com.co.diaz.pages.CheckoutPage;
import com.co.diaz.pages.LoginPage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

public class CheckoutTest {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://opencart.abstracta.us/index.php?route=common/home");

        // Inicializar páginas
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        try (FileInputStream fis = new FileInputStream("src/test/resources/testdata.xlsx");
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet productSheet = workbook.getSheet("Sheet2");

            // Leer productos y cantidades
            for (int i = 1; i <= productSheet.getLastRowNum(); i++) {
                Row row = productSheet.getRow(i);
                if (row != null) {
                    String productName = row.getCell(0).getStringCellValue();
                    int quantity = (int) row.getCell(1).getNumericCellValue();

                    System.out.println("Buscando producto: " + productName + ", cantidad: " + quantity);
                    checkoutPage.searchProduct(productName);
                    pause(2000);

                    checkoutPage.addToCart(productName, quantity);
                    pause(2000);
                }
            }

            // Visualizar carrito
            checkoutPage.viewCart();
            pause(3000);

            // Proceder al checkout
            checkoutPage.checkout();
            pause(3000);

            // Iniciar sesión
            loginPage.fillLoginForm("john.doeefgjkgdh@test.com", "password123");
            loginPage.clickLoginButton();
            pause(3000);
            // Continuar en Delivery Details
            checkoutPage.clickContinueDeliveryDetails();
            pause(3000);

            System.out.println("Continuando después de los detalles de facturación...");
            checkoutPage.clickContinueBillingDetails();
            pause(3000);

            System.out.println("Continuando después de los detalles de entrega...");
            checkoutPage.clickContinueDeliveryDetails();
            pause(3000);

            // Rellenar comentarios y continuar
            checkoutPage.fillAndSubmitDeliveryDetails("Este es un comentario de prueba para los detalles de entrega.");
            checkoutPage.clickContinueDeliveryDetails();

            // Completar checkout
            checkoutPage.completeCheckout();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static void pause(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }
}
