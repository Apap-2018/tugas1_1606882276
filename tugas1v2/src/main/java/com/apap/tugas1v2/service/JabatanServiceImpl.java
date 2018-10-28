package com.apap.tugas1v2.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1v2.model.JabatanModel;
import com.apap.tugas1v2.repository.JabatanDB;
import com.apap.tugas1v2.service.JabatanService;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDB jabatanDb;
	
	@Override
	public List<JabatanModel> listJabatan() {
		return jabatanDb.findAll();
	}

	@Override
	public JabatanModel getJabatanbById(long id) {
		return jabatanDb.findById(id);
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
		
	}

	@Override
	public void updateJabatan(long id, JabatanModel jabatanv2) {
		JabatanModel jabatanv1 = jabatanDb.findById(id);
		jabatanv1.setNama(jabatanv2.getNama());
		jabatanv1.setDeskripsi(jabatanv2.getDeskripsi());
		jabatanv1.setGaji_pokok(jabatanv2.getGaji_pokok());
		jabatanDb.save(jabatanv1);
		
	}

	@Override
	public void deleteJabatan(JabatanModel jabatan) {
		jabatanDb.delete(jabatan);
		
	}

}
