package ru.stqa.pft.litecart.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.stqa.pft.litecart.pages.AdminCountriesPage;
import ru.stqa.pft.litecart.pages.AdminCountryZonesPage;
import ru.stqa.pft.litecart.pages.AdminMainPage;

import java.beans.Visibility;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class AdminApplication{

  private WebDriver driver;
  private AdminMainPage adminMainPage;
  private AdminCountriesPage adminCountriesPage;
  private AdminCountryZonesPage adminCountryZonesPage;

  public AdminApplication(WebDriver driver) {
    this.driver=driver;
  }

  public void openWebPage() throws InterruptedException {
    driver.manage().window().maximize();
    driver.get("http://localhost/litecart/admin/login.php");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();
    Thread.sleep(2000);
    adminMainPage = new AdminMainPage(driver);
    adminCountriesPage = new AdminCountriesPage(driver);
    adminCountryZonesPage = new AdminCountryZonesPage(driver);
  }

  public void gotoCountriesPage() {
    adminMainPage.gotoCountriesPage();
  }

  public void sortCountries() {
    adminCountriesPage.sortCountries();
  }

  public void sortCountryZones() {
    int numberOfCountries = adminCountriesPage.countriesInRows.size();
    for (int i = 0; i < numberOfCountries; i++) {
      List<WebElement> we = adminCountriesPage.countriesInRows;
      WebElement w = we.get(i);
      if (Integer.parseInt(w.findElement(By.xpath("./td[6]")).getText().trim()) > 0) {
        w.findElement(By.xpath("./td[5]/a[@href]")).click();
        adminCountryZonesPage.sortZones();
        adminCountryZonesPage.gotoCountriesPage();
      }
    }
  }
}
