package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ModifyContactTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size()==0){
      app.goTo().homePage();
      app.contact().createPartial(new ContactData().withName("David").withLastName("John").withNickName("DJ").withTitle("CEO"));
    }
  }

  @Test(enabled = true)
  public void testModifyContact() throws InterruptedException {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contactForm = new ContactData().withId(modifiedContact.getId())
            .withName("Mark").withLastName("Robinson").withNickName("MR").withTitle("COO")
            .withCompanyName("CBA").
                    withCompanyAddress("777 Third Line").
                    withHomePhone("797979").
                    withMobilePhone("5252525").
                    withWorkPhone("454545").
                    withEmail("mr@gmail.com").
                    withCompanyURL("www.cba.com").
                    withYear("1980").
                    withMonth("November").
                    withDay("11").
                    withSecondAddress("45245245").
                    withSecondPhone("858585").
                    withNote("blabla")/*.
                    withGroup(null)*/;
    app.goTo().homePage();
    app.contact().modify(contactForm);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contactForm)));
    verifyContactListInUi();
  }
}
