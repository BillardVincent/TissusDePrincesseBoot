package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class RangementRootDemat extends AbstractEntity{

    private String nom;
    @OneToMany(mappedBy = "conteneur")
    private List<RangementDemat> subdivision;

    public List<RangementDemat> getSubdivision() {
        if (subdivision == null){
            subdivision = new ArrayList<>();
        }
        return subdivision;
    }

}
