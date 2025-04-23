package PageObjects.Railway;

import Common.Constant.Constant;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgotPasswordPage {
    // Locators for Forgot Password form (email submission)
    private final By txtEmail = By.xpath("//input[@id='email']");
    private final By btnSendInstructions = By.xpath("//input[@type='submit' and @value='Send Instructions']");
    private final By lblErrorMessage = By.cssSelector("p.message.error");

    // Locators for Password Change Form (reset password)
    private final By txtNewPassword = By.xpath("//input[@id='newPassword']");
    private final By txtConfirmPassword = By.xpath("//input[@id='confirmPassword']");
    private final By txtResetToken = By.xpath("//input[@id='resetToken']"); // Optional, for TC12
    private final By btnResetPassword = By.xpath("//input[@type='submit' and @value='Reset Password']");
    private final By lblMainErrorMessage = By.cssSelector("p.message.error");
    private final By lblConfirmPasswordError = By.cssSelector("label.validation-error[for='confirmPassword']");
    private final By lblTokenErrorMessage = By.cssSelector("label.validation-error[for='resetToken']");

    // Elements for Forgot Password form
    private WebElement getTxtEmail() {
        return Constant.WEBDRIVER.findElement(txtEmail);
    }

    private WebElement getBtnSendInstructions() {
        return Constant.WEBDRIVER.findElement(btnSendInstructions);
    }

    private WebElement getLblErrorMessage() {
        return Constant.WEBDRIVER.findElement(lblErrorMessage);
    }

    // Elements for Password Change Form
    private WebElement getTxtNewPassword() {
        return Constant.WEBDRIVER.findElement(txtNewPassword);
    }

    private WebElement getTxtConfirmPassword() {
        return Constant.WEBDRIVER.findElement(txtConfirmPassword);
    }

    private WebElement getTxtResetToken() {
        return Constant.WEBDRIVER.findElement(txtResetToken);
    }

    private WebElement getBtnResetPassword() {
        return Constant.WEBDRIVER.findElement(btnResetPassword);
    }

    private WebElement getLblMainErrorMessage() {
        return Constant.WEBDRIVER.findElement(lblMainErrorMessage);
    }

    private WebElement getLblConfirmPasswordError() {
        return Constant.WEBDRIVER.findElement(lblConfirmPasswordError);
    }

    private WebElement getLblTokenErrorMessage() {
        return Constant.WEBDRIVER.findElement(lblTokenErrorMessage);
    }

    // Methods for Forgot Password form
    public void requestResetInstructions(String email) {
        getTxtEmail().sendKeys(email);
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(getBtnSendInstructions()));
        getBtnSendInstructions().click();
    }

    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblErrorMessage));
        return getLblErrorMessage().getText().trim();
    }

    // Methods for Password Change Form
    public void resetPassword(String newPassword, String confirmPassword) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtNewPassword));

        getTxtNewPassword().sendKeys(newPassword);
        getTxtConfirmPassword().sendKeys(confirmPassword);

        wait.until(ExpectedConditions.elementToBeClickable(getBtnResetPassword()));
        getBtnResetPassword().click();
    }

    public void resetPassword(String newPassword, String confirmPassword, String resetToken) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtNewPassword));

        getTxtNewPassword().sendKeys(newPassword);
        getTxtConfirmPassword().sendKeys(confirmPassword);
        getTxtResetToken().sendKeys(resetToken);

        wait.until(ExpectedConditions.elementToBeClickable(getBtnResetPassword()));
        getBtnResetPassword().click();
    }

    public String getMainErrorMessage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblMainErrorMessage));
        return getLblMainErrorMessage().getText().trim();
    }

    public String getConfirmPasswordErrorMessage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblConfirmPasswordError));
        return getLblConfirmPasswordError().getText().trim();
    }

    public String getTokenErrorMessage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblTokenErrorMessage));
        return getLblTokenErrorMessage().getText().trim();
    }
}