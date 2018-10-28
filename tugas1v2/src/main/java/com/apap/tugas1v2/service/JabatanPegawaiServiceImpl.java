package com.apap.tugas1v2.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1v2.model.JabatanModel;
import com.apap.tugas1v2.model.JabatanPegawaiModel;
import com.apap.tugas1v2.model.PegawaiModel;
import com.apap.tugas1v2.repository.JabatanPegawaiDB;
import com.apap.tugas1v2.service.JabatanPegawaiService;

@Service
@Transactional
public class JabatanPegawaiServiceImpl implements JabatanPegawaiService {
	@Autowired 
	JabatanPegawaiDB jabatanPegawaiDb;


	@Override
	public void addJabatanPegawai(PegawaiModel pegawai, JabatanModel jabatan) {
		// TODO Auto-generated method stub
		JabatanPegawaiModel baru = new JabatanPegawaiModel();
		baru.setJabatan(jabatan);
		baru.setPegawai(pegawai);
		jabatanPegawaiDb.save(baru);
	}

}
