package fr.vbillard.tissusdeprincesseboot.controller.components.aside;

import fr.vbillard.tissusdeprincesseboot.controller.components.aside.search.SearchService;
import fr.vbillard.tissusdeprincesseboot.controller.components.aside.search.controllers.ISearchController;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AsideController {

    private Aside aside;
    private SearchService service;

    public AsideController(SearchService service) {
        this.service = service;
    }

    public void setSearch(Specification specification){
        aside.addSearch(service.getComponent(specification));
    }

    public Aside getAside(){
        if(aside == null){
            aside = new Aside();
        }
        return aside;
    }
}
