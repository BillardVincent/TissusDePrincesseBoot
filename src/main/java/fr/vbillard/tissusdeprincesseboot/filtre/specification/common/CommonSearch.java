package fr.vbillard.tissusdeprincesseboot.filtre.specification.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public abstract class CommonSearch<T extends Comparable<? super T>> extends AbstractSearch<T> {

    private T lessThan;

    private T lessThanEqual;

    private T greaterThan;

    private T greaterThanEqual;

    public CommonSearch(T equals) {
        super(equals);
    }
}
