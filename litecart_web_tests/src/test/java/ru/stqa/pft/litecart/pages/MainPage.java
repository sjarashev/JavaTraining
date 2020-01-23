package ru.stqa.pft.litecart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class MainPage extends Page {

  public MainPage(WebDriver driver) {
    super(driver);
  }

  @FindBy (xpath = "//div[@id='box-most-popular']//li[1]")
  private WebElement product;

  public void openProduct(){
    wait.until(visibilityOf(product)).click();
  }
}
