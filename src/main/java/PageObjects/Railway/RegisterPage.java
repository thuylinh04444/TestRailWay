package PageObjects.Railway;

import Common.Constant.Constant;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class RegisterPage {
    // Bộ định vị
    private final By txtEmail = By.xpath("//input[@id='email']");
    private final By txtPassword = By.xpath("//input[@id='password']");
    private final By txtConfirmPassword = By.xpath("//input[@id='confirmPassword']");
    private final By txtPid = By.xpath("//input[@id='pid']");
    private final By btnRegister = By.xpath("//input[@type='submit' and @value='Register']");
    private final By lblSuccessMessage = By.xpath("//p[text()='Registration Confirmed! You can now log in to the site.']");
    private final By lblErrorMessage = By.cssSelector("p.message.error"); // Sửa bộ chọn
    private final By lblPasswordError=By.cssSelector("label.validation-error[for='password']");
    private final By lblPidError=By.cssSelector("label.validation-error[for ='pid']");

    // Các phần tử
    private WebElement getTxtEmail() {
        return Constant.WEBDRIVER.findElement(txtEmail);
    }

    private WebElement getTxtPassword() {
        return Constant.WEBDRIVER.findElement(txtPassword);
    }

    private WebElement getTxtConfirmPassword() {
        return Constant.WEBDRIVER.findElement(txtConfirmPassword);
    }

    private WebElement getTxtPid() {
        return Constant.WEBDRIVER.findElement(txtPid);
    }

    private WebElement getBtnRegister() {
        return Constant.WEBDRIVER.findElement(btnRegister);
    }

    private WebElement getLblSuccessMessage() {
        return Constant.WEBDRIVER.findElement(lblSuccessMessage);
    }

    private WebElement getLblErrorMessage() {
        return Constant.WEBDRIVER.findElement(lblErrorMessage);
    }

    private WebElement getLblPasswordError() {
        return Constant.WEBDRIVER.findElement(lblPasswordError);
    }

    private WebElement getLblPidError() {
        return Constant.WEBDRIVER.findElement(lblPidError);
    }

    // Các phương thức
    public void register(String email, String password, String confirmPassword, String pid) {
        getTxtEmail().sendKeys(email);
        getTxtPassword().sendKeys(password);
        getTxtConfirmPassword().sendKeys(confirmPassword);
        getTxtPid().sendKeys(pid);

        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(getBtnRegister()));

        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("arguments[0].click();", getBtnRegister());
    }

    public String getSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblSuccessMessage));
        return getLblSuccessMessage().getText().trim();
    }

    public String getErrorMessage() {
            WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOfElementLocated(lblErrorMessage));
            return getLblErrorMessage().getText().trim();

        }

    public String getErrorPasswordMessage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblPasswordError));
        return getLblPasswordError().getText().trim();

    }

    public String getPidErrorMessage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblPidError));
        return getLblPidError().getText().trim();

    }
    }
