package fr.vbillard.tissusdeprincesseboot.services;

import javax.swing.filechooser.FileSystemView;

import org.springframework.stereotype.Service;

import fr.vbillard.tissusdeprincesseboot.dao.PreferenceDao;
import fr.vbillard.tissusdeprincesseboot.model.Preference;

@Service
public class PreferenceService {

	private PreferenceDao dao = new PreferenceDao();
	
	public Preference getPreferences() {
		return dao.getPreference();
	}
	
	public void savePreferences(Preference pref) {
		pref.setDataBasePath(normalizePath(pref.getDataBasePath()));
		pref.setPictureLastUploadPath(normalizePath(pref.getPictureLastUploadPath()));
		dao.savePreferences(pref);
	}
	
	private String normalizePath(String path) {
		if (!path.equals(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()) &&
				path.contains("\\") &&
				!path.contains("\\\\")) {
			path =  path.substring(0, path.lastIndexOf("\\")).replace("\\", "\\\\");
		}
		return path;
	}
}
