package models;

import org.codehaus.jackson.annotate.JsonIgnore;
import play.data.validation.Email;
import play.data.validation.MinSize;
import play.data.validation.Required;
import util.PasswordEncoder;
import util.Utils;

import javax.persistence.*;

@Entity
public class Person extends AbstractDomainModel {


    @Required
    public String firstname;
    @Required
    public String lastname;
    @Required @Email
    @Column(nullable=false)
    public String email;

    @JsonIgnore
    @MinSize(6)
    @Column(nullable=false)
    public String password;

    @JsonIgnore
    public boolean enabled = true;

    public String getFullname() {
        return firstname + " " + lastname;
    }
    
    public void setNewPassword(String password) {
        String salt = Utils.generateRandomString(5);
        this.password = salt + "$" + PasswordEncoder.encodePassword(password, salt);
    }

    @Override
    public String toString() {
        return getFullname();
    }
}