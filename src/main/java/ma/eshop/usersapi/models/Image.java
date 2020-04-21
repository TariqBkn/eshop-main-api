package ma.eshop.usersapi.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Image {
    @Id
    private String id;
    private String path;
    @ManyToOne
    private Product product;
    protected Image(){

    }

    public Image(String path){
        this.path=path;
    }
}
