/*
package ru.stqa.pft.litecart.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;

import java.io.File;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static org.testng.Assert.assertEquals;

public class GettingStarted extends TestBase {

  private void type(By locator, String text) {
    wd.findElement(locator).click();
    wd.findElement(locator).clear();
    wd.findElement(locator).sendKeys(text);
  }

  private String newWindow() {
    String mainWindow = wd.getWindowHandle();
    wait.until(numberOfWindowsToBe(2));
    Set<String> allWindows = wd.getWindowHandles();
    String newWindow = null;
    for (String window : allWindows) {
      if (!window.equals(mainWindow)) {
        newWindow = window;
      }
    }
    return newWindow;
  }

  @Test(enabled = false)
  public void loopThroughMenuItems() {
    wd.get("http://localhost/litecart/admin/");
    wd.manage().window().maximize();
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("login")).click();

    List<WebElement> id = wd.findElements(By.xpath("//li[@id='app-']"));

    for (int i = 1; i <= id.size(); i++) {
      wd.findElement(By.xpath("//li[@id='app-'][" + i + "]")).click();
      List<WebElement> con = wd.findElements(By.xpath("//ul[@class='docs']"));
      if (con != null) {
        List<WebElement> attr = wd.findElements(By.xpath("//li[contains(@id,'doc')]"));
        for (int j = 1; j <= attr.size(); j++) {
          wd.findElement(By.xpath("//li[contains(@id,'doc')][" + j + "]")).click();
          boolean present;
          try {
            wd.findElement(By.xpath("//tr/td/h1"));
            present = true;
          } catch (NoSuchElementException e) {
            present = false;
          }
          Assert.assertTrue(present);
        }
      }
    }
  }

  @Test(enabled = false)
  public void checkStickers() {
    wd.get("http://localhost/litecart/en/");
    wd.manage().window().maximize();
    List<WebElement> ducks = wd.findElements(By.xpath("//li[contains(@class, 'product')]"));

    for (WebElement duck : ducks) {
      int sticker = duck.findElements(By.xpath(".//div[contains(@class, 'sticker')]")).size();
      if (sticker > 0) {
        assertEquals(sticker, 1);
        System.out.println(sticker);
      }
    }
  }

  @Test(enabled = false)
  public void checkAlphaOrder() throws InterruptedException {
    wd.get("http://localhost/litecart/admin/?app=countries&doc=countries");
    wd.manage().window().maximize();
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("login")).click();
    TestHelper testHelper = new TestHelper();
    List<WebElement> countries = wd.findElements(By.xpath("//tr//td[5]/a"));
    List<String> listOfCountries = testHelper.createListOf(countries);
    testHelper.sort(listOfCountries);
    int numberOfCountries = wd.findElements(By.xpath("//tr[@class='row']")).size();
    for (int i = 0; i < numberOfCountries; i++) {
      List<WebElement> we = wd.findElements(By.xpath("//tr[@class='row']"));
      WebElement w = we.get(i);
      if (Integer.parseInt(w.findElement(By.xpath("./td[6]")).getText().trim()) > 0) {
        w.findElement(By.xpath("./td[5]/a[@href]")).click();
        List<WebElement> zones = wd.findElements(By.xpath("//table[@class='dataTable']//tr/td[3]"));
        List<String> listOfZones = testHelper.createListOf(zones);
        testHelper.sort(listOfZones);
        wd.findElement(By.xpath("//li[3]//a[1]")).click();
      }
    }
  }

  @Test(enabled = false)
  public void checkAlphaOrder2() throws InterruptedException {
    wd.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
    wd.manage().window().maximize();
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("login")).click();

    TestHelper testHelper = new TestHelper();

    int numberOfCountries = wd.findElements(By.xpath("//tr[@class='row']")).size();

    for (int i = 0; i < numberOfCountries; i++) {
      List<WebElement> we = wd.findElements(By.xpath("//tr[@class='row']"));
      WebElement w = we.get(i);
      w.findElement(By.xpath("./td[3]/a[@href]")).click();
      List<WebElement> zones = wd.findElements(By.xpath("//form//td[3]//option[@selected='selected']"));
      List<String> listOfZones = testHelper.createListOf(zones);
      testHelper.sort(listOfZones);
      wd.findElement(By.xpath("//li[6]//a[1]")).click();
    }
  }

  @Test(enabled = false)
  public void checkCampaignsItem() throws InterruptedException {
    wd.get("http://localhost/litecart/en/");
    wd.manage().window().maximize();
    WebElement campaignName = wd.findElement(By.xpath("//div[@id='box-campaigns']//a/div[2]"));
    WebElement campaignRegularPrice = wd.findElement(By.xpath("//div[@id='box-campaigns']//a/div[4]/s"));
    WebElement campaignPromoPrice = wd.findElement(By.xpath("//div[@id='box-campaigns']//a/div[4]/strong"));
    ProductVerification pd1 = new ProductVerification(campaignName, campaignRegularPrice, campaignPromoPrice);

    Assert.assertTrue(pd1.regularPrice());
    Assert.assertTrue(pd1.promoPrice());
    Assert.assertTrue(pd1.promoPriceFont() > pd1.regularPriceFont());

    wd.findElement(By.xpath("//div[@id='box-campaigns']//a[1]")).click();

    WebElement productName = wd.findElement(By.xpath("//div[@id='box-product']/div[1]/h1"));
    WebElement productRegularPrice = wd.findElement(By.xpath("//div[@id='box-product']/div[2]/div[2]/div[2]/s"));
    WebElement productPromoPrice = wd.findElement(By.xpath("//div[@id='box-product']/div[2]/div[2]/div[2]/strong"));
    ProductVerification pd2 = new ProductVerification(productName, productRegularPrice, productPromoPrice);

    Assert.assertTrue(pd2.regularPrice());
    Assert.assertTrue(pd2.promoPrice());
    Assert.assertTrue(pd2.promoPriceFont() > pd2.regularPriceFont());

    wd.findElement(By.xpath("//div[@class='content']//div//a[contains(text(),'Home')]")).click();

    Assert.assertEquals(pd1.productInfo(), pd2.productInfo());
  }

  @Test(enabled = false)
  public void userRegistration() throws Exception {
    wd.get("http://localhost/litecart/en/");
    wd.manage().window().maximize();
    wd.findElement(By.linkText("New customers click here")).click();
    type(By.name("firstname"), "David");
    type(By.name("lastname"), "John");
    type(By.name("tax_id"), "123");
    type(By.name("company"), "abc");
    type(By.name("address1"), "123 first line");
    type(By.name("postcode"), "12345");
    type(By.name("city"), "NY");
    String email = RandomStringUtils.randomAlphabetic(8) + "@mail.ru";
    type(By.name("email"), email);
    type(By.name("phone"), "1234567890");
    type(By.name("password"), "123");
    type(By.name("confirmed_password"), "123");
    new Select(wd.findElement(By.name("zone_code"))).selectByVisibleText("Ontario");
    wd.findElement(By.xpath("//span[@class='select2-selection__arrow']")).click();
    wd.findElement(By.xpath("//input[@class='select2-search__field']")).sendKeys("United States", Keys.ENTER);

    if (!wd.findElement(By.xpath("//input[@name='newsletter']")).isSelected()) {
      wd.findElement(By.xpath("//input[@name='newsletter']")).click();
    }
    wd.findElement(By.name("create_account")).click();
    wd.findElement(By.linkText("Logout")).click();
    type(By.name("email"), "david@abc.com");
    type(By.name("password"), "123");
    wd.findElement(By.name("login")).click();
    wd.findElement(By.linkText("Logout")).click();
  }

  @Test(enabled = false)
  public void addProduct() throws Exception {
    wd.get("http://localhost/litecart/admin/");
    wd.manage().window().maximize();
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("login")).click();
    wd.findElement(By.xpath("//li[2]//a[1]")).click();
    wd.findElement(By.xpath("//a[contains(text(),'Add New Product')]")).click();

    //заполнение полей
    wd.findElement(By.xpath("//label[contains(text(),'Enabled')]//input[@name='status']")).click();
    type(By.name("name[en]"), "product");
    type(By.name("code"), "123");
    type(By.name("quantity"), "1000");

    // проставление галочек
    if (!wd.findElement(By.xpath("//tr[3]//input[@name='categories[]']")).isSelected()) {
      wd.findElement(By.xpath("//tr[3]//input[@name='categories[]']")).click();
    }
    if (!wd.findElement(By.xpath("//tr[4]//input[@name='product_groups[]']")).isSelected()) {
      wd.findElement(By.xpath("//tr[4]//input[@name='product_groups[]']")).click();
    }

    //выбор опций из выпадающих списков
    new Select(wd.findElement(By.name("quantity_unit_id"))).selectByVisibleText("pcs");
    new Select(wd.findElement(By.name("delivery_status_id"))).selectByVisibleText("3-5 days");
    new Select(wd.findElement(By.name("sold_out_status_id"))).selectByVisibleText("Temporary sold out");

    //загрузка картинки с указанием относительного пути
    File file = new File("image/Capture.JPG");
    String image = file.getAbsolutePath();
    WebElement uploadElement = wd.findElement(By.name("new_images[]"));
    uploadElement.sendKeys(image);

    //проставление даты, от - до
    JavascriptExecutor js = (JavascriptExecutor) wd;
    js.executeScript("document.getElementsByName('date_valid_from')[0].setAttribute('value', '2019-12-12')");
    js.executeScript("document.getElementsByName('date_valid_to')[0].setAttribute('value', '2020-12-12')");

    //переход на вкладку "Infomation"
    wd.findElement(By.xpath("//a[contains(text(),'Information')]")).click();
    TimeUnit.SECONDS.sleep(2);

    //заполнение полей + выбор опций из выпадающих списков
    new Select(wd.findElement(By.name("manufacturer_id"))).selectByVisibleText("ACME Corp.");
    type(By.name("keywords"), "abc");
    type(By.name("short_description[en]"), "abc");
    type(By.cssSelector("div.trumbowyg-editor"), "abc");
    type(By.name("head_title[en]"), "abc");
    type(By.name("meta_description[en]"), "abc");

    //переход на вкладку "Prices"
    wd.findElement(By.xpath("//a[contains(text(),'Prices')]")).click();
    TimeUnit.SECONDS.sleep(2);

    //заполнение полей + выбор опций из выпадающих списков
    type(By.name("purchase_price"), "20.00");
    new Select(wd.findElement(By.name("purchase_price_currency_code"))).selectByVisibleText("US Dollars");
    new Select(wd.findElement(By.name("tax_class_id"))).selectByVisibleText("Standard");
    type(By.xpath("//input[@name='prices[USD]']"), "40.00");
    type(By.xpath("//input[@name='prices[EUR]']"), "40.00");

    //сохранение формы
    wd.findElement(By.name("save")).click();
  }

  @Test(enabled = false)
  public void switchBetweenWindows() throws Exception {
    wd.get("http://localhost/litecart/admin/?app=countries&doc=countries");
    wd.manage().window().maximize();
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("login")).click();
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='button']"))).click();
    List<WebElement> links = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tr/td/a[@target]")));
    String mainWindow = wd.getWindowHandle();
    for (WebElement link : links) {
      link.click();
      wd.switchTo().window(newWindow());
      wd.close();
      wd.switchTo().window(mainWindow);
    }
  }

  @Test(enabled = false)
  public void checkLogs() throws Exception {
    wd.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
    wd.manage().window().maximize();
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("login")).click();
    int numberOfPproducts = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tr/td[3]/a[contains(text(), 'Duck')]"))).size();
    for (int i = 0; i < numberOfPproducts; i++) {
      List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tr/td[3]/a[contains(text(), 'Duck')]")));
      products.get(i).click();
      wd.manage().logs().get("browser").forEach(System.out::println);
      wd.findElement(By.xpath("//li[@id='doc-catalog']//a")).click();
      wd.findElement(By.xpath("//tr/td[3]/a[contains(text(), 'Ducks')]")).click();
    }
  }

  @Test(enabled = true)
  public void addToCart() throws InterruptedException {
    wd.get("http://localhost/litecart/en/");
    wd.manage().window().maximize();
    WebElement items = wd.findElement(By.xpath("//span[@class='quantity']"));

    //добавление товара
    for (int i = 0; i < 3; i++) {
      wd.findElement(By.xpath("//div[@id='box-most-popular']//li[1]")).click();
      if (isElementPresent(wd, By.xpath("//select[@name='options[Size]']"))) {
        new Select(wd.findElement(By.xpath("//select[@name='options[Size]']"))).selectByVisibleText("Small");
      }
      wd.findElement(By.xpath("//button[@name='add_cart_product']")).click();


      Alert alert = wait.until(alertIsPresent());
      alert.accept();
      wd.navigate().refresh();
      wait.until(stalenessOf(items));
      items = wd.findElement(By.xpath("//span[@class='quantity']"));
      wait.until(textToBePresentInElement(items, String.valueOf(i + 1)));
      wd.findElement(By.xpath("//li[@class='general-0']//a")).click();
    }

    //переход в корзину
    wd.findElement(By.xpath("//a[contains(text(),'Checkout »')]")).click();

    //создание списка товаров
    WebElement table = wd.findElement(By.xpath("//div[@id='checkout-summary-wrapper']"));
    List<WebElement> products = table.findElements(By.xpath(".//tr/td[@class='item']"));

    //удаление товара
    for (int i = 1; i < 3; i++) {
      List<WebElement> removeitem = wd.findElements(By.xpath("//li//button[contains(text(), 'Remove')]"));
      wait.until(visibilityOf(removeitem.get(0))).click();
      wait.until(stalenessOf(products.get(0)));
      products = table.findElements(By.xpath(".//tr/td[@class='item']"));
      Assert.assertEquals(products.size(), 3 - i);
    }
  }
}*/
