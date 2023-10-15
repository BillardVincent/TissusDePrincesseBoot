package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import javafx.beans.property.*;

public class RangementDto {
    private IntegerProperty id;
    private IntegerProperty rang;
    private StringProperty nom;
    private TypeRangement type;


    public RangementDto() {
        this.id = new SimpleIntegerProperty();
        this.rang = new SimpleIntegerProperty();
        this.nom = new SimpleStringProperty();
    }

    public RangementDto(int rank, String nom) {
        this();
        setRang(rank);
        setNom(nom);
    }

    @Override
    public String toString() {
        return nom.get();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty getIdProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getRang() {
        return rang.get();
    }

    public IntegerProperty getRangProperty() {
        return rang;
    }

    public void setRang(int id) {
        this.rang.set(id);
    }

    public String getNom() {
        return nom.get();
    }

    public StringProperty getNomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public void setType(TypeRangement type) {
        this.type = type;
    }

    public TypeRangement getType() {
        return type;
    }

}
