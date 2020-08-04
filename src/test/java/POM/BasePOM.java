package POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.BaseDriver;

public class BasePOM {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePOM() {
        driver = BaseDriver.getDriver();
        wait = new WebDriverWait(driver, 10);
    }
    public void waitAndClick(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).click();
    }
    public void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
    }
    public void waitAndSendKeys(By locator, String text) {
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
    }
    public void waitAndConfirm(By locator, String str){
        WebElement webElement=wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Assert.assertTrue(driver.findElement(locator).getText().contains(str));
    }
    public double StringToDouble(String str){
        return Double.parseDouble(str.replace("$",""));
    }
}
