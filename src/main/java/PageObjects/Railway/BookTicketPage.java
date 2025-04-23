package PageObjects.Railway;

import Common.Constant.Constant;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BookTicketPage {
    // Locators
    private final By selectDepartDate = By.xpath("//select[@name='Date']");
    private final By selectDepartFrom = By.xpath("//select[@name='DepartStation']");
    private final By selectArriveAt = By.xpath("//select[@name='ArriveStation']");
    private final By selectSeatType = By.xpath("//select[@name='SeatType']");
    private final By selectTicketAmount = By.xpath("//select[@name='TicketAmount']");
    private final By btnBookTicket = By.xpath("//input[@type='submit' and @value='Book ticket']");
    private final By lblSuccessMessage = By.cssSelector("h1[align='center']");
    private final By tableTicketInfo = By.xpath("//table[@class='MyTable WideTable']");

    // Elements
    private Select getSelectDepartDate() {
        return new Select(Constant.WEBDRIVER.findElement(selectDepartDate));
    }

    private Select getSelectDepartFrom() {
        return new Select(Constant.WEBDRIVER.findElement(selectDepartFrom));
    }

    private Select getSelectArriveAt() {
        return new Select(Constant.WEBDRIVER.findElement(selectArriveAt));
    }

    private Select getSelectSeatType() {
        return new Select(Constant.WEBDRIVER.findElement(selectSeatType));
    }

    private Select getSelectTicketAmount() {
        return new Select(Constant.WEBDRIVER.findElement(selectTicketAmount));
    }

    private WebElement getBtnBookTicket() {
        return Constant.WEBDRIVER.findElement(btnBookTicket);
    }

    private WebElement getLblSuccessMessage() {
        return Constant.WEBDRIVER.findElement(lblSuccessMessage);
    }

    private WebElement getTableTicketInfo() {
        return Constant.WEBDRIVER.findElement(tableTicketInfo);
    }

    // Methods
    public void bookTicket(String departDate, String departFrom, String arriveAt, String seatType, String ticketAmount) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(20));
        try {
            // Chọn Ngày khởi hành
            System.out.println("Đang chọn ngày khởi hành: " + departDate);
            wait.until(ExpectedConditions.visibilityOfElementLocated(selectDepartDate)); // Chờ dropdown ngày khởi hành hiển thị
            Select departDateSelect = getSelectDepartDate(); // Lấy dropdown ngày khởi hành
            List<WebElement> dateOptions = departDateSelect.getOptions(); // Lấy danh sách các ngày có sẵn
            if (!dateOptions.isEmpty()) {
                String selectedDate = dateOptions.get(0).getText(); // Lấy ngày đầu tiên trong danh sách
                if (!departDate.equals("fallback")) {
                    try {
                        departDateSelect.selectByVisibleText(departDate); // Chọn ngày khởi hành
                        selectedDate = departDate;
                    } catch (NoSuchElementException e) {
                        System.out.println("Ngày " + departDate + " không tìm thấy, chọn ngày đầu tiên có sẵn...");
                        selectedDate = dateOptions.get(0).getText();
                        departDateSelect.selectByVisibleText(selectedDate); // Chọn ngày mặc định nếu không tìm thấy
                    }
                } else {
                    selectedDate = dateOptions.get(0).getText();
                    departDateSelect.selectByVisibleText(selectedDate); // Chọn ngày mặc định
                }
                System.out.println("Đã chọn ngày khởi hành: " + selectedDate);
            } else {
                throw new RuntimeException("Không có ngày nào trong dropdown ngày khởi hành");
            }

            // Chọn Ga đi
            System.out.println("Đang chọn ga đi: " + departFrom);
            wait.until(ExpectedConditions.visibilityOfElementLocated(selectDepartFrom)); // Chờ dropdown ga đi hiển thị
            Select departFromSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(selectDepartFrom))); // Lấy dropdown ga đi và đảm bảo có thể tương tác
            int maxRetries = 3; // Số lần thử lại tối đa
            for (int i = 0; i < maxRetries; i++) {
                try {
                    departFromSelect.selectByVisibleText(departFrom); // Chọn ga đi "Sài Gòn"
                    break; // Thoát vòng lặp nếu chọn thành công
                } catch (StaleElementReferenceException e) {
                    System.out.println("Lỗi StaleElementReferenceException, thử lại lần " + (i + 1));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(selectDepartFrom)); // Chờ lại dropdown ga đi
                    departFromSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(selectDepartFrom))); // Lấy lại dropdown
                    if (i == maxRetries - 1) {
                        throw new RuntimeException("Không thể chọn ga đi '" + departFrom + "' sau " + maxRetries + " lần thử");
                    }
                } catch (NoSuchElementException e) {
                    throw new RuntimeException("Ga đi '" + departFrom + "' không tìm thấy trong dropdown");
                }
            }

            // Chọn Ga đến
            System.out.println("Đang chọn ga đến: " + arriveAt);
            wait.until(ExpectedConditions.visibilityOfElementLocated(selectArriveAt)); // Chờ dropdown ga đến hiển thị
            Select arriveAtSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(selectArriveAt))); // Lấy dropdown ga đến
            for (int i = 0; i < maxRetries; i++) {
                try {
                    arriveAtSelect.selectByVisibleText(arriveAt); // Chọn ga đến "Nha Trang"
                    break;
                } catch (StaleElementReferenceException e) {
                    System.out.println("Lỗi StaleElementReferenceException, thử lại lần " + (i + 1));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(selectArriveAt));
                    arriveAtSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(selectArriveAt)));
                    if (i == maxRetries - 1) {
                        throw new RuntimeException("Không thể chọn ga đến '" + arriveAt + "' sau " + maxRetries + " lần thử");
                    }
                } catch (NoSuchElementException e) {
                    throw new RuntimeException("Ga đến '" + arriveAt + "' không tìm thấy trong dropdown");
                }
            }

            // Chọn Loại ghế
            System.out.println("Đang chọn loại ghế: " + seatType);
            wait.until(ExpectedConditions.visibilityOfElementLocated(selectSeatType)); // Chờ dropdown loại ghế hiển thị
            Select seatTypeSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(selectSeatType))); // Lấy dropdown loại ghế
            for (int i = 0; i < maxRetries; i++) {
                try {
                    seatTypeSelect.selectByVisibleText(seatType); // Chọn loại ghế "Soft bed with air conditioner"
                    break;
                } catch (StaleElementReferenceException e) {
                    System.out.println("Lỗi StaleElementReferenceException, thử lại lần " + (i + 1));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(selectSeatType));
                    seatTypeSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(selectSeatType)));
                    if (i == maxRetries - 1) {
                        throw new RuntimeException("Không thể chọn loại ghế '" + seatType + "' sau " + maxRetries + " lần thử");
                    }
                } catch (NoSuchElementException e) {
                    throw new RuntimeException("Loại ghế '" + seatType + "' không tìm thấy trong dropdown");
                }
            }

            // Chọn Số lượng vé
            System.out.println("Đang chọn số lượng vé: " + ticketAmount);
            wait.until(ExpectedConditions.visibilityOfElementLocated(selectTicketAmount)); // Chờ dropdown số lượng vé hiển thị
            Select ticketAmountSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(selectTicketAmount))); // Lấy dropdown số lượng vé
            for (int i = 0; i < maxRetries; i++) {
                try {
                    ticketAmountSelect.selectByVisibleText(ticketAmount); // Chọn số lượng vé "1"
                    break;
                } catch (StaleElementReferenceException e) {
                    System.out.println("Lỗi StaleElementReferenceException, thử lại lần " + (i + 1));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(selectTicketAmount));
                    ticketAmountSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(selectTicketAmount)));
                    if (i == maxRetries - 1) {
                        throw new RuntimeException("Không thể chọn số lượng vé '" + ticketAmount + "' sau " + maxRetries + " lần thử");
                    }
                }
            }

            // Nhấn nút Đặt vé
            System.out.println("Đang nhấn nút Đặt vé...");
            WebElement bookTicketButton = wait.until(ExpectedConditions.elementToBeClickable(btnBookTicket)); // Chờ nút đặt vé có thể nhấn
            if (!bookTicketButton.isEnabled()) {
                throw new RuntimeException("Nút Đặt vé đang bị vô hiệu hóa");
            }
            bookTicketButton.click(); // Nhấn nút đặt vé
            System.out.println("Đã nhấn nút Đặt vé thành công");
        } catch (Exception e) {
            throw new RuntimeException("Không thể đặt vé: " + e.getMessage(), e); // Báo lỗi nếu có ngoại lệ
        }
    }
    public String getSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblSuccessMessage));
        return getLblSuccessMessage().getText().trim();
    }

    public String getTicketInfo() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableTicketInfo));
        return getTableTicketInfo().getText().trim();
    }
}