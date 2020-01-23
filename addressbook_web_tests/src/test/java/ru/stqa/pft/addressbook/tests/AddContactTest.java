package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactTest extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromXML() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xStream = new XStream();
      xStream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xStream.fromXML(xml);
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJSON() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType()); //List<ContactData>.class
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(enabled = true, dataProvider = "validContactsFromJSON")
  public void testAddContact(ContactData contactForm) {
    app.goTo().homePage();
    Contacts before = app.db().contacts();
/*
    File photo = new File("src/test/resources/image.jpg");
    ContactData contactForm = new ContactData().withName("David").withLastName("John").withNickName("DJ").withPhoto(photo)
            .withTitle("CEO")
            .withCompanyName("ABC")
            .withCompanyAddress("555 First Line")
            .withHomePhone("55555555")
            .withMobilePhone("777777777")
            .withWorkPhone("999999999")
            .withEmail("dj@gmail.com")
            .withCompanyURL("www.abc.com")
            .withYear("1979")
            .withMonth("December")
            .withDay("10")
            .withSecondAddress("202020")
            .withSecondPhone("101010")
            .withNote("bla")
            .withGroup("group");*/
    app.contact().createNew(contactForm, true);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(
            before.withAdded(contactForm.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));
    verifyContactListInUi();
  }

  @Test(enabled = true)
  public void testAddBadContact() {
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    ContactData contactForm = new ContactData().withName("David'").withLastName("John'").withNickName("DJ").withTitle("CEO")
            .withCompanyName("ABC").
                    withCompanyAddress("555 First Line").
                    withHomePhone("55555555").
                    withMobilePhone("777777777").
                    withWorkPhone("999999999").
                    withEmail("dj@gmail.com").
                    withCompanyURL("www.abc.com").
                    withYear("1979").
                    withMonth("December").
                    withDay("10").
                    withSecondAddress("202020").
                    withSecondPhone("101010").
                    withNote("bla").
                    withGroup("group");
    app.contact().createNew(contactForm, true);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before));
    verifyContactListInUi();
  }
}
