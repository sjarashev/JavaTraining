package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.*;

import java.util.List;

public class ContactHelper extends HelperBase {

  ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitForm() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void gotoAddContactPage() {
    click(By.linkText("add new"));
  }

  public void modifyContactPageById(int id) {
    List<WebElement> rows = wd.findElements(By.xpath("//tr[@class]"));
    int number = wd.findElements(By.xpath("//tr[@class]")).size();
    for (int i=0;i<number;i++){
      WebElement w = rows.get(i);
      if (w.findElements(By.xpath(".//input[@id='" + id + "']")).size()!=0) {
        w.findElement(By.xpath(".//img[@alt='Edit']")).click();
        break;
      }
    }
  }

  public ContactData getContactInfoFromEditForm(ContactData contact) {
    modifyContactPageById(contact.getId());
    String name = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String second = wd.findElement(By.name("phone2")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withName(name).withLastName(lastname)
            .withHomePhone(home)
            .withMobilePhone(mobile)
            .withWorkPhone(work)
            .withSecondPhone(second)
            .withCompanyAddress(address)
            .withEmail(email)
            .withEmail2(email2)
            .withEmail3(email3);
  }

  public void createPartial(ContactData contact) {
    gotoAddContactPage();
    fillPartialContactForm(contact);
    submitForm();
    contactCache = null;
    goToHomePage();
  }

  public void createNew(ContactData conData, boolean creation) {
    gotoAddContactPage();
    fillContactForm(conData, creation);
    submitForm();
    contactCache = null;
    goToHomePage();
  }

  public void modify(ContactData conData) {
    modifyContactPageById(conData.getId());
    fillContactForm(conData, false);
    submitUpdatedContact();
    contactCache = null;
    goToHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteContact();
    closeAttention();
    contactCache = null;
    goToHomePage();
  }

  public void submitUpdatedContact() {
    click(By.name("update"));
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void add(){
    click(By.name("add"));
  }

  public void remove(){
    click(By.name("remove"));
  }

  public List<WebElement> allContacts() {
    List<WebElement> j = wd.findElements(By.name("selected[]"));
    return j;
  }

  public void selectAndAdd3(ContactData contact, int id) {
    selectContactById(contact.getId());
    String s = String.valueOf(id);
    new Select(wd.findElement(By.name("to_group"))).selectByValue(s);
    add();
    goToHomePage();
  }

  public void selectAndAdd(ContactData contact, GroupData group) {
    String id = String.valueOf(group.getId());
    selectContactById(contact.getId());
    new Select(wd.findElement(By.name("to_group"))).selectByValue(id);
    add();
    goToHomePage();
  }

  public void selectAndRemove(ContactData contact, GroupData group) {
    String s = String.valueOf(group.getId());
    new Select(wd.findElement(By.name("group"))).selectByValue(s);
    selectContactById(contact.getId());
    remove();
  }

  public void deleteContact() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void closeAttention() {
    closeAttentionDialog();
    wd.findElement(By.cssSelector("div.msgbox"));
  }

  private void returnToHomePage() {
    click(By.linkText("home page"));
  }

  public void goToHomePage() {
    if (isElementPresent(By.id("maintable"))){
      return;
    }
    click(By.linkText("home"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    if (creation) {
      try {
        new Select(wd.findElement(By.name("new_group")))
                .selectByVisibleText(contactData.getGroup());
      } catch (Exception e) {
        createGroup();
        new Select(wd.findElement(By.name("new_group")))
                .selectByVisibleText(contactData.getGroup());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
    type(By.name("firstname"), contactData.getName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("address"), contactData.getCompanyAddress());
    type(By.name("email"), contactData.getEmail());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getCompanyName());
    /*type(By.name("nickname"), contactData.getNickName());
    attach(By.name("photo"), contactData.getPhoto());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getCompanyName());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("homepage"), contactData.getCompanyURL());
    new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactData.getDay());
    new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactData.getMonth());
    type(By.name("byear"), contactData.getYear());
    type(By.name("notes"), contactData.getNote());
    type(By.name("phone2"), contactData.getSecondPhone());
    type(By.name("address2"), contactData.getSecondAddress());*/
  }

  public void createGroup(){
    NavigationHelper n = new NavigationHelper(wd);
    GroupHelper g = new GroupHelper(wd);
    n.groupPage();
    GroupData group = new GroupData().withName("group").withHeader("header").withFooter("footer");
    g.initGroupPage();
    g.fillGroupForm(group);
    g.submitGroupCreation();
    gotoAddContactPage();
  }

  private void fillPartialContactForm(ContactData contactData) {
    type(By.name("firstname"), contactData.getName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getNickName());
    type(By.name("address"), contactData.getCompanyAddress());
  }

  public boolean thereIsNoContact() {
    return !isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache !=null){
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
    for (WebElement element:elements){
      String lastName = element.findElement(By.xpath("./td[2]")).getText();
      String name = element.findElement(By.xpath("./td[3]")).getText();
      String address = element.findElement(By.xpath("./td[4]")).getText();
      String allEmails = element.findElement(By.xpath("./td[5]")).getText();
      String allPhones = element.findElement(By.xpath("./td[6]")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
      contactCache.add(new ContactData().withId(id).withName(name).withLastName(lastName).withAllPhones(allPhones)
      .withCompanyAddress(address).withAllEmails(allEmails));
    }
    return contactCache;
  }
}
