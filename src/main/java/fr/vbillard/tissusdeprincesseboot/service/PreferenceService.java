package fr.vbillard.tissusdeprincesseboot.service;

import fr.vbillard.tissusdeprincesseboot.dao.PreferenceDao;
import fr.vbillard.tissusdeprincesseboot.model.Preference;
import org.springframework.stereotype.Service;

import javax.swing.filechooser.FileSystemView;

@Service
public class PreferenceService {

	private PreferenceDao dao;
	
	public PreferenceService(PreferenceDao dao) {
		this.dao = dao;
	}
	
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
