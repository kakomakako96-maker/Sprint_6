package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class QuestionsClick {

    private WebDriver driver;
    private int number;
    private static final By QUESTION_XPATH = By.xpath(".//div[contains(@id, 'accordion__heading-')]");
    private final String question = ".//div[contains(@id, 'accordion__heading-%d')]";

    public QuestionsClick(WebDriver driver) {
        this.driver = driver;
    }

    public void clickQuestion(int number) {
        this.number = number;
        String xpathQuestion = String.format(question, number);
        WebElement elementQuestions = driver.findElement(By.xpath(xpathQuestion));

        //Скролим и нажимаем на вопрос
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elementQuestions);
        elementQuestions.click();
    }
}
