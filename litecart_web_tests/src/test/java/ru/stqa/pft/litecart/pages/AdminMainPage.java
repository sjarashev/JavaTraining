package ru.stqa.pft.litecart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class AdminMainPage extends Page {
  public AdminMainPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(xpath = "//li[3]//a[1]")
  private WebElement countriesPage;

  public void gotoCountriesPage() {
    wait.until(visibilityOf(countriesPage)).click();
  }
}
