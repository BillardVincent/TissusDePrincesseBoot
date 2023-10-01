package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class RangementDemat extends AbstractEntity{

    private String url;
    @ManyToOne
    private RangementRootDemat conteneur;
    @OneToMany(mappedBy = "rangement")
    private List<Patron> patron;

    public List<Patron> getPatron() {
        if (patron == null){
            patron = new ArrayList<>();
        }
        return patron;
    }

}
