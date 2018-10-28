package com.apap.tugas1v2.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1v2.model.InstansiModel;
import com.apap.tugas1v2.model.PegawaiModel;
import com.apap.tugas1v2.repository.PegawaiDB;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDB pegawaiDb;
	
	@Override
	public PegawaiModel getPegawaiByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}
	
	@Override
	public String generateNip(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		String Nip = "";
		Nip += pegawai.getInstansi().getId();
		Nip += pegawai.getTanggalLahir().toString().substring(8, 10);
		Nip += pegawai.getTanggalLahir().toString().substring(5, 7);
		Nip += pegawai.getTanggalLahir().toString().substring(2, 4);
		Nip += pegawai.getTahunMasuk();
		
		String lahirDanMasuk = "";
		List<PegawaiModel> lahirDanMasukSama = pegawaiDb.findByTahunMasukAndTanggalLahir(pegawai.getTahunMasuk(), pegawai.getTanggalLahir());
		lahirDanMasukSama.add(pegawai);
		lahirDanMasuk = Integer.toString(lahirDanMasukSama.size());
		
		if(Integer.parseInt(lahirDanMasuk) < 10) {
			lahirDanMasuk = "0" + lahirDanMasuk;
		}
		
		Nip += lahirDanMasuk;
		
		return Nip;
	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
	}

	@Override
	public List<PegawaiModel> getPegawaiTertua(InstansiModel instansi) {
		List<PegawaiModel> listPegawai = new ArrayList<PegawaiModel>();
		listPegawai = instansi.getListPegawai();
		Collections.sort(listPegawai, new Comparator<PegawaiModel>() {
			  public int compare(PegawaiModel o1, PegawaiModel o2) {
				  return o1.getTanggalLahir().compareTo(o2.getTanggalLahir());
			  }
			}
		);		
		return listPegawai;
	}

	@Override
	public void updatePegawai(String nip, PegawaiModel pegawaiv2) {
		PegawaiModel pegawaiv1 = pegawaiDb.findByNip(nip);
		pegawaiv1.setNama(pegawaiv2.getNama());
		pegawaiv1.setTempat_lahir(pegawaiv2.getTempat_lahir());
		pegawaiv1.setTanggalLahir(pegawaiv2.getTanggalLahir());
		pegawaiv1.setTahunMasuk(pegawaiv2.getTahunMasuk());
		pegawaiv1.setInstansi(pegawaiv2.getInstansi());
		pegawaiv1.setListJabatan(pegawaiv2.getListJabatan());
		System.out.println(pegawaiv2.getInstansi().getId());
		pegawaiv1.setNip(this.generateNip(pegawaiv2));
	}
}
