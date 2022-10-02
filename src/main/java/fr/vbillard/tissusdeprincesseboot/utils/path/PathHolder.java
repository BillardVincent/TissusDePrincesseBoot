package fr.vbillard.tissusdeprincesseboot.utils.path;

import java.net.URL;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PathHolder {
	private URL url;
	private Class controller;
}
