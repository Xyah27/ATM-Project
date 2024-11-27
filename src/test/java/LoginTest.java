import com.co.diaz.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeClass
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

    @Test
    public void testLogin() {
        // Credenciales de prueba
        String email = "example@test.com";
        String password = "password123";

        // Ejecutar flujo de login
        loginPage.openLoginPage();
        loginPage.login(email, password);

        // Validar que el login fue exitoso
        boolean isAlertPresent = driver.findElement(By.cssSelector("#account-login > div.alert.alert-danger.alert-dismissible")).isDisplayed();
        if(isAlertPresent){
            System.out.println("Login failed");
        }
        Assert.assertTrue(isAlertPresent, "Login failed");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
