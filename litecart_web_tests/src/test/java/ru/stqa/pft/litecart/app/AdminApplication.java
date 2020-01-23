package ru.stqa.pft.litecart.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminApplication{

  private WebDriver driver;

  public AdminApplication(WebDriver driver) {
    this.driver=driver;
  }

  public void openWebPage() {
    driver.manage().window().maximize();
    driver.get("http://localhost/litecart/admin/login.php");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();
  }

  public void d(){
    driver.findElement(By.xpath("//a[@class='button']")).click();
  }
}
