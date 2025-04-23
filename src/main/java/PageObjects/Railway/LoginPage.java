package PageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import Common.Constant.Constant;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    // Locators
    private final By txtUsername = By.xpath("//input[@id='username']");
    private final By txtPassword = By.xpath("//input[@id='password']");
    private final By btnLogin = By.xpath("//input[@type='submit' and @value='login']");
    private final By bookTicketTab = By.xpath("//a[@href='/Page/BookTicketPage.cshtml']");
    private final By tickLogin = By.xpath("//a[@href='/Account/Login.cshtml']");
    private final By forgotPasswordLink = By.xpath("//a[@href='/Account/ForgotPassword.cshtml']");

    // Elements
    private WebElement getTxtUsername() {
        return Constant.WEBDRIVER.findElement(txtUsername);
    }

    private WebElement getTxtPassword() {
        return Constant.WEBDRIVER.findElement(txtPassword);
    }

    private WebElement getBtnLogin() {
        return Constant.WEBDRIVER.findElement(btnLogin);
    }

    private WebElement getForgotPasswordLink() {
        return Constant.WEBDRIVER.findElement(forgotPasswordLink);
    }

    // Methods
    public LoginPage open() {
        Constant.WEBDRIVER.navigate().to(Constant.RAILWAY_URL + "/Account/Login.cshtml");
        return this;
    }

    public ForgotPasswordPage gotoForgotPasswordPage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink));
        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("arguments[0].click();", link);
        return new ForgotPasswordPage();
    }

    public void login(String username, String password) {
        getTxtUsername().sendKeys(username);
        getTxtPassword().sendKeys(password);

        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(getBtnLogin()));

        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("arguments[0].click();", getBtnLogin());
    }

    public String getErrorMessage() {
        WebElement errorMsg = Constant.WEBDRIVER.findElement(By.cssSelector("p.message.error.LoginForm"));
        return errorMsg.getText().trim();
    }

    public String getPageTitle() {
        return Constant.WEBDRIVER.getTitle();
    }

    public void clickBookTicketTab() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement bookTicketBtn = wait.until(ExpectedConditions.elementToBeClickable(bookTicketTab));
        bookTicketBtn.click();
    }

    public void clickLogin() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(tickLogin));
        loginBtn.click();
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