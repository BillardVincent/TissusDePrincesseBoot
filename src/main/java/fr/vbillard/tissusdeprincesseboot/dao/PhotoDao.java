package fr.vbillard.tissusdeprincesseboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.vbillard.tissusdeprincesseboot.model.Photo;
import fr.vbillard.tissusdeprincesseboot.model.Tissu;

@Repository
public interface PhotoDao extends JpaRepository<Photo, Integer> {

	 List<Photo> getAllByTissu(Tissu tissu);

	Photo getByTissu(Tissu tissu);

}
