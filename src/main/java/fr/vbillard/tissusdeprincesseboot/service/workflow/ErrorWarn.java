package fr.vbillard.tissusdeprincesseboot.service.workflow;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorWarn {
	
	private final List<String> error = new ArrayList<>();
	private final List<String> warn = new ArrayList<>();

	public void addError(String e) {
		error.add(e);
	}
	
	public void addWarn(String e) {
		warn.add(e);
	}
	
	public void merge(ErrorWarn errorWarn) {
		error.addAll(errorWarn.getError());
		warn.addAll(errorWarn.getWarn());
	}

}
