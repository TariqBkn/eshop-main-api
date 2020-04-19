package ma.eshop.usersapi.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Image {
    @Id
    private String id;
    private String path;

    protected Image(){

    }

    public Image(String path){
        this.path=path;
    }
}
