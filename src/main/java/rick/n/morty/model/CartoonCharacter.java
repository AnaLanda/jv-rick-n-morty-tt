package rick.n.morty.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "characters")
public class CartoonCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Origin origin;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Location location;
    private String imageUrl;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Episode> episodes;
    private String url;
    private String created;
}
