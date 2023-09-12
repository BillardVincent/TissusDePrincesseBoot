package fr.vbillard.tissusdeprincesseboot.controller.utils.path;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URL;

@Data
@AllArgsConstructor
public class PathHolder {
	private URL url;
	private Class controller;
}
