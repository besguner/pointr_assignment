package io.cucumber.skeleton;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v116.network.Network;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class StepDefinitions {

  List<TestData> tdList;
  WebDriver driver;
  String browser;
  String folderPath;

  public void initDriver(String browser) {
    this.browser = browser;
    if (browser.equals("chrome")) initChromDriver(); else initFirefoxDriver();
  }

  @Given("Login page is open on {string}")
  public void openLoginPage(String browser) {
    initDriver(browser);
    readFromConfigFile();
    driver.get("https://www.n11.com/giris-yap");
  }

  @When("User enters correct credentials and clicks login")
  public void login() {
    //System.out.println(tdList.get(0));
    driver.findElement(By.id("email")).sendKeys(tdList.get(0).username);
    driver.findElement(By.id("password")).sendKeys(tdList.get(0).password);
    WebElement loginBtn = driver.findElement(By.id("loginButton"));
    Actions actions = new Actions(driver);
    actions.moveToElement(loginBtn);
    actions.perform();
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(0,250)", "");
    loginBtn.click();
  }

  @When("User enters wrong credentials and clicks login")
  public void failedLogin() {
    //System.out.println(tdList.get(1));
    driver.findElement(By.id("email")).sendKeys(tdList.get(1).username);
    driver.findElement(By.id("password")).sendKeys(tdList.get(1).password);
    WebElement loginBtn = driver.findElement(By.id("loginButton"));
    Actions actions = new Actions(driver);
    actions.moveToElement(loginBtn);
    actions.perform();
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(0,250)", "");
    loginBtn.click();
  }

  @Then("Login should be successful")
  public void login_should_be_s() {
    List<WebElement> user = driver.findElements(By.className("user"));
    Assert.assertEquals(1, user.size());
    driver.close();
  }

  @Then("Login should not be successful")
  public void login_should_be_u() {
    List<WebElement> user = driver.findElements(By.className("errorText"));
    Assert.assertEquals(0, user.size());
    driver.close();
  }

  @Given("Homepage is open on {string}")
  public void homepage_is_open(String browser) {
    initDriver(browser);
    driver.get("https://www.n11.com/giris-yap");
    readFromConfigFile();
  }

  @When("user enters valid keyword")
  public void user_enters_valid_keyword() {
    driver.findElement(By.id("searchData")).sendKeys(tdList.get(0).searchTerm);
    driver.findElement(By.className("searchBtn")).click();
  }

  @Then("Results should be present")
  public void results_should_be_present() {
    List<WebElement> resultText = driver.findElements(
      By.className("resultText")
    );
    Assert.assertTrue(resultText.size() > 0);
    logResults(resultText.get(0).getText(), tdList.get(0).searchTerm);
    takeSS();
    driver.close();
  }

  @When("user enters invalid keyword")
  public void user_enters_invalid_keyword() {
    driver.findElement(By.id("searchData")).sendKeys(tdList.get(1).searchTerm);
    driver.findElement(By.className("searchBtn")).click();
  }

  @Then("Results should not be present")
  public void results_should_not_be_present() {
    List<WebElement> resultText = driver.findElements(
      By.id("searchResultNotFound")
    );
    Assert.assertTrue(resultText.size() > 0);
    logResults("Sonuç bulunamadı", tdList.get(1).searchTerm);
    takeSS();
    driver.close();
  }

  public void logResults(String result, String searchTerm) {
    folderPath =
      browser +
      java.time.LocalDate.now() +
      "_" +
      java.time.LocalTime.now().getHour() +
      "-" +
      java.time.LocalTime.now().getMinute() +
      "-" +
      java.time.LocalTime.now().getSecond();
    new File("target\\artifacts\\" + folderPath).mkdirs();
    try {
      FileWriter myWriter = new FileWriter(
        "target\\artifacts\\" + folderPath + "\\results.txt"
      );
      myWriter.write("Search term:" + searchTerm + ", Result:" + result + "\n");
      myWriter.flush();
      myWriter.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void takeSS() {
    TakesScreenshot scrShot = ((TakesScreenshot) driver);
    File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
    String fileName =
      "ss_" +
      java.time.LocalDate.now() +
      "_" +
      java.time.LocalTime.now().getHour() +
      "-" +
      java.time.LocalTime.now().getMinute() +
      "-" +
      java.time.LocalTime.now().getSecond();

    File DestFile = new File(
      "target\\artifacts\\" + folderPath + "\\" + fileName + ".jpg"
    );
    try {
      FileUtils.copyFile(SrcFile, DestFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public WebDriver initChromDriver() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--disable-popup-blocking");
    options.addArguments("--disable-notifications");
    options.addArguments("--incognito");
    driver = new ChromeDriver(options);
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    return driver;
  }

  public WebDriver initFirefoxDriver() {
    FirefoxOptions options = new FirefoxOptions();
    options.addArguments("-private");
    driver = new FirefoxDriver(options);
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    return driver;
  }

  public void readFromConfigFile() {
    tdList = new ArrayList<TestData>();
    //read from the config file
    File file = new File("src\\test\\config.txt");
    Scanner sc = null;
    try {
      sc = new Scanner(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    String username = "", password = "", searchTerm = "";
    while (sc != null && sc.hasNextLine()) {
      String nl = sc.nextLine();
      if (nl.isEmpty()) {
        tdList.add(new TestData(username, password, searchTerm));
      } else {
        if (nl.substring(0, 9).equals("Username:")) username =
          username = nl.substring(10, nl.length());
        if (nl.substring(0, 9).equals("Password:")) password =
          password = nl.substring(10, nl.length());
        if (nl.substring(0, 11).equals("SearchTerm:")) searchTerm =
          searchTerm = nl.substring(12, nl.length());
      }
    }
  }
}
