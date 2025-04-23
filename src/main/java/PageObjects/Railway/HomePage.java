package PageObjects.Railway;

import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends GeneralPage {

    // Khai báo và khởi tạo WebDriverWait
    private WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

    // Khai báo phần tử Book Ticket Tab
    private By bookTicketTab = By.xpath("//a[@href='/Page/BookTicketPage.cshtml']");
    // Khai báo phần ử tickLogin
    private By tickLogin = By.xpath("//a[@href='/Account/Login.cshtml']");
    private final By tabTimetable = By.cssSelector("a[href='TrainTimeListPage.cshtml'] > span");

    public HomePage open() {
        Constant.WEBDRIVER.navigate().to(Constant.RAILWAY_URL);
        return this;
    }

    public void clickBookTicketTab() {
        // Đợi cho phần tử có thể click được
        WebElement bookTicketBtn = wait.until(ExpectedConditions.elementToBeClickable(bookTicketTab));

        // Sử dụng JavascriptExecutor để click vào phần tử
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", bookTicketBtn);
    }
    public void clickLogin(){
        WebElement loginBtn =wait.until(ExpectedConditions.elementToBeClickable(tickLogin));
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", loginBtn);
    }
    public RegisterPage gotoRegisterPage() {
        WebElement registerBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='menu']//a[@href='/Account/Register.cshtml']")));
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", registerBtn);
        return new RegisterPage();
    }
    public ChangePasswordPage gotoChangePassword() {
        WebElement changePasswordBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@id='menu']//a[@href='/Account/ChangePassword.cshtml']")
                )
        );
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", changePasswordBtn);
        return new ChangePasswordPage(); // Sửa ở đây: phải có từ khóa new
    }
    public BookTicketPage gotoBookTicketPage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement bookTicketTab = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/Page/BookTicketPage.cshtml']"))
        );
        bookTicketTab.click();
        return new BookTicketPage();
    }
    public TimetablePage gotoTimetablePage() {
        // Nhấp vào tab "Timetable"
        System.out.println("Đang nhấp vào tab 'Timetable'...");
        Constant.WEBDRIVER.findElement(tabTimetable).click();
        System.out.println("Đã nhấp vào tab 'Timetable' thành công");
        return new TimetablePage();
    }




}
