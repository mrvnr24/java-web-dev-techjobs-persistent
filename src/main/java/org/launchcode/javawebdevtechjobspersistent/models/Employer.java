package org.launchcode.javawebdevtechjobspersistent.models;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
public class Employer extends AbstractEntity {

  @Size(max=100, message = "Location too long!")
  private String location;

  public Employer(@Size(max=100, message = "Location too long!") String location) {
    this.location = location;
  }

  public Employer() {}

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
