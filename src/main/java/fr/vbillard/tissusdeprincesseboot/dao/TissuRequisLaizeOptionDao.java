package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Projet;
import fr.vbillard.tissusdeprincesseboot.model.TissuRequisLaizeOption;
import javafx.beans.property.IntegerProperty;

@Repository
public interface TissuRequisLaizeOptionDao extends Idao<TissuRequisLaizeOption, Integer> {
  List<TissuRequisLaizeOption> getTissuRequisLaizeOptionByRequisId(int id);
}
