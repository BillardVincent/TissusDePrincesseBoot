package fr.vbillard.tissusdeprincesseboot.controller.components.aside.search.controllers;

import fr.vbillard.tissusdeprincesseboot.controller.components.aside.search.CaracChkBox;
import fr.vbillard.tissusdeprincesseboot.controller.components.aside.search.FournitureSearchComponent;
import fr.vbillard.tissusdeprincesseboot.controller.components.aside.search.SearchComponent;
import fr.vbillard.tissusdeprincesseboot.model.Fourniture;
import fr.vbillard.tissusdeprincesseboot.service.TypeFournitureService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(SearchControlerNames.FOURNITURE)
@AllArgsConstructor
public class FournitureSearchController implements ISearchController<Fourniture>{

    private final TypeFournitureService typeFournitureService;

    @Override
    public SearchComponent<Fourniture> getSearchComponent(Specification specification) {
        FournitureSearchComponent component =  new FournitureSearchComponent(specification);
        component.setCaracCheckBox(new CaracChkBox("Type", typeFournitureService.getAllValues(), new ArrayList<>(), true));
        return component;
    }
}
