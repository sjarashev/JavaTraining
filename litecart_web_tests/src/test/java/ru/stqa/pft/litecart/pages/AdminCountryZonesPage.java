package ru.stqa.pft.litecart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.stqa.pft.litecart.app.TestHelper;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class AdminCountryZonesPage extends Page {

  private TestHelper testHelper = new TestHelper();

  public AdminCountryZonesPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(xpath = "//table[@class='dataTable']//tr/td[3]")
  private List<WebElement> zones;

  @FindBy(xpath = "//li[3]//a[1]")
  private WebElement countriesPage;

  public void gotoCountriesPage() {
    wait.until(visibilityOf(countriesPage)).click();
  }

  public void sortZones() {
    List<String> listOfZones = testHelper.createListOf(zones);
    testHelper.sort(listOfZones);
  }
}
