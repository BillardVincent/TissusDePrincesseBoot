package fr.vbillard.tissusdeprincesseboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class RangementRoot extends AbstractRangement{

    @OneToMany(mappedBy = "conteneurRoot")
    private List<Rangement> subdivision;

    public List<Rangement> getSubdivision() {
        if (subdivision == null){
            subdivision = new ArrayList<>();
        }
        return subdivision;
    }
}
