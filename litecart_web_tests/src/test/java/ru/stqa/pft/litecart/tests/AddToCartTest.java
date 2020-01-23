package ru.stqa.pft.litecart.tests;

import org.testng.annotations.Test;

public class AddToCartTest extends TestBase {

  @Test(enabled = true)
  public void addToCart(){
    clientApp.openWebPage();
    clientApp.fillCartWithProducts(3);
    clientApp.deleteProductsFromTheCart(3);
  }

  @Test (enabled = true)
  public void startStart(){
    adminApp.openWebPage();
    adminApp.d();
  }
}
