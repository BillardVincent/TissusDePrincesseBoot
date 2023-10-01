package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Rangement extends AbstractEntity{

    @OneToMany(mappedBy = "conteneur")
    private List<Rangement> subdivision;
    @ManyToOne
    private Rangement conteneur;
    @ManyToOne
    private RangementRoot conteneurRoot;

    private String nom;
    @OneToMany(mappedBy = "rangement")
    private List<Fourniture> fournitures;
    @OneToMany(mappedBy = "rangement")
    private List<Tissu> tissus;
    @OneToMany(mappedBy = "rangement")
    private List<Patron> patron;
    private int rang;

    public List<Rangement> getSubdivision() {
        if (subdivision == null){
            subdivision = new ArrayList<>();
        }
        return subdivision;
    }

    public List<Fourniture> getFournitures() {
        if (fournitures == null){
            fournitures = new ArrayList<>();
        }
        return fournitures;
    }

    public List<Tissu> getTissus() {
        if (tissus == null){
            tissus = new ArrayList<>();
        }
        return tissus;
    }

    public List<Patron> getPatron() {
        if (patron == null){
            patron = new ArrayList<>();
        }
        return patron;
    }
}
