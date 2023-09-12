package fr.vbillard.tissusdeprincesseboot.dao;

import fr.vbillard.tissusdeprincesseboot.model.PatronVersion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatronVersionDao extends Idao<PatronVersion, Integer> {

  List<PatronVersion> getByPatronId(int id);
}
