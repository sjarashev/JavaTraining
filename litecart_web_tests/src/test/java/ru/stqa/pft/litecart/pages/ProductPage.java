package ru.stqa.pft.litecart.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ProductPage extends Page {

  public ProductPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(xpath = "//select[@name='options[Size]']")
  private WebElement sizeSelectionDropdown;

  @FindBy(xpath = "//button[@name='add_cart_product']")
  private WebElement addProduct;

  @FindBy(xpath = "//span[@class='quantity']")
  private WebElement itemsInCart;

  @FindBy(xpath = "//li[@class='general-0']//a")
  private WebElement goToMainPage;

  @FindBy(xpath = "//a[contains(text(),'Checkout »')]")
  private WebElement checkout;

  public void addProductToCart() {
    addProduct.click();
    Alert alert = wait.until(alertIsPresent());
    alert.accept();
  }

  public void selectSizeOfProduct() {
    if (isElementPresent(By.xpath("//select[@name='options[Size]']"))) {
      new Select(sizeSelectionDropdown).selectByVisibleText("Small");
    }
  }

  private boolean isElementPresent(By locator) {
    return driver.findElements(locator).size() > 0;
  }

  public void checkItemsInCart(int items) {
    driver.navigate().refresh();
    itemsInCart = driver.findElement(By.xpath("//span[@class='quantity']"));
    wait.until(textToBePresentInElement(itemsInCart, String.valueOf(items)));
  }

  public void goToMainPage(){
    goToMainPage.click();
  }

  public void goToCheckout() {
    checkout.click();
  }
}
