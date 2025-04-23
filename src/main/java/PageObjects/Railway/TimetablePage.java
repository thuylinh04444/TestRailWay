package PageObjects.Railway;

import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TimetablePage {
    // Locators
    private final By linkBookTicket = By.xpath("//td[text()='Huế']/following-sibling::td[text()='Sài Gòn']/following-sibling::td/a[text()='book ticket']");

    // Elements
    private WebElement getLinkBookTicket() {
        return Constant.WEBDRIVER.findElement(linkBookTicket);
    }

    // Methods
    public BookTicketPage clickBookTicketLink() {
        // Chờ link "book ticket" sẵn sàng
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(20));
        WebElement bookTicketLink = wait.until(ExpectedConditions.elementToBeClickable(linkBookTicket));

        // Cuộn trang đến link "book ticket"
        System.out.println("Đang cuộn trang đến link 'book ticket'...");
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView({block: 'center'});", bookTicketLink);

        // Đợi một chút để đảm bảo cuộn hoàn tất
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Nhấp vào link "book ticket"
        System.out.println("Đang nhấp vào link 'book ticket' của tuyến Huế - Sài Gòn...");
        bookTicketLink.click();
        System.out.println("Đã nhấp vào link 'book ticket' thành công");

        // Trả về trang BookTicketPage
        return new BookTicketPage();
    }
}