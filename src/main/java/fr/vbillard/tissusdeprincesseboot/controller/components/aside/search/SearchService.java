package fr.vbillard.tissusdeprincesseboot.controller.components.aside.search;

import fr.vbillard.tissusdeprincesseboot.controller.components.aside.search.controllers.ISearchController;
import fr.vbillard.tissusdeprincesseboot.controller.components.aside.search.controllers.SearchControlerNames;
import fr.vbillard.tissusdeprincesseboot.filtre.specification.FournitureSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SearchService {
    private final Map<String, ISearchController> searchControllerList;

    public SearchService(Map<String, ISearchController> searchControllerList) {
        this.searchControllerList = searchControllerList;
    }

    public SearchComponent getComponent(Specification specification){
        if (specification instanceof FournitureSpecification){
            return searchControllerList.get(SearchControlerNames.FOURNITURE).getSearchComponent(specification);
        }

        return null;
    }
}
