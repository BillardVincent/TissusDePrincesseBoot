package fr.vbillard.tissusdeprincesseboot.dao;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import fr.vbillard.tissusdeprincesseboot.model.Preference;

public class PreferenceDao {
	
	private static File file = new File("resources/preferences.yaml");


	public Preference getPreference() {
		Preference pref = new Preference();
		ObjectMapper om = new ObjectMapper(new YAMLFactory());
		
			try {
				pref = om.readValue(file, Preference.class);
			} catch (IOException e) {
				pref.setDataBasePath("");
				pref.setPictureLastUploadPath(FileSystemView.getFileSystemView().getDefaultDirectory().getPath());
				pref.setFirstLaunch(true);
				try {
					om.writeValue(file, pref);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} 

		return pref;
	}
	
	public void savePreferences(Preference pref) {
		ObjectMapper om = new ObjectMapper(new YAMLFactory());

		try {
			om.writeValue(file, pref);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
