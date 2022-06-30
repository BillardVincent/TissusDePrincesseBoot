package fr.vbillard.tissusdeprincesseboot.utils;

import java.net.URL;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.dtos_fx.PatronDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.ProjetDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuRequisDto;
import fr.vbillard.tissusdeprincesseboot.dtos_fx.TissuVariantDto;
import fr.vbillard.tissusdeprincesseboot.model.TissuUsed;
import lombok.Data;

@Data
@Component
public class FxData {

	private Page page;
	private PathEnum path;
	private TissuDto tissu;
	private PatronDto patron;
	private ProjetDto projet;
	private TissuUsed tissuUsed;
	private TissuRequisDto tissuRequis;
	private TissuVariantDto tissuVariant;
	private int longueurRequise;
	private URL url;
	private List<String> listValues;
	private List<String> listDataCBox;
	private Specification specification;

	public FxData getCopy() {
		try {
			return (FxData) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			throw new RuntimeException("Echec du clonage : " + this.toString());
		}
	}

}
