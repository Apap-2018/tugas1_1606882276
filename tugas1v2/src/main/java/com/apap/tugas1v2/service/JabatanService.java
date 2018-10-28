package com.apap.tugas1v2.service;

import java.util.List;

import com.apap.tugas1v2.model.JabatanModel;

public interface JabatanService {
	List<JabatanModel> listJabatan();
	JabatanModel getJabatanbById(long id);
	void addJabatan(JabatanModel jabatan);
	void updateJabatan(long id, JabatanModel jabatan);
	void deleteJabatan(JabatanModel jabatan);
}
