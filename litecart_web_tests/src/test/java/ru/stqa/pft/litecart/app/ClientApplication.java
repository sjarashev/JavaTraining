package ru.stqa.pft.litecart.app;

import org.openqa.selenium.WebDriver;
import ru.stqa.pft.litecart.pages.CheckoutPage;
import ru.stqa.pft.litecart.pages.MainPage;
import ru.stqa.pft.litecart.pages.ProductPage;

public class ClientApplication{

  private WebDriver driver;
  private MainPage mainPage;
  private ProductPage productPage;
  private CheckoutPage checkoutPage;

  public ClientApplication(WebDriver driver) {
    this.driver = driver;
  }

  public void openWebPage() {
    driver.manage().window().maximize();
    driver.get("http://localhost/litecart/en/");
    mainPage = new MainPage(driver);
    productPage = new ProductPage(driver);
    checkoutPage = new CheckoutPage(driver);
  }

  public void fillCartWithProducts(int number) {
    for (int i = 0; i < number; i++) {
      mainPage.openProduct();
      productPage.selectSizeOfProduct();
      productPage.addProductToCart();
      productPage.checkItemsInCart(i + 1);
      productPage.goToMainPage();
    }
    productPage.goToCheckout();
  }

  public void deleteProductsFromTheCart(int number){
    checkoutPage.deleteProductFromTheCart(number);
  }
}
