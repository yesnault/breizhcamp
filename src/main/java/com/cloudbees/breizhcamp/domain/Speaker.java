package com.cloudbees.breizhcamp.domain;

import com.google.common.base.Objects;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Speaker {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    private long id;
    
    private String firstName;

    private String lastName;

    private String twitter;

    @Column(length = 4000)
    private String description;

    @JsonProperty("pictureUrl")
    private URL picture;

    @ManyToMany(fetch = FetchType.EAGER)
    private final Set<Talk> talks = new HashSet<Talk>();

    @JsonProperty("twitter")
    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public Set<Talk> getTalks() {
        return talks;
    }

    @JsonProperty("name")
        public String getName() {
            return firstName+" "+lastName;
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
