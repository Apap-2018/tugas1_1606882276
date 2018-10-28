package com.apap.tugas1v2.service;

import java.util.List;

import com.apap.tugas1v2.model.InstansiModel;
import com.apap.tugas1v2.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiByNip(String nip);
	String generateNip(PegawaiModel pegawai);
	void addPegawai(PegawaiModel pilot);
	void updatePegawai(String nip, PegawaiModel pegawaiv2);
	List<PegawaiModel> getPegawaiTertua(InstansiModel instansi);
}
