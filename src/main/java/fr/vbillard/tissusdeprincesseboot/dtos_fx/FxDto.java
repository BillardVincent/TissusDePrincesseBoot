package fr.vbillard.tissusdeprincesseboot.dtos_fx;

import fr.vbillard.tissusdeprincesseboot.model.AbstractEntity;

public interface FxDto<T extends AbstractEntity>  {

	int getId();

	void setId(int id);

}
