package com.apap.tugas1v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1v2.model.InstansiModel;

@Repository
public interface InstansiDB extends JpaRepository<InstansiModel, Long> {
	InstansiModel findById(long Id);
}
