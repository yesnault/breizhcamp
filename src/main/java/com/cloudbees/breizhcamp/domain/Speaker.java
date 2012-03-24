package com.cloudbees.breizhcamp.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: <a hef="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Speaker {

    @Id
    @GeneratedValue
    private long id;
    
    private String firstName;

    private String lastName;
    
    private URL picture;

    @ManyToMany()
    private Set<Talk> talks = new HashSet<Talk>();

    public long getId() {
        return id;
    }

    public Set<Talk> getTalks() {
        return talks;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public URL getPicture() {
        return picture;
    }

    public void setPicture(URL picture) {
        this.picture = picture;
    }


    @Override
    public String toString() {
        return "Speaker{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
