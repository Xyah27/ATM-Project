import com.co.diaz.pages.LoginPage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        // Configurar WebDriver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Inicializar la página de login
        loginPage = new LoginPage(driver);
        driver.get("https://opencart.abstracta.us/index.php?route=common/home");
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String email, String password) {
        // Navegar a la página de login
        loginPage.openLoginPage();

        // Llenar el formulario de login
        loginPage.fillLoginForm(email, password);

        // Hacer clic en el botón de login
        loginPage.clickLoginButton();
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws IOException {
        return readExcelData("src/main/resources/testdata.xlsx", "Sheet1");
    }

    public Object[][] readExcelData(String filePath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
        Object[][] data = new Object[rowCount - 1][colCount];

        // Leer los datos desde la segunda fila (excluyendo los encabezados)
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                data[i - 1][j] = (cell != null) ? cell.toString() : ""; // Manejar celdas vacías
            }
        }

        workbook.close();
        fis.close();
        return data;
    }

    @AfterMethod
    public void tearDown() {
        // Cerrar el navegador después de cada prueba
        if (driver != null) {
            driver.quit();
        }
    }
}
