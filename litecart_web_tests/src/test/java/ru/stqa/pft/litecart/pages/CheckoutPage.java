package ru.stqa.pft.litecart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import java.util.List;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class CheckoutPage extends Page {

  public CheckoutPage(WebDriver driver) {
    super(driver);
  }

  public void deleteProductFromTheCart(int number) {
    WebElement table = driver.findElement(By.xpath("//div[@id='checkout-summary-wrapper']"));
    List<WebElement> products = table.findElements(By.xpath(".//tr/td[@class='item']"));
    for (int i = 1; i < number; i++) {
      List<WebElement> removeitem = driver.findElements(By.xpath("//li//button[contains(text(), 'Remove')]"));
      wait.until(visibilityOf(removeitem.get(0))).click();
      wait.until(stalenessOf(products.get(0)));
      products = table.findElements(By.xpath(".//tr/td[@class='item']"));
      Assert.assertEquals(products.size(), number - i);
    }
  }
}
