package fr.vbillard.tissusdeprincesseboot.utils;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.dtosFx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtosFx.TissuDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

@Getter
@Component
public class FxData  {

    private History history;

    private PathEnum path;
    private int tissuId;
    private int patronId;
    private int projetId;
    private int tissuUsedId;
    private int tissuRequisId;
    private int tissuVariantId;
    

    public FxData(History history){
        this.history = history;
    }

    public FxData getCopy() {
        try {
            return (FxData) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException("Echec du clonage : " + this.toString());
        }
    }

    public void setPath(PathEnum path) {
        this.path = path;
        history.add(this);
    }

    public void setTissuId(int tissuId) {
        this.tissuId = tissuId;
        history.add(this);

    }

    public void setPatronId(int patronId) {
        this.patronId = patronId;
        history.add(this);

    }

    public void setTissuUsedId(int tissuUsedId) {
        this.tissuUsedId = tissuUsedId;
        history.add(this);

    }
    
    public void setProjetId(int projetId) {
        this.projetId = projetId;
        history.add(this);

    }
    
    public void setTissuRequisId(int tissuRequisId) {
        this.tissuRequisId = tissuRequisId;
        history.add(this);

    }
    
    public void setTissuVariantId(int tissuVariantId) {
        this.tissuVariantId = tissuVariantId;
        history.add(this);

    }
  
    
}
