package com.cloudbees.breizhcamp.domain;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.google.common.base.Objects;

/**
 * @author: <a hef="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
@Entity
public class Speaker {

    @Id
    @GeneratedValue
    private long id;
    
    private String firstName;

    private String lastName;
    
    private URL picture;

    @ManyToMany()
    private final Set<Talk> talks = new HashSet<Talk>();

    public long getId() {
        return id;
    }

    public Set<Talk> getTalks() {
        return talks;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

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
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Speaker) {
            Speaker other=(Speaker)o;
            return Objects.equal(other.firstName, firstName) &&
                    Objects.equal(other.lastName, lastName);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
    	return Objects.hashCode(firstName, lastName);
    }
    
    @Override
    public String toString() {
    	return Objects.toStringHelper(this).add("id", id).add("firstname", firstName).add("lastname", lastName).toString();
    }
}
