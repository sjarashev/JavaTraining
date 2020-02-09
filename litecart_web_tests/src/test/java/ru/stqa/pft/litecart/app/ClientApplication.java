package ru.stqa.pft.litecart.app;

import org.openqa.selenium.WebDriver;
import ru.stqa.pft.litecart.pages.ClientCheckoutPage;
import ru.stqa.pft.litecart.pages.ClientMainPage;
import ru.stqa.pft.litecart.pages.ClientProductPage;

public class ClientApplication{

  private WebDriver driver;
  private ClientMainPage clientMainPage;
  private ClientProductPage clientProductPage;
  private ClientCheckoutPage clientCheckoutPage;

  public ClientApplication(WebDriver driver) {
    this.driver = driver;
  }

  public void openWebPage() {
    driver.manage().window().maximize();
    driver.get("http://localhost/litecart/en/");
    clientMainPage = new ClientMainPage(driver);
    clientProductPage = new ClientProductPage(driver);
    clientCheckoutPage = new ClientCheckoutPage(driver);
  }

  public void fillCartWithProducts(int number) {
    for (int i = 0; i < number; i++) {
      clientMainPage.openProduct();
      clientProductPage.selectSizeOfProduct();
      clientProductPage.addProductToCart();
      clientProductPage.checkItemsInCart(i + 1);
      clientProductPage.goToMainPage();
    }
    clientProductPage.goToCheckout();
  }

  public void deleteProductsFromTheCart(int number){
    clientCheckoutPage.deleteProductFromTheCart(number);
  }
}
