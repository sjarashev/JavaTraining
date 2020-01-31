package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

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
    Groups groups = app.db().groups();
    Contacts contacts = app.db().contacts();
    for (GroupData group : groups) {
      if (group.getContacts().size() != contacts.size()) {
        app.findMatchingPair(group, contacts);
        return;
        }
      }
    app.goTo().groupPage();
    app.group().create(new GroupData().withName("New group").withHeader("header").withFooter("footer"));
    app.goTo().homePage();
    app.contact().selectAndAdd(contacts.iterator().next(), app.getNewGroupId());
    Assert.assertTrue(app.after(null, app.getNewGroupId()) > 0);
  }

  @Test(enabled = true)
  public void testRemoveContactFromGroup() {
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();
    if (!app.contactsInGroups(contacts)) {
      app.contact().selectAndAdd(contacts.iterator().next(), groups.iterator().next().getId());
    }
    contacts = app.db().contacts();
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
