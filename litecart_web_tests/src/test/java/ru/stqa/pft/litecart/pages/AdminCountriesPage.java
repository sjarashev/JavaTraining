package ru.stqa.pft.litecart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.stqa.pft.litecart.app.TestHelper;

import java.util.List;

public class AdminCountriesPage extends Page {

  public AdminCountriesPage(WebDriver driver) {
    super(driver);
  }

  private TestHelper testHelper = new TestHelper();

  @FindBy(xpath = "//tr//td[5]/a")
  private List<WebElement> countries;

  @FindBy(xpath = "//tr[@class='row']")
  public List<WebElement> countriesInRows;

  public void sortCountries() {
    List<String> listOfCountries = testHelper.createListOf(countries);
    testHelper.sort(listOfCountries);
  }


}
