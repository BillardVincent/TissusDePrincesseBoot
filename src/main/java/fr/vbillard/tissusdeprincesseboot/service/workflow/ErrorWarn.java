package fr.vbillard.tissusdeprincesseboot.service.workflow;

import java.util.ArrayList;
import java.util.List;

public class ErrorWarn {
	
	private List<String> error = new ArrayList();
	private List<String> warn = new ArrayList();
	
	public List<String> getError() {
		return error;
	}
	
	public List<String> getWarn() {
		return warn;
	}
	
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
