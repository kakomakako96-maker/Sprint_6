import org.example.Order;
import org.example.Rent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.stream.Stream;

import static org.testng.AssertJUnit.assertTrue;

public class OrderTest {

    private WebDriver driver;
    private final By numberOrder = By.xpath(".//div[contains(text(), 'Номер заказа')]");
    private final By chekOrder = By.xpath(".//div[text() = 'Для кого самокат']");

    @BeforeEach
    void setUp() {
        String browser = System.getProperty("browser", "chrome"); // по умолчанию — chrome
        createDriver(browser);
        // переход на страницу тестового приложения
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    private void createDriver(String browser){
        // драйвер для браузера
        if (browser.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
        } else if (browser.equals("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
            driver = new FirefoxDriver(options);
        }
    }

    private static Stream<Arguments> constructorOrOrder() {
        return Stream.of(
                Arguments.of("Катя", "Марченко", "ул.Петрова", "Перово", "89996663322", "chrome"),
                Arguments.of("Катя", "Марченко", "ул.Петрова", "Перово", "89996663322", "firefox")
        );
    }

    @ParameterizedTest
    @MethodSource("constructorOrOrder")
    public void autoCompletionOrder(String name, String surname, String address, String metro, String number, String browser) {

        System.out.println("===== Начинаем тест с параметризацией =====");

        //Создание объекта класса
        Order objOrder = new Order(driver);
        //Нажимаем кнопку заказа, кнопку куки
        objOrder.clickButtonHeader();
        objOrder.clickCookie();
        //Запуск метода заполнения полей заказа
        objOrder.fillingOrder(name, surname, address, metro, number);
        System.out.println("Ввод данных: " + name + ", " + surname + ", " + address + ", " + metro + ", " + number + " в браузере:  " + browser);

        //Проверка заполнения формы "Для кого самокат"
        assertTrue("Ошибка заполнения формы", driver.findElement(By.xpath(".//div[text() = 'Про аренду']")).isDisplayed());
        System.out.println("Форма успешно заполнена");

    }

    private static Stream<Arguments> constructorOrRent() {
        return Stream.of(
                Arguments.of("11.02.2026", "сутки", "чёрный жемчуг", "нет", "chrome"),
                Arguments.of("11.02.2026", "сутки", "чёрный жемчуг", "нет", "firefox"),
                Arguments.of("1102", "одни сутки", "черный жемчуг", "-", "chrome")
        );
    }

    @ParameterizedTest
    @MethodSource("constructorOrRent")
    public void autoCompletionRent(String data, String choice, String color, String comment, String browser) {
        // переход на страницу тестового приложения
        driver.get("https://qa-scooter.praktikum-services.ru/");
        //Создаем объект order и запускаем первую часть кода
        Order objOrder = new Order(driver);
        objOrder.clickButtonHeader();
        objOrder.fillingOrder("Катя", "Марченко", "ул.Петрова", "Перово", "89996663322");

        //Создание объекта класса rent
        Rent objRent = new Rent(driver);

        System.out.println("===== Начинаем тест с параметризацией =====");

        //Запуск метода заполнения полей
        objRent.fillingRent(data, choice, color, comment);
        System.out.println("Ввод данных: " + data + ", " + choice + ", " + color + ", " + comment + " в браузере:  " + browser);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //Проверка заполнения формы
        WebElement visibleOrderPopUp = driver.findElement(By.xpath(".//div[text() = 'Хотите оформить заказ?']"));
        assertTrue("Ошибка заполнения формы \"Про аренду\"", visibleOrderPopUp.isDisplayed());

        //Подтверждаем заказ
        objRent.clickPopUp();
        //Проверка окна "Статус заказа"
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement visibleNumberOrder = wait.until(
                ExpectedConditions.visibilityOfElementLocated(numberOrder));
        assertTrue("Подтверждения заказа нет", visibleNumberOrder.isDisplayed());
    }


//Проверка второй кнопки заказа
@Test
public void twoButtonOrderTest() {
    //Создание объекта класса
    Order objOrder = new Order(driver);
    //Нажимаем кнопку заказа
    objOrder.clickButtonHomeFinish();
    //Проверка перехода на страницу аренды
    try {
        WebElement visibleOrder = driver.findElement(chekOrder);
        if (visibleOrder.isDisplayed()) {
            System.out.println("Кнопка работает");
        }
    } catch (Exception e) {
        System.out.println("Ошибка, кнопка не была нажата");
    }
}

@AfterEach
public void teardown() {
    // Закрой браузер
    driver.quit();
}
}
