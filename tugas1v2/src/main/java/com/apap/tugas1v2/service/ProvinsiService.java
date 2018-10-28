package com.apap.tugas1v2.service;

import java.util.List;

import com.apap.tugas1v2.model.ProvinsiModel;

public interface ProvinsiService {
	List<ProvinsiModel> listProv();
	ProvinsiModel getProvinsiById(long id);
}
