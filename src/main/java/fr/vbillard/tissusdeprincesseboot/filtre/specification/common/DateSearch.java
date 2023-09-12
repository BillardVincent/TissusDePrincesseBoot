package fr.vbillard.tissusdeprincesseboot.filtre.specification.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Setter
public class DateSearch extends CommonSearch<LocalDate> {

    public DateSearch(LocalDate equals) {
        super(equals);
    }
}
