package Testcases;

import Common.Constant.Constant;
import PageObjects.Railway.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static Common.Constant.Constant.WEBDRIVER;
import static org.testng.Assert.assertTrue;

public class LoginTest {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        WEBDRIVER = new ChromeDriver();
        WEBDRIVER.manage().window().maximize();

        WEBDRIVER.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
        WEBDRIVER.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WEBDRIVER.get(Constant.RAILWAY_URL);
        homePage = new HomePage();
        loginPage = new LoginPage();
    }

    @Test(description = "đây la bài 1 đê ")
    public void TC01() {
        System.out.println("TC01 - Login with valid username and password");

        HomePage homePage = new HomePage();
        LoginPage loginPage = homePage.gotoLoginPage();

        loginPage.login("thuylinhH311004@gmail.com", "Nhonho311004@");

        WebDriverWait wait = new WebDriverWait(WEBDRIVER, Duration.ofSeconds(15));

        // Chờ phần tử "content" hiển thị
        WebElement contentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content")));

        String contentText = contentElement.getText();
        System.out.println("Nội dung sau khi login: " + contentText);

        // Kiểm tra chữ "Welcome"
        assertTrue(contentText.contains("Welcome"), "Không tìm thấy chữ 'Welcome' sau khi login");
    }

    @Test
    public void TC02_Sai_user(){
        System.out.println("User can't login with blank \"Username\" textbox");
        //step 1
        HomePage homePage=new HomePage();
        homePage.open();
        //step2
        homePage.gotoLoginPage();
        //step3
        LoginPage loginPage=new LoginPage();
        loginPage.login("", "12345677");
        //step4: guwir thoong baso
        String expectedMsg = "There was a problem with your login and/or errors exist in your form.";
        String actualMsg = loginPage.getErrorMessage();
        Assert.assertEquals(actualMsg,expectedMsg);

    }
    @Test
    public void TC_03_sai_password (){
        System.out.println("User cannot log into Railway with invalid password ");
         HomePage homePage=new HomePage();
         homePage.open();

         homePage.gotoLoginPage();

         LoginPage loginPage =new LoginPage();
         loginPage.login("thanhle@logigear.com", "");

         String expectedMsg="There was a problem with your login and/or errors exist in your form.";
         String actualMsg= loginPage.getErrorMessage();;
         Assert.assertEquals(actualMsg,expectedMsg);
    }


    @Test
    public void TC_04_Login_Bookticket() {
        System.out.println("TC_04 - Verify login page appears when clicking 'Book ticket' without logging in");

        // Mở trang chủ
        HomePage homePage = new HomePage();
        homePage.open();

        // Click vào "Book Ticket"
        homePage.clickBookTicketTab();

        // Step 3: Verify - Login page displays instead of Book ticket page
        LoginPage loginPage = new LoginPage();
        String currentUrl = WEBDRIVER.getCurrentUrl();
        String pageTitle = WEBDRIVER.getTitle();

        // Kiểm tra URL hoặc tiêu đề có chứa "Login"
        assertTrue(currentUrl.contains("Login") || pageTitle.contains("Login"),
                "Expected to be redirected to Login page, but got: " + currentUrl);


    }
    @Test
    public void TC_05(){
        System.out.println("System shows message when user enters wrong password several times");
        HomePage homePage= new HomePage();
        homePage.open();
        homePage.clickLogin();
         String valiUsername= "thuylinh31004@gmail.com";
         String emptyPassword="";

         for (int i = 1 ; i<=4; i++){
             loginPage.login(valiUsername,emptyPassword);
             try {
                 Thread.sleep(1000);

             } catch (InterruptedException e){
                 e.printStackTrace();

             }

         }
         String expectedError="You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
         String actualError=loginPage.getErrorMessage();
         Assert.assertEquals(actualError,expectedError,"Error message after 4 failed login attempts is incorrect.");
    }

    @Test
    public void TC_06 (){
        //buoc 1: truy cập trang QA Railway website
        HomePage homepage= new HomePage();
        homepage.open();
        // buoc 2: Nhan vao tab Login

        LoginPage loginPage = homePage.gotoLoginPage();
        // buoc 3: đăng nhap voi tài khoản hợp lệ
        loginPage.login("thuylinhH311004@gmail.com","Nhonho311004@");
        // chờ cho trang tải hoàn tất và kiểm tra các tab hin thị
        WebDriverWait wait= new WebDriverWait(WEBDRIVER,Duration.ofSeconds(15));
        // kieerm tra su hien thi cua tab hien thi
        By tabMyTicket= By.xpath("//div[@id='menu']//a[@href='/Page/ManageTicket.cshtml']");
        By tabChangePassword= By.xpath("//div[@id='menu']//a[@href='/Account/ChangePassword.cshtml']");
        By tablogout = By.xpath("//div[@id='menu']//a[@href='/Account/Logout']");



        WebElement myTicketTab=wait.until(ExpectedConditions.visibilityOfElementLocated(tabMyTicket));
        WebElement changePasswordTab = wait.until(ExpectedConditions.visibilityOfElementLocated(tabChangePassword));
        WebElement logoutTab=wait.until(ExpectedConditions.visibilityOfElementLocated(tablogout));


        // kiểm tra các tab có hiển thị không
        assertTrue(myTicketTab.isDisplayed(),"Tab'My ticket' khong the hien thi sau khi dang nhap" );
        assertTrue(changePasswordTab.isDisplayed(),"Tab 'Change password' khong hien thi");
        assertTrue(logoutTab.isDisplayed(),"Tab'Logout' khoong hieern thi sau khi dang nhap thanh cong ");

        // Step 4: Nhấp vào tab "My ticket" và kiểm tra URL
        myTicketTab.click();
        wait.until(ExpectedConditions.urlContains("/Page/ManageTicket.cshtml"));
        String currentUrl = WEBDRIVER.getCurrentUrl();
        assertTrue(currentUrl.contains("/Page/ManageTicket.cshtml"),
                "Không chuyển đến trang 'My ticket'. URL hiện tại: " + currentUrl);

        // Quay lại trang chính để tiếp tục kiểm tra
        homePage.open();

        // Step 5: Nhấp vào tab "Change password" và kiểm tra URL
        changePasswordTab = wait.until(ExpectedConditions.elementToBeClickable(tabChangePassword));
        changePasswordTab.click();
        wait.until(ExpectedConditions.urlContains("/Account/ChangePassword.cshtml"));
        currentUrl = WEBDRIVER.getCurrentUrl();
        assertTrue(currentUrl.contains("/Account/ChangePassword.cshtml"),
                "Không chuyển đến trang 'Change password'. URL hiện tại: " + currentUrl);
    }
    @Test
    public void TC07() {
        System.out.println("TC07 - User can create new account");

        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Click on "Register" tab
        RegisterPage registerPage = homePage.gotoRegisterPage();

        // Step 3: Enter valid information into all fields
        // Sử dụng email ngẫu nhiên để tránh trùng lặp
        String randomEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";
        String password = "Test@1234";
        String confirmPassword = password;
        String pid = "123456789";

        registerPage.register(randomEmail, password, confirmPassword, pid);

        // Step 4: Verify success message
        //String expectedMessage = "Registration Confirmed! You can now log in to the site.";
        String expectedMessage = "Thank you for registering your account";
        String actualMessage = registerPage.getSuccessMessage();
        Assert.assertEquals(actualMessage, expectedMessage,
                "Success message is incorrect. Expected: " + expectedMessage + ", but got: " + actualMessage);
    }

    @Test
    public void TC_08(){
        HomePage homePage= new HomePage();
        homePage.open();
        // Bước 2: Nhấn tab "Login"

        LoginPage loginPage= homePage.gotoLoginPage();
        // Bước 3: Nhập username và password của tài khoản chưa kích hoạt
        String email="linh@gmail.com";
        String password="12345";


        loginPage.login(email,password);
        // Bước 4: Kiểm tra thông báo lỗi
        String expetedMgs="Invalid username or password. Please try again.";
        String actualMgs=loginPage.getErrorMessage();
        Assert.assertEquals(expetedMgs,actualMgs);

    }
    @Test
    public void TC_09() {
        WebDriver driver = Constant.WEBDRIVER;

        // Mở trang Home
        HomePage homePage = new HomePage();
        homePage.open();

        // Điều hướng đến trang Login
        LoginPage loginPage = homePage.gotoLoginPage();

        // Đăng nhập
        String email = "thuylinhH311004@gmail.com";
        String password = "haha12345";
        loginPage.login(email, password);
        ChangePasswordPage changePasswordPage= homePage.gotoChangePassword();
        // Gọi class ChangePasswordPage và thực hiện đổi mật khẩu
//        String currentPassword = "abc123456";
//        String newPassword = "haha12345";
//        String confirmPassword = "haha12345";
//        changePasswordPage.changePassword(currentPassword, newPassword, confirmPassword);

        String currentPassword = "haha12345";
        String newPassword = "abc123456";
        String confirmPassword = "abc123456";
        changePasswordPage.changePassword(currentPassword, newPassword, confirmPassword);

        // Kiểm tra thông báo thành công
        String expectedMsg = "Your password has been updated!";
        String actualMsg = changePasswordPage.getSuccessMessage();
        Assert.assertEquals(actualMsg, expectedMsg);
    }

    @Test
    public void TC_10(){
        HomePage homePage= new HomePage();
        homePage.open();
        RegisterPage registerPage=homePage.gotoRegisterPage();
        String email="tlinh12345@gmail.com";
        String password="123456789";
        String confirmPassword="12345678";
        String pid="1234567889";

        registerPage.register(email, password, confirmPassword, pid);

        String expectedMgs="There're errors in the form. Please correct the errors and try again.";
        String actualMgs=registerPage.getErrorMessage();
        Assert.assertEquals(expectedMgs,actualMgs);

    }
