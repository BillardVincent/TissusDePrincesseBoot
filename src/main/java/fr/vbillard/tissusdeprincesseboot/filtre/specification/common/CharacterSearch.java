package fr.vbillard.tissusdeprincesseboot.filtre.specification.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class CharacterSearch extends AbstractSearch<String> {

    private String startsWith;

    private String endsWith;

    private String contains;

    private String notContains;

    private Boolean ignoreCase;

    public CharacterSearch(String equals) {
        super(equals);
    }

    @Override
    public String getEquals() {
        return applyIgnoreCase(super.getEquals());
    }

    @Override
    public String getNotEquals() {
        return applyIgnoreCase(super.getNotEquals());
    }

    public String getStartsWith() {
        return applyIgnoreCase(startsWith);
    }

    public String getEndsWith() {
        return applyIgnoreCase(endsWith);
    }

    public String getContains() {
        return applyIgnoreCase(contains);
    }

    public String getNotContains() {
        return applyIgnoreCase(notContains);
    }

    public boolean getIgnoreCase() {
        return Boolean.TRUE.equals(ignoreCase);
    }

    private String applyIgnoreCase(String str) {
        return getIgnoreCase() && str != null ? str.toLowerCase() : str;
    }
}
