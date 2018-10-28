package com.apap.tugas1v2.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1v2.model.PegawaiModel;

@Repository
public interface PegawaiDB extends JpaRepository<PegawaiModel, Long> {
	PegawaiModel findById(long Id);
	PegawaiModel findByNip(String nip);
	List<PegawaiModel> findByTahunMasukAndTanggalLahir(String tahunMasuk, Date tanggalLahir);

}
