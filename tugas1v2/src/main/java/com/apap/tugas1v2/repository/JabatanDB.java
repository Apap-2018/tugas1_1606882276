package com.apap.tugas1v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1v2.model.JabatanModel;

@Repository
public interface JabatanDB extends JpaRepository<JabatanModel, Long> {
	JabatanModel findById(long Id);

}
