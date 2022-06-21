package fr.vbillard.tissusdeprincesseboot.dao;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import fr.vbillard.tissusdeprincesseboot.model.Preference;

@Component
public class PreferenceDao {

	private static File file = new File("preferences.yaml");

	public Preference getPreference() {
		Preference pref = new Preference();
		ObjectMapper om = new ObjectMapper(new YAMLFactory());

		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			pref = om.readValue(file, Preference.class);
		} catch (IOException e) {
			e.printStackTrace();
			// throw new RuntimeException("Erreur de lecture du fichier préferences");
			pref.setDataBasePath("");
			pref.setPictureLastUploadPath(FileSystemView.getFileSystemView().getDefaultDirectory().getPath());
			pref.setFirstLaunch(true);
			try {
				om.writeValue(file, pref);
			} catch (IOException e1) {
				e1.printStackTrace();
				// throw new RuntimeException("Erreur d'ecriture du fichier préferences");
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
