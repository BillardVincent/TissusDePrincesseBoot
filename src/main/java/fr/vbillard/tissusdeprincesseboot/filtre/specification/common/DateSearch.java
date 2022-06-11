package fr.vbillard.tissusdeprincesseboot.filtre.specification.common;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class DateSearch extends CommonSearch<LocalDate> {

    public DateSearch(LocalDate equals) {
        super(equals);
    }
}
