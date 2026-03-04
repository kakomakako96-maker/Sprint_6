import org.example.QuestionsClick;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.testng.AssertJUnit.assertTrue;

public class ChromeQuestionsClickTest {

    private WebDriver driver;
    private final String answer = ".//div[@aria-labelledby='accordion__heading-%d']";

    @BeforeEach
    void setUp() {
        // драйвер для браузера Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        // переход на страницу тестового приложения
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 0})

    public void checkQuestions(int number) {

        //Создание объекта класса и запуск теста
        QuestionsClick objQuestion = new QuestionsClick(driver);
        objQuestion.clickQuestion(number);
        //Проверка, что текст к вопросу отобразился
        String xpathAnswer = String.format(answer, number);
        WebElement elementAnswer = driver.findElement(By.xpath(xpathAnswer));
        assertTrue("Ответ не отобразился", elementAnswer.isDisplayed());
    }

    @AfterEach
    public void teardown() {
        // Закрой браузер
        driver.quit();
    }
}
