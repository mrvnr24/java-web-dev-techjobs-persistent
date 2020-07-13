package org.launchcode.javawebdevtechjobspersistent.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employer extends AbstractEntity {

  @OneToMany
  @JoinColumn
  private final List<Job> jobs = new ArrayList<>();

  @Size(max=100, message = "Location too long!")
  private String location;

  public Employer(@Size(max=100, message = "Location too long!") String location) {
    this.location = location;
  }

  public Employer() {}

  public List<Job> getJobs() {
    return jobs;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
