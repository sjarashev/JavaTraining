package ru.stqa.pft.litecart.tests;

import org.testng.annotations.Test;

public class Tests extends TestBase {

  @Test(enabled = false)
  public void testAddToCart(){
    clientApp.openWebPage();
    clientApp.fillCartWithProducts(3);
    clientApp.deleteProductsFromTheCart(3);
  }

  @Test(enabled = true)
  public void testSortCountries() throws InterruptedException {
    adminApp.openWebPage();
    adminApp.gotoCountriesPage();
    adminApp.sortCountries();
    adminApp.sortCountryZones();
  }
}
