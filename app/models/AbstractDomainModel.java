package models;

import org.codehaus.jackson.annotate.JsonIgnore;
import play.db.jpa.Blob;
import play.db.jpa.GenericModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractDomainModel extends GenericModel {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    @JsonIgnore
    public String getLabel() {
        return toString();
    }

}