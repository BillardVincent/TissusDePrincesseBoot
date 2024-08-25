package fr.vbillard.tissusdeprincesseboot.controller.components.aside.search.controllers;

import fr.vbillard.tissusdeprincesseboot.controller.components.aside.search.SearchComponent;
import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import org.springframework.data.jpa.domain.Specification;

public interface ISearchController<T> {

    public SearchComponent<T> getSearchComponent(Specification specification);
}
