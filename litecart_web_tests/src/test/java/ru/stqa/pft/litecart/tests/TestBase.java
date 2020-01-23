package ru.stqa.pft.litecart.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.litecart.app.AdminApplication;
import ru.stqa.pft.litecart.app.ClientApplication;

public class TestBase {
  ClientApplication clientApp;
  AdminApplication adminApp;
  private WebDriver driver;

  @BeforeMethod
  public void start() {
    driver = new FirefoxDriver();
    clientApp = new ClientApplication(driver);
    adminApp = new AdminApplication(driver);
  }

  @AfterMethod
  public void stop() {
    driver.quit();
  }
}