@Test
public void TC_11(){
        HomePage homePage= new HomePage();
        homePage.open();

        RegisterPage registerPage= homePage.gotoRegisterPage();

    String email="tlinh12345@gmail.com";
    String password="";
    String confirmPassword="";
    String pid="";
    registerPage.register(email,password,confirmPassword,pid);

    // kiem tra thong bao loi
    String expectedMgs="There're errors in the form. Please correct the errors and try again.";
    String actualMgs=registerPage.getErrorMessage();
    Assert.assertEquals(expectedMgs,actualMgs);

    //kiem tra thong bao loi cua passsword bo trong
    String expectedpasswordMgs="Invalid password length";
    String actualpasswordMgs =registerPage.getErrorPasswordMessage();
    Assert.assertEquals(expectedpasswordMgs, actualpasswordMgs);

    //kiem tra thong bao loi tai pid
    String expectedpidMgs="Invalid ID length";
    String actualpidMgs =registerPage.getPidErrorMessage();
    Assert.assertEquals(expectedpidMgs, actualpidMgs);
    }
    @Test
    public void TC_12() {
        // Step 1: Navigate to Login Page
        LoginPage loginPage = new LoginPage();
        loginPage.open();

        // Step 2: Go to Forgot Password Page
        ForgotPasswordPage forgotPasswordPage = loginPage.gotoForgotPasswordPage();
        String email = "tlinh12345@gmail.com";
        forgotPasswordPage.requestResetInstructions(email);


        String newPassword = "newPassword123";
        String confirmPassword = "newPassword123";
        String resetToken = "";

        forgotPasswordPage.resetPassword(newPassword, confirmPassword, resetToken);

        String expectedMainMsg = "The password reset token is incorrect or may be expired. Visit the forgot password page to generate a new one.";
        String actualMainMsg = forgotPasswordPage.getMainErrorMessage();
        Assert.assertEquals(actualMainMsg, expectedMainMsg, "Main error message does not match");

        String expectedTokenMsg = "The password reset token is invalid.";
        String actualTokenMsg = forgotPasswordPage.getTokenErrorMessage();
        Assert.assertEquals(actualTokenMsg, expectedTokenMsg, "Token error message does not match");
    }

    @Test
    public void TC_13() {
        System.out.println("TC13 - Errors display if password and confirm password don't match when resetting password");

        LoginPage loginPage = new LoginPage();
        loginPage.open();

        ForgotPasswordPage forgotPasswordPage = loginPage.gotoForgotPasswordPage();

        String email = "tlinh12345@gmail.com";
        forgotPasswordPage.requestResetInstructions(email);


        String newPassword = "newPassword123";
        String confirmPassword = "differentPassword123";
        forgotPasswordPage.resetPassword(newPassword, confirmPassword);

        String expectedMainMsg = "Could not reset password. Please correct the errors and try again.";
        String actualMainMsg = forgotPasswordPage.getMainErrorMessage();
        Assert.assertEquals(actualMainMsg, expectedMainMsg, "Main error message does not match");

        String expectedConfirmPasswordMsg = "The password confirmation did not match the new password.";
        String actualConfirmPasswordMsg = forgotPasswordPage.getConfirmPasswordErrorMessage();
        Assert.assertEquals(actualConfirmPasswordMsg, expectedConfirmPasswordMsg, "Confirm password error message does not match");
    }

    @Test
    public void TC_14() {
        System.out.println("TC14 - User can book 1 ticket at a time");

        try {
            HomePage homePage = new HomePage();
            homePage.open(); // Mở trang chủ của website

            LoginPage loginPage = homePage.gotoLoginPage(); // Chuyển đến trang đăng nhập
            loginPage.login("hihi311004@gmail.com", "123456789"); // Đăng nhập với email và mật khẩu hợp lệ

            BookTicketPage bookTicketPage = loginPage.gotoBookTicketPage(); // Nhấn vào tab "Book ticket"

            Select departDateSelect = new Select(Constant.WEBDRIVER.findElement(By.xpath("//select[@name='Date']"))); // Lấy dropdown chọn ngày
            List<WebElement> dateOptions = departDateSelect.getOptions(); // Lấy danh sách các ngày có sẵn
            if (dateOptions.isEmpty()) {
                Assert.fail("Không có ngày nào trong dropdown ngày khởi hành"); // Báo lỗi nếu dropdown rỗng
            }
            String selectedDate = dateOptions.get(0).getText(); // Lấy ngày đầu tiên trong danh sách
            departDateSelect.selectByVisibleText(selectedDate); // Chọn ngày đó
            System.out.println("Đã chọn ngày khởi hành: " + selectedDate);


            String departFrom = "Sài Gòn";
            String arriveAt = "Nha Trang";
            String seatType = "Soft bed with air conditioner";
            String ticketAmount = "1";
            System.out.println("Đang đặt vé với Ngày khởi hành: " + selectedDate + ", Ga đi: " + departFrom + ", Ga đến: " + arriveAt);

            // Gọi hàm đặt vé từ BookTicketPage với các thông tin đã chọn
            bookTicketPage.bookTicket(selectedDate, departFrom, arriveAt, seatType, ticketAmount);

            // Bước 8: Kiểm tra kết quả đặt vé
            WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(20)); // Tạo đối tượng chờ tối đa 20 giây
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1[align='center']"))); // Chờ thông báo thành công hiển thị

            String expectedSuccessMsg = "Ticket booked successfully!"; // Thông báo thành công mong đợi
            String actualSuccessMsg = bookTicketPage.getSuccessMessage(); // Lấy thông báo thực tế
            Assert.assertEquals(actualSuccessMsg, expectedSuccessMsg, "Thông báo thành công không khớp"); // So sánh thông báo

            // Kiểm tra thông tin vé hiển thị trong bảng
            WebElement ticketTable = Constant.WEBDRIVER.findElement(By.xpath("//table[@class='MyTable WideTable']")); // Lấy bảng thông tin vé
            String ticketInfoText = ticketTable.getText(); // Lấy toàn bộ nội dung bảng
            Assert.assertTrue(ticketInfoText.contains(departFrom), "Thông tin vé không chứa ga đi: " + departFrom); // Kiểm tra ga đi
            Assert.assertTrue(ticketInfoText.contains(arriveAt), "Thông tin vé không chứa ga đến: " + arriveAt); // Kiểm tra ga đến
            Assert.assertTrue(ticketInfoText.contains(seatType), "Thông tin vé không chứa loại ghế: " + seatType); // Kiểm tra loại ghế
            Assert.assertTrue(ticketInfoText.contains(ticketAmount), "Thông tin vé không chứa số lượng vé: " + ticketAmount); // Kiểm tra số lượng vé
            Assert.assertTrue(ticketInfoText.contains(selectedDate), "Thông tin vé không chứa ngày khởi hành: " + selectedDate); // Kiểm tra ngày khởi hành

        } catch (Exception e) {
            Assert.fail("TC14 thất bại: " + e.getMessage(), e); // Báo lỗi nếu có ngoại lệ xảy ra
        }
    }

    @Test
    public void TC_15() {
        System.out.println("TC15 - User can open 'Book ticket' page by clicking on 'Book ticket' link in 'Train timetable' page");

        try {
            System.out.println("Đang truy cập website QA Railway...");
            HomePage homePage = new HomePage();
            homePage.open();

            LoginPage loginPage = homePage.gotoLoginPage(); // Chuyển đến trang đăng nhập
            loginPage.login("hihi311004@gmail.com", "123456789"); // Đăng nhập với tài khoản hợp lệ

            TimetablePage timetablePage = homePage.gotoTimetablePage(); // Chuyển đến trang Timetable

            BookTicketPage bookTicketPage = timetablePage.clickBookTicketLink(); // Nhấp link và chuyển đến trang Book ticket

            WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(20));

            // Kiểm tra giá trị "Depart from" là "Huế"
            WebElement departFromElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@name='DepartStation']")));
            String selectedDepartFrom = new Select(departFromElement).getFirstSelectedOption().getText();
            Assert.assertEquals(selectedDepartFrom, "Huế", "Giá trị 'Depart from' không đúng, mong đợi: Huế, thực tế: " + selectedDepartFrom);

            // Kiểm tra giá trị "Arrive at" là "Sài Gòn"
            WebElement arriveAtElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@name='ArriveStation']")));
            String selectedArriveAt = new Select(arriveAtElement).getFirstSelectedOption().getText();
            Assert.assertEquals(selectedArriveAt, "Sài Gòn", "Giá trị 'Arrive at' không đúng, mong đợi: Sài Gòn, thực tế: " + selectedArriveAt);

            System.out.println("Trang 'Book ticket' được tải với giá trị đúng: Depart from = " + selectedDepartFrom + ", Arrive at = " + selectedArriveAt);
        } catch (Exception e) {
            Assert.fail("TC15 thất bại: " + e.getMessage(), e);
        }
    }

    @AfterMethod
    public void tearDown() {
        if (WEBDRIVER != null) {
            WEBDRIVER.quit();
        }
    }
}
