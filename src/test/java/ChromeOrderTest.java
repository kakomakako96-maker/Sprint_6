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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.stream.Stream;

public class ChromeOrderTest {

    private WebDriver driver;
    private final By numberOrder = By.xpath(".//div[contains(text(), 'Номер заказа')]");
    private final By chekOrder = By.xpath(".//div[text() = 'Для кого самокат']");

    @BeforeEach
    void setUp() {
        // драйвер для браузера Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        // переход на страницу тестового приложения
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    private static Stream<Arguments> constructorOrOrder() {
        return Stream.of(
                Arguments.of("Катя", "Марченко", "ул.Петрова", "Перово", "89996663322"),
                Arguments.of("Katya", "Marchenko", "ул.Петрова", "перово", "00000000000"),
                Arguments.of("Катя", "", "ул.Петрова", "Перово", "89996632")
        );
    }

    @ParameterizedTest
    @MethodSource("constructorOrOrder")
    public void autoCompletionOrder(String name, String surname, String address, String metro, String number) {

        System.out.println("===== Начинаем тест с параметризацией =====");

        //Создание объекта класса
        Order objOrder = new Order(driver);
        //Нажимаем кнопку заказа, кнопку куки
        objOrder.clickButtonHeader();
        objOrder.clickCookie();
        //Запуск метода заполнения полей заказа
        objOrder.fillingOrder(name, surname, address, metro, number);

        //Проверка заполнения формы "Для кого самокат"
        try {
            WebElement checkOrder = driver.findElement(By.xpath(".//div[text() = 'Про аренду']"));
            if (checkOrder.isDisplayed()) {
                System.out.println("Заполнили данные \"Для кого самокат\"");
            }
        } catch (Exception e) {
            System.out.println("Ошибка заполнения полей формы \"Для кого самокат\", один из параметров заполнен не верно");
        }

    }

    private static Stream<Arguments> constructorOrRent() {
        return Stream.of(
                Arguments.of("11.02.2026", "сутки", "чёрный жемчуг", "нет"),
                Arguments.of("11022026", "", "серая безысходность", ""),
                Arguments.of("1102", "одни сутки", "черный жемчуг", "-")
        );
    }

    @ParameterizedTest
    @MethodSource("constructorOrRent")
    public void autoCompletionRent(String data, String choice, String color, String comment) {
        //Создаем объект order и запускаем первую часть кода
        Order objOrder = new Order(driver);
        objOrder.clickButtonHeader();
        objOrder.fillingOrder("Катя", "Марченко", "ул.Петрова", "Перово", "89996663322");

        //Создание объекта класса rent
        Rent objRent = new Rent(driver);

        System.out.println("===== Начинаем тест с параметризацией =====");

        //Запуск метода заполнения полей
        objRent.fillingRent(data, choice, color, comment);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //Проверка заполнения формы
        try {
            WebElement visibleOrderPopUp = driver.findElement(By.xpath(".//div[text() = 'Хотите оформить заказ?']"));
            if (visibleOrderPopUp.isDisplayed()) {
                System.out.println("Заполнили данные \"Про аренду\"");
                //Подтверждаем заказ
                objRent.clickPopUp();
                //Проверка окна "Статус заказа"
                try {
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    WebElement visibleNumberOrder = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(numberOrder));
                    if (visibleNumberOrder.isDisplayed()) {
                        System.out.println("Подтверждение заказа есть");
                    }
                } catch (Exception e) {
                    System.out.println("Подтверждения заказа нет");
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка заполнения формы \"Про аренду\", один из параметров заполнен не верно");
        }
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
