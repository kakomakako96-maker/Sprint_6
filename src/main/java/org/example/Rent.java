package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Rent {

    final String BLACK = "чёрный жемчуг";
    final String GREY = "серая безысходность";
    private WebDriver driver;
    private final By data = By.xpath(".//input[@placeholder = '* Когда привезти самокат']");
    private final By dataChoice = By.xpath(".//div[contains(@class, 'react-datepicker__day') and @tabindex = '0']");
    private final By term = By.xpath("//div[contains(@class, 'Dropdown-root')]");
    private final String color = ".//input[@id='%s']";
    private final By comment = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath(".//button[contains(@class, 'Button_Middle__1CSJM') and text()='Заказать']");
    private final String termList = ".//div[text()='%s']";
    private final By popUpButton = By.xpath(".//button[text()='Да']");

    public Rent(WebDriver driver) {
        this.driver = driver;
    }

    // Метод ввода в поля «Когда привезти самокат»
    public void setData(String userData) {
        try {
            driver.findElement(data).click();
            driver.findElement(dataChoice).click();
        } catch (Exception e) {
            System.out.println("Некорректный ввод даты: " + userData);
        }
    }

    //Метод заполнения поля "Срок аренды"
    public void setTerm(String choice) {
        try {
            //Кликаем на поле
            driver.findElement(term).click();
            //Ищем элемент и кликаем на него
            String termChoice = String.format(termList, choice);
            driver.findElement(By.xpath(termChoice)).click();
        } catch (Exception e) {
            System.out.println("Ошибка ввода, нельзя выбрать пункт: " + choice);
        }
    }

    //Метод ввода в поля "Цвет самоката"
    public void setColor(String userColor) {
        try {
            if (userColor.equals(BLACK)) {
                String col = String.format(color, "black");
                driver.findElement(By.xpath(col)).click();
            } else if (userColor.equals(GREY)) {
                String col = String.format(color, "grey");
                driver.findElement(By.xpath(col)).click();
            } else {
                System.out.println("Цвет не найден: " + color);
            }
        } catch (Exception e) {
            System.out.println("Нельзя выбрать указанный цвет: " + userColor);
        }
    }

    //Метод ввода в поля "Комментарий для курьера"
    public void setComment(String userComment) {
        try {
            driver.findElement(comment).sendKeys(userComment);
        } catch (Exception e) {
            System.out.println("Ошибка ввода данных: " + userComment);
        }
    }

    public void clickOrder() {
        driver.findElement(orderButton).click();
    }

    public void fillingRent(String data, String choice, String color, String comment) {
        setData(data);
        setTerm(choice);
        setColor(color);
        setComment(comment);
        clickOrder();
    }

    //Метод поиска всплывающего окна
    public void clickPopUp() {
        try {
            WebElement element = driver.findElement(popUpButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
            driver.findElement(popUpButton).click();
        } catch (Exception e) {
            System.out.println("Ошибка подтверждения заказа");
        }
    }
}

