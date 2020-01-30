package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class ApplicationManager {
  private WebDriver wd;
  private NavigationHelper navigationHelper;
  private ContactHelper contactHelper;
  private GroupHelper groupHelper;
  private String browser;
  private final Properties properties;
  private DbHelper dbHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {

    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    dbHelper = new DbHelper();

    if ("".equals(properties.getProperty("selenium.server"))) {
      switch (browser) {
        case BrowserType.FIREFOX:
          wd = new FirefoxDriver();
          break;
        case BrowserType.CHROME:
          wd = new ChromeDriver();
          break;
        case BrowserType.EDGE:
          wd = new EdgeDriver();
          break;
      }
    } else {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setBrowserName(browser);
      //capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win10")));
      wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
    }

    wd.manage().timeouts().implicitlyWait(70, TimeUnit.MILLISECONDS);
    wd.manage().window().maximize();
    wd.get(properties.getProperty("web.baseUrl"));
    groupHelper = new GroupHelper(wd);
    contactHelper = new ContactHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    SessionHelper sessionHelper = new SessionHelper(wd);
    sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
  }

  public void logout() {
    wd.findElement(By.linkText("Logout")).click();
  }

  public void stop() {
    wd.quit();
  }

  public GroupHelper group() {
    return groupHelper;
  }

  public ContactHelper contact() {
    return contactHelper;
  }

  public NavigationHelper goTo() {
    return navigationHelper;
  }

  public DbHelper db() {
    return dbHelper;
  }

  public int getNewGroupId() {
    Groups groups = db().groups();
    int newGroupId = 0;
    for (GroupData group : groups) {
      int maxId = group.getId();
      if (maxId > newGroupId) {
        newGroupId = maxId;
      }
    }
    return newGroupId;
  }

  public int after(GroupData group, int id) {
    Groups groups = db().groups();
    int after = 0;
    if (id == 0) {
      for (GroupData groupAfter : groups) {
        if (groupAfter.getId() == group.getId()) {
          after = groupAfter.getContacts().size();
          break;
        }
      }
    }
    for (GroupData groupAfter : groups) {
      if (groupAfter.getId() == id) {
        after = groupAfter.getContacts().size();
      }
    }
    return after;
  }

  public void findMatchingPair(GroupData group, Contacts contacts) {
    Set<ContactData> filteredContacts = new HashSet<ContactData>();
    int before = group.getContacts().size();
    for (ContactData contact : contacts) {
      try {
        assertThat(group.getContacts(), hasItem(contact));
      } catch (AssertionError e) {
        filteredContacts.add(contact);
      }
    }
    if (filteredContacts.size() != 0) {
      contact().selectAndAdd(filteredContacts.iterator().next(), group.getId());
      Assert.assertTrue(after(group, 0) > before);
    }
  }
}
