package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInfoTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().homePage();
    if (app.contact().all().size()==0){
      app.contact().createPartial(new ContactData().withName("David")
              .withLastName("John").withNickName("DJ").withTitle("CEO"));
    }
  }

  @Test (enabled = true)
  public void testContactInfo(){
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().getContactInfoFromEditForm(contact);

    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    assertThat(contact.getCompanyAddress(), equalTo(contactInfoFromEditForm.getCompanyAddress()));
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(),contact.getMobilePhone(),contact.getWorkPhone()
            ,contact.getSecondPhone())
            .stream().filter((s)->!s.equals(""))
            .map(ContactInfoTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(),contact.getEmail2(),contact.getEmail3())
            .stream().filter((s)->!s.equals(""))
            .map(ContactInfoTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String contactInfo){
    return contactInfo.replaceAll("\\s","").replaceAll("[-()]","");
  }
}
