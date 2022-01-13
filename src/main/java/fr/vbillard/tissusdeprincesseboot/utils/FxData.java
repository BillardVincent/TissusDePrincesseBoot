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
public class FxData implements Cloneable {

    private History history;

    private PathEnum path;
    private int tissuId;
    private int patronId;
    private int projetId;
    private ObservableList<TissuDto> tissuList;
    private ObservableList<PatronDto> patronList;
    private ObservableList<ProjetDto> projetList;

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

    public void setProjetId(int projetId) {
        this.projetId = projetId;
        history.add(this);

    }

    public void setTissuList(ObservableList<TissuDto> tissuList) {
        this.tissuList = FXCollections.observableList(tissuList);
        history.add(this);
    }
    
    public void setPatronList(ObservableList<PatronDto> patronList) {
        this.patronList = FXCollections.observableList(patronList);
        history.add(this);
    }
    
    public void setProjetList(ObservableList<ProjetDto> projetList) {
        this.projetList = FXCollections.observableList(projetList);
        history.add(this);
    }
    
    
}
