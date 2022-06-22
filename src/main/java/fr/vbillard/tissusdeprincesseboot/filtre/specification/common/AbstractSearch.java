package fr.vbillard.tissusdeprincesseboot.filtre.specification.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public abstract class AbstractSearch<T extends Comparable<? super T>> {

    private T equals;

    private T notEquals;

    private Boolean isNull;

    protected AbstractSearch(T equals) {
        this.equals = equals;
    }
}
