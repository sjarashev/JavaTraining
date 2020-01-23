package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

public class ContactInGroup extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().createPartial(new ContactData().withName("David").withLastName("John").withTitle("CEO")
              .withCompanyName("ABC").withCompanyAddress("3rd Line"));
    }
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("group").withHeader("header").withFooter("footer"));
      app.goTo().homePage();
    }
  }

  @Test(enabled = true)
  public void testAddContactIntoGroup() {
    ContactData contact = app.db().contacts().iterator().next();
    GroupData group = app.db().groups().iterator().next();
    int index = app.db().groups().size();
    Contacts contacts = group.getContacts();
    int before = contact.getGroups().size();

    if (group.getContacts().size() == 0) {
      app.contact().selectAndAdd(contact, group);
      contact = app.db().contacts().iterator().next();
      int after = contact.getGroups().size();
      Assert.assertTrue(after > before);

    } else {
      for (ContactData conData : contacts) {
        try {
          Assert.assertNotEquals(conData.getId(), contact.getId());
        } catch (AssertionError e) {
          app.goTo().groupPage();
          app.group().create(new GroupData().withName("group").withHeader("header").withFooter("footer"));
          List<WebElement> ws = app.group().allGroups();
          int max = 0;
          for (WebElement w : ws) {
            int g = Integer.parseInt(w.getAttribute("value"));
            if (g > max) {
              max = g;
            }
          }
          app.goTo().homePage();
          app.contact().selectAndAdd3(contact, max);
          contact = app.db().contacts().iterator().next();
          int after = contact.getGroups().size();
          Assert.assertTrue(after > before);
          break;
        }
      }
      int index2 = app.db().groups().size();
      if (index == index2) {
        app.contact().selectAndAdd(contact, group);
        contact = app.db().contacts().iterator().next();
        int after = contact.getGroups().size();
        Assert.assertTrue(after > before);
      }
    }
  }

  @Test(enabled = true)
  public void testRemoveContactFromGroup() {
    Contacts contacts = app.db().contacts();
    for (ContactData contact : contacts) {
      int id = contact.getId();
      try {
        Assert.assertNull(contact.getGroups());
      } catch (AssertionError e) {
        Groups groupsFromContact = contact.getGroups();
        int before = groupsFromContact.size();
        for (GroupData group : groupsFromContact) {
          app.contact().selectAndRemove(contact, group);
          app.goTo().homePage();
          ContactData contactFromDb = app.db().contact(id);
          groupsFromContact = contactFromDb.getGroups();
          int after = groupsFromContact.size();
          Assert.assertTrue(after < before);
        }
      }
    }
  }
}
