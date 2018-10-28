package com.apap.tugas1v2.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1v2.model.ProvinsiModel;
import com.apap.tugas1v2.repository.ProvinsiDB;
import com.apap.tugas1v2.service.ProvinsiService;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService{
	@Autowired
	private ProvinsiDB provinsiDb;
	
//	@Autowired
//	private ProvinsiModel provinsiModel;
	
	@Override
	public List<ProvinsiModel> listProv() {
		return provinsiDb.findAll();
	}

@Override
public ProvinsiModel getProvinsiById(long id) {
	// TODO Auto-generated method stub
	return provinsiDb.findById(id);
	}
}
