package PageObjects.Railway;
import org.openqa.selenium.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import Common.Constant.Constant;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;  

public class GeneralPage {
    // Locators
    private final By tabLogin = By.xpath("//div[@id='menu']//a[@href='/Account/Login.cshtml']");
    private final By tabLogout = By.xpath("//div[@id='menu']//a[@href='/Account/Logout']");
    private final By lblWelcomeMessage = By.xpath("//div[@class='account']//strong");

    private final By tabRegister=By.xpath("//div[@id='menu']//a[@href='Account/Register.cshtml']");
    private final By tabForgotPassword = By.xpath("//a[@href='/Account/ForgotPassword.cshtml']");

    // Elements
    protected WebElement getTabLogin() {
        return Constant.WEBDRIVER.findElement(tabLogin);
    }

    protected WebElement getTabLogout() {
        return Constant.WEBDRIVER.findElement(tabLogout);
    }

    protected WebElement getLblWelcomeMessage() {
        return Constant.WEBDRIVER.findElement(lblWelcomeMessage);
    }
    protected WebElement getTabRegister(){
        return Constant.WEBDRIVER.findElement(tabRegister);
    }
    protected WebElement getForgotPassword(){return Constant.WEBDRIVER.findElement(tabForgotPassword);
    }

    // Methods
    public String getWelcomeMessage() {
        return this.getLblWelcomeMessage().getText();
    }

    public LoginPage gotoLoginPage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(tabLogin));
        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("arguments[0].click();", loginBtn);
        return new LoginPage();
    }

    public RegisterPage gotoRegisterPage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement registerBtn = wait.until(ExpectedConditions.elementToBeClickable(tabRegister));
        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("arguments[0].click();", registerBtn);
        return new RegisterPage();
    }
    public ChangePasswordPage gotoChangPassword(){
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement changePasswordTab = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='menu']//a[@href='/Account/ChangePassword.cshtml']"))
        );

        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("arguments[0].click();", changePasswordTab);
        return new ChangePasswordPage();

    }
    public ForgotPasswordPage gotoForgotPasswordPage(){
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement forgotPasswordTab = wait.until(
                ExpectedConditions.elementToBeClickable(tabForgotPassword)
        );

        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("arguments[0].click();", forgotPasswordTab);
        return new ForgotPasswordPage();
    }
    public BookTicketPage gotoBookTicketPage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement bookTicketTab = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/Page/BookTicketPage.cshtml']"))
        );
        bookTicketTab.click();
        return new BookTicketPage();
    }


}
