package fr.vbillard.tissusdeprincesseboot.filtre.specification.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class NumericSearch<T extends Number & Comparable<? super T>>  extends CommonSearch<T> {

    public NumericSearch(T equals) {
        super(equals);
    }
}
