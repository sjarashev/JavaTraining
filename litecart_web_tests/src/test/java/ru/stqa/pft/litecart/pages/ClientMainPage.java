package ru.stqa.pft.litecart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class ClientMainPage extends Page {

  public ClientMainPage(WebDriver driver) {
    super(driver);
  }

  @FindBy (xpath = "//div[@id='box-most-popular']//li[1]")
  private WebElement product;

  public void openProduct(){
    wait.until(visibilityOf(product)).click();
  }
}
