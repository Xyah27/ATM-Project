import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.co.diaz.pages.CheckoutPage;

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

        CheckoutPage checkoutPage = new CheckoutPage(driver);

        try (FileInputStream fis = new FileInputStream("src/test/resources/testdata.xlsx");
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Leer datos del archivo Excel
            Row macRow = sheet.getRow(0); // Primera fila
            String macProduct = macRow.getCell(0).getStringCellValue();
            int macQuantity = getNumericCellValue(macRow.getCell(1));

            Row iphoneRow = sheet.getRow(1); // Segunda fila
            String iphoneProduct = iphoneRow.getCell(0).getStringCellValue();
            int iphoneQuantity = getNumericCellValue(iphoneRow.getCell(1));

            // Flujo de búsqueda y compra
            checkoutPage.searchProduct(macProduct);
            checkoutPage.addToCart(checkoutPage.addToCartMac, macQuantity);

            checkoutPage.searchProduct(iphoneProduct);
            checkoutPage.addToCart(checkoutPage.addToCartIphone, iphoneQuantity);

            checkoutPage.viewCart();
            checkoutPage.checkout();
            checkoutPage.completeCheckout();

            System.out.println("Flujo completado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static int getNumericCellValue(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            return Integer.parseInt(cell.getStringCellValue());
        } else {
            throw new IllegalStateException("Cannot get a NUMERIC value from a non-numeric cell");
        }
    }
}