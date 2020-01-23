package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@XStreamAlias("contact")
@Entity
@Table (name="addressbook")
public class ContactData {

  @XStreamOmitField
  @Id
  @Column(name = "id")
  private int id = Integer.MAX_VALUE;

  @Expose
  @Column(name = "firstname")
  private String name;

  @Expose
  @Column(name = "lastname")
  private String lastName;

  @Column(name = "nickName")
  private String nickName;

  @Column(name = "photo")
  @Type(type = "text")
  private String photo;

  @Expose
  @Column(name = "title")
  private String title;

  @Expose
  @Column(name = "company")
  private String companyName;

  @Expose
  @Column(name = "address")
  @Type(type = "text")
  private String companyAddress;

  @Column(name = "homepage")
  @Type(type = "text")
  private String companyURL;

  @Expose
  @Column(name = "email")
  @Type(type = "text")
  private String email;

  @Column(name = "email2")
  @Type(type = "text")
  private String email2;

  @Column(name = "email3")
  @Type(type = "text")
  private String email3;

  @Column(name = "work")
  @Type(type = "text")
  private String workPhone;

  @Column(name = "mobile")
  @Type(type = "text")
  private String mobilePhone;

  @Expose
  @Column(name = "home")
  @Type(type = "text")
  private String homePhone;

  @Column(name = "byear")
  private String year;

  @Column(name = "bmonth")
  private String month;

  @Column(name = "bday")
  @Transient
  private String day;

  @Column(name = "notes")
  @Type(type = "text")
  private String note;

  @Column(name = "phone2")
  @Type(type = "text")
  private String secondPhone;

  @Column(name = "address2")
  @Type(type = "text")
  private String secondAddress;

  @Transient
  private String allPhones;

  @Transient
  private String allEmails;

  @Expose
  @Transient
  private String group;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "address_in_groups", joinColumns = @JoinColumn(name = "id")
          , inverseJoinColumns = @JoinColumn(name = "group_id"))
  private Set<GroupData> groups = new HashSet<GroupData>();

  public ContactData withAllEmails(String allEmails) {
    this.allEmails = allEmails;
    return this;
  }

  public ContactData withName(String name) {
    this.name = name;
    return this;
  }

  public ContactData withLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public ContactData withNickName(String nickName) {
    this.nickName = nickName;
    return this;
  }

  public ContactData withPhoto(File photo) {
    this.photo = photo.getPath();
    return this;
  }

  public ContactData withTitle(String title) {
    this.title = title;
    return this;
  }

  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public ContactData withCompanyName(String companyName) {
    this.companyName = companyName;
    return this;
  }

  public ContactData withCompanyAddress(String companyAddress) {
    this.companyAddress = companyAddress;
    return this;
  }

  public ContactData withWorkPhone(String workPhone) {
    this.workPhone = workPhone;
    return this;
  }

  public ContactData withMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
    return this;
  }

  public ContactData withHomePhone(String homePhone) {
    this.homePhone = homePhone;
    return this;
  }

  public ContactData withEmail(String email) {
    this.email = email;
    return this;
  }

  public ContactData withEmail2(String email2) {
    this.email2 = email2;
    return this;
  }

  public ContactData withEmail3(String email3) {
    this.email3 = email3;
    return this;
  }

  public ContactData withCompanyURL(String companyURL) {
    this.companyURL = companyURL;
    return this;
  }

  public ContactData withYear(String year) {
    this.year = year;
    return this;
  }

  public ContactData withMonth(String month) {
    this.month = month;
    return this;
  }

  public ContactData withDay(String day) {
    this.day = day;
    return this;
  }

  public ContactData withNote(String note) {
    this.note = note;
    return this;
  }

  public ContactData withSecondPhone(String secondPhone) {
    this.secondPhone = secondPhone;
    return this;
  }

  public ContactData withSecondAddress(String secondAddress) {
    this.secondAddress = secondAddress;
    return this;
  }

  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public ContactData withGroup(String group) {
    this.group = group;
    return this;
  }

  public String getGroup() {
    return group;
  }

  public String getName() {
    return name;
  }

  public String getLastName() {
    return lastName;
  }

  public String getNickName() {
    return nickName;
  }

  public File getPhoto() {
    return new File(photo);
  }

  public String getTitle() {
    return title;
  }

  public String getCompanyName() {
    return companyName;
  }

  public String getCompanyAddress() {
    return companyAddress;
  }

  public String getCompanyURL() {
    return companyURL;
  }

  public String getEmail() {
    return email;
  }

  public String getEmail2() {
    return email2;
  }

  public String getEmail3() {
    return email3;
  }

  public String getWorkPhone() {
    return workPhone;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public String getHomePhone() {
    return homePhone;
  }

  public String getYear() {
    return year;
  }

  public String getMonth() {
    return month;
  }

  public String getDay() {
    return day;
  }

  public String getNote() {
    return note;
  }

  public String getSecondPhone() {
    return secondPhone;
  }

  public String getSecondAddress() {
    return secondAddress;
  }

  public String getAllPhones() {
    return allPhones;
  }

  public String getAllEmails() {
    return allEmails;
  }

  public int getId() {return id;}

  public Groups getGroups() {
    return new Groups(groups);
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", lastName='" + lastName + '\'' +
            ", title='" + title + '\'' +
            ", companyName='" + companyName + '\'' +
            ", companyAddress='" + companyAddress + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return id == that.id &&
            Objects.equals(name, that.name) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(title, that.title) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(companyAddress, that.companyAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, lastName, title, companyName, companyAddress);
  }

  public ContactData inGroup(GroupData group){
    groups.add(group);
    return this;
  }
}




