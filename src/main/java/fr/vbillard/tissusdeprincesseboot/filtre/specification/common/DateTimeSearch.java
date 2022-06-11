package fr.vbillard.tissusdeprincesseboot.filtre.specification.common;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class DateTimeSearch extends CommonSearch<LocalDateTime> {

    public DateTimeSearch(LocalDateTime equals) {
        super(equals);
    }
}
