import org.example.QuestionsClick;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeQuestionsClickTest {

    private WebDriver driver;

    @Test
    public void checkQuestions() {
        // драйвер для браузера Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);

        // переход на страницу тестового приложения
        driver.get("https://qa-scooter.praktikum-services.ru/");

        //Создание объекта класса и запуск теста
        QuestionsClick objQuestion = new QuestionsClick(driver);
        objQuestion.clickQuestion();

    }

    @AfterEach
    public void teardown() {
        // Закрой браузер
        driver.quit();
    }
}
