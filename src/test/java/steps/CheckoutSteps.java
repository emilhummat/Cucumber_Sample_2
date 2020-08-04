package steps;

import POM.BasePOM;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.BaseDriver;

import java.util.List;
import java.util.Random;

public class CheckoutSteps {
    public WebDriver driver = BaseDriver.getDriver();
    BasePOM basePOM = new BasePOM();

    @Given("^Navigate to the website$")
    public void navigate_to_the_website() {
//        driver.manage().window().maximize();
        driver.get("http://automationpractice.com/index.php ");
    }

    @When("^I use legit username and password$")
    public void i_use_legit_username_and_password() {
        basePOM.waitAndClick(By.cssSelector(".login"));
        basePOM.waitAndSendKeys(By.id("email"), "qwerty0123@gmail.com");
        basePOM.waitAndSendKeys(By.id("passwd"), "123456789");
        basePOM.waitAndClick(By.cssSelector(".icon-lock.left"));
    }

    @Then("^I am logged in$")
    public void i_am_logged_in() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("My account - My Store"));
    }

    @Given("^Navigate to the dresses page$")
    public void navigateToTheDressesPage() {
        basePOM.waitAndClick(By.xpath("(//a[@title='Dresses'])[2]"));
    }

    @And("^Choose a random item and add it to the cart$")
    public void chooseARandomItemAndAddItToTheCart() throws InterruptedException {
        List<WebElement> itemList = driver.findElements(By.cssSelector(".product_img_link"));
        int num=new Random().nextInt(itemList.size());
        basePOM.waitAndClick(itemList.get(num));
        basePOM.waitAndClick(By.cssSelector("button[name='Submit']"));
        basePOM.waitAndClick(By.cssSelector("a[title='Proceed to checkout']"));
    }

    @And("^Verify the total price of the product$")
    public void verifyTheTotalPriceOfTheProduct() {
        String totalProductsPrice = driver.findElement(By.id("total_product")).getText();
        double totalProductsPriceDouble = basePOM.StringToDouble(totalProductsPrice);
        String totalShippingPrice = driver.findElement(By.id("total_shipping")).getText();
        double totalShippingPriceDouble = basePOM.StringToDouble(totalShippingPrice);
        String taxPrice = driver.findElement(By.id("total_tax")).getText();
        double taxPriceDouble = basePOM.StringToDouble(taxPrice);
        String totalPrice = driver.findElement(By.id("total_price")).getText();
        double totalPriceDouble = basePOM.StringToDouble(totalPrice);
        Assert.assertEquals(totalPriceDouble,totalProductsPriceDouble+totalShippingPriceDouble+taxPriceDouble);
    }

    @And("^Proceed to the checkout page$")
    public void proceedToTheCheckoutPage() {
        basePOM.waitAndClick(By.xpath("(//a[@title='Proceed to checkout'])[2]"));
        basePOM.waitAndClick(By.cssSelector("button[name='processAddress']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        basePOM.waitAndClick(By.id("uniform-cgv"));
        basePOM.waitAndClick(By.xpath("(//span[contains(text(),'Proceed to checkout')])[2]"));
    }

    @When("^I do payment and confirm it$")
    public void iDoPaymentAdnConfirmIt() {
        basePOM.waitAndClick(By.cssSelector(".bankwire"));
        basePOM.waitAndClick(By.xpath("//span[contains(text(),'I confirm my order')]"));
    }

    @Then("^Order confirmation message should be appeared$")
    public void orderConfirmationMessageShouldBeAppeared() {
        String expected="Your order on My Store is complete.";
        String actual=driver.findElement(By.cssSelector(".cheque-indent")).getText();
        Assert.assertEquals(expected,actual);
    }
}
