package fr.vbillard.tissusdeprincesseboot.filtre.specification.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
public class DateTimeSearch extends CommonSearch<LocalDateTime> {

    public DateTimeSearch(LocalDateTime equals) {
        super(equals);
    }
}
