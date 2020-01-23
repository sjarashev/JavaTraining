package ru.stqa.pft.litecart.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;

class TestHelper{

  List<String> createListOf(List<WebElement> we) {
    List<String> newList = new ArrayList<>();
    for (WebElement webElement : we) {
      if (webElement.getText() != null && !webElement.getText().trim().equals("")) {
        newList.add(webElement.getText());
      }
    }
    return newList;
  }

  void sort(List<String> we) {
    String previousWe, currentWe;
    previousWe = we.get(0);
    for (int i = 1; i < we.size(); i++) {
      currentWe = we.get(i);
      Assert.assertTrue(previousWe.trim().toLowerCase().compareTo(currentWe.trim().toLowerCase()) < 0);
      previousWe = we.get(i);
    }
    System.out.println("Список отсортирован в алфавитном порядке");
  }
}