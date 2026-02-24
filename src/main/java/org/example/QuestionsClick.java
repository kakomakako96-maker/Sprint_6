package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class QuestionsClick {

    private WebDriver driver;
    private static final By QUESTION_XPATH = By.xpath(".//div[contains(@id, 'accordion__heading-')]");
    private final String question = ".//div[contains(@id, 'accordion__heading-%d')]";
    private final String answer = ".//div[@aria-labelledby='accordion__heading-%d']";

    public QuestionsClick(WebDriver driver) {
        this.driver = driver;
    }

    public void clickQuestion() {
        List<WebElement> elementQuestionsAll = driver.findElements(QUESTION_XPATH);
        for (int i = 0; i <= elementQuestionsAll.size()-1; i++) {
            String xpathQuestion = String.format(question, i);
            WebElement elementQuestions = driver.findElement(By.xpath(xpathQuestion));

            //Скролим и нажимаем на вопрос
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elementQuestions);
            elementQuestions.click();

            //Проверка, что текст к вопросу отобразился
            String xpathAnswer = String.format(answer, i);
            WebElement elementAnswer = driver.findElement(By.xpath(xpathAnswer));
            if (elementAnswer.isDisplayed()) {
                System.out.println("Ответ на вопрос " + (i+1) + " отобразился");
            } else {
                System.out.println("Ответ на вопрос " + (i+1) + " не отобразился");
            }
        }
    }
}
