package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Order {

    private final WebDriver driver;
    private final By buttonHeader = By.className("Button_Button__ra12g");
    private final By buttonHomeFinish = By.xpath(".//div[@class='Home_FinishButton__1_cWm']/button");
    private final By buttonNext = By.xpath(".//div[@class='Order_NextButton__1_rCA']/button");
    private final By name = By.xpath(".//input[@placeholder='* Имя']");
    private final By surname = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By address = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metro = By.xpath(".//input[@placeholder='* Станция метро']");
    private final String metroString = "//div[contains(text(), '%s')]";
    private final By number = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By cookie = By.xpath(".//button[@id='rcc-confirm-button']");

    public Order(WebDriver driver) {
        this.driver = driver;
    }

    //Метод клика на кнопку куки
    public void clickCookie() {
        try {
            driver.findElement(cookie).click();
        } catch (Exception e) {
            System.out.println("Ошибка при клике на кнопку: " + cookie);
        }
    }

    //Метод клика на кнопку заказа в хедере страницы
    public void clickButtonHeader() {
        driver.findElement(buttonHeader).click();
    }

    //Метод клика на кнопку заказа в хедере страницы
    public void clickButtonHomeFinish() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonHomeFinish));
            if (!button.isEnabled()) {
                System.out.println("Кнопка 'Home Finish' неактивна");
                }
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", button);
        driver.findElement(buttonHomeFinish).click();
    }


    //Метод нажатия кнопки "Далее" после заполнения данных.
    public void clickButtonNext() {
        try {
            driver.findElement(buttonNext).click();
        } catch (Exception e) {
            System.out.println("Ошибка при закрытии окна cookie");
        }
    }

    //Метод ввода поля "Имя"
    public void setUserName(String userName) {
        driver.findElement(name).sendKeys(userName);
        WebElement element = driver.findElement(By.xpath(".//div[text() = 'Введите корректное имя']"));
        if (element.isDisplayed()) {
            System.out.println("Ошибка ввода данных: " + userName);
        }

    }

    //Метод ввода поля "Фамилия"
    public void setUserSurname(String userSurname) {
        driver.findElement(surname).sendKeys(userSurname);
        WebElement element = driver.findElement(By.xpath(".//div[text() = 'Введите корректную фамилию']"));
        if (element.isDisplayed()) {
            System.out.println("Ошибка ввода данных: " + userSurname);
        }
    }

    //Метод ввода поля "Адрес"
    public void setAddress(String userAddress) {
        driver.findElement(address).sendKeys(userAddress);

        WebElement element = driver.findElement(By.xpath(".//div[text() = 'Введите корректный адрес']"));
        if (element.isDisplayed()) {
            System.out.println("Ошибка ввода данных: " + userAddress);
        }
    }

    //Метод ввода поля "Метро"
    public void setMetro(String userMetro) {
        try {
            driver.findElement(metro).click();
            driver.findElement(metro).sendKeys(userMetro);
            String metro = String.format(metroString, userMetro);
            driver.findElement(By.xpath(metro)).click();
        } catch (Exception e) {
            System.out.println("Некорректно введена станция метро: " + userMetro);
        }
    }

        //Метод ввода поля "Номер телефона"
        public void setNumber (String userNumber){
            driver.findElement(number).sendKeys(userNumber);
            //
            WebElement element = driver.findElement(By.xpath(".//div[text()='Введите корректный номер']"));
            boolean isVisible = element.isDisplayed();
            if (isVisible) {
                System.out.println("Ошибка ввода данных телефона: " + userNumber);
            }
        }

        //Метод автозаполнения данных для заказа
        public void fillingOrder (String name, String surname, String address, String metro, String number){
            setUserName(name);
            setUserSurname(surname);
            setAddress(address);
            setMetro(metro);
            setNumber(number);
            clickButtonNext();
        }

    }
