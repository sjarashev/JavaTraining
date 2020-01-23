package ru.stqa.pft.litecart.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;

public class ProductVerification {

  private String productNameText;
  private String regularPriceText;
  private String promoPriceText;
  private WebElement regularPriceElement;
  private WebElement promoPriceElement;

  public ProductVerification (WebElement productNameText, WebElement regularPriceText, WebElement promoPriceText){
    this.productNameText = productNameText.getText();
    this.regularPriceText = regularPriceText.getText();
    this.promoPriceText = promoPriceText.getText();
    this.regularPriceElement = regularPriceText;
    this.promoPriceElement = promoPriceText;
  }

  public List productInfo(){
    List<String> l = new ArrayList<>();
    l.add(productNameText);
    l.add(regularPriceText);
    l.add(promoPriceText);
    return l;
  }

  public boolean regularPrice(){
    String i = regularPriceElement.getCssValue("color");
    String r = regularPriceElement.getCssValue("text-decoration-line");
    String[] hexValue = i.replace("rgb(", "").replace(")", "").split(",");
    hexValue[0] = hexValue[0].trim();
    int hexValue1 = Integer.parseInt(hexValue[0]);
    hexValue[1] = hexValue[1].trim();
    int hexValue2 = Integer.parseInt(hexValue[1]);
    hexValue[2] = hexValue[2].trim();
    int hexValue3 = Integer.parseInt(hexValue[2]);
    Assert.assertEquals(hexValue1,hexValue2,hexValue3);
    Assert.assertEquals(r, "line-through");
    return true;
  }

  public boolean promoPrice(){
    String i = promoPriceElement.getCssValue("color");
    String r = promoPriceElement.getTagName();
    String[] hexValue = i.replace("rgb(", "").replace(")", "").split(",");
    hexValue[0] = hexValue[0].trim();
    int hexValue1 = Integer.parseInt(hexValue[0]);
    hexValue[1] = hexValue[1].trim();
    int hexValue2 = Integer.parseInt(hexValue[1]);
    hexValue[2] = hexValue[2].trim();
    int hexValue3 = Integer.parseInt(hexValue[2]);
    Assert.assertEquals(hexValue2,0);
    Assert.assertEquals(hexValue3,0);
    Assert.assertEquals(r, "strong");
    return true;
  }

  public double regularPriceFont(){
    String d = regularPriceElement.getCssValue("font-size").replace("px", "");
    return Double.parseDouble(d);
  }

  public double promoPriceFont(){
    String d = promoPriceElement.getCssValue("font-size").replace("px", "");
    return Double.parseDouble(d);
  }
}
