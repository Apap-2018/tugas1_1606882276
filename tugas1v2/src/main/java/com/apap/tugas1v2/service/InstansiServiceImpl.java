package com.apap.tugas1v2.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1v2.model.InstansiModel;
import com.apap.tugas1v2.repository.InstansiDB;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{
	@Autowired
	private InstansiDB instansiDb;
	
	@Override
	public List<InstansiModel> listInstansi() {
		// TODO Auto-generated method stub
		return instansiDb.findAll();
	}

	@Override
	public InstansiModel getInstansiById(long id) {
		return instansiDb.findById(id);
	}
}
