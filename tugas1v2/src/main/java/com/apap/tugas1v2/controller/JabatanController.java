package com.apap.tugas1v2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1v2.model.JabatanModel;
import com.apap.tugas1v2.service.JabatanService;


@Controller
public class JabatanController {
	@Autowired 
	private JabatanService jabatanService;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add (Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "addJabatan";
	}
	
	//pilot itu dibikin pas disubmit di addPilot.html trus disimpen di db lewat yang ini
	//@modelattribute buat ngambil data yang dibikin di addPilot
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		model.addAttribute("message", "Jabatan Berhasil Ditambah!");
		jabatanService.addJabatan(jabatan);
		return "addJabatan";
	}
	
	@RequestMapping("/jabatan/view")
	public String viewJabatan(@RequestParam("idJabatan") long idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanbById(idJabatan);
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String updateJabatan(@RequestParam("id") long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanbById(id);
		model.addAttribute("jabatan", jabatan);
		return "updateJabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatanSubmit(@RequestParam("id") long id, @ModelAttribute JabatanModel jabatanU, Model model) {
		jabatanService.updateJabatan(id, jabatanU);
		model.addAttribute("jabatan", jabatanU);
		model.addAttribute("message", "Berhasil Mengubah Jabatan!");
		return "updateJabatan";
	}
	
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.GET)
	private String deleteJabatan(@RequestParam("id") long id, Model model) {
		if(jabatanService.getJabatanbById(id).getListPegawai().size() > 0) {
			return "hapusJabatanGagal";
		}
		
		else {
			jabatanService.deleteJabatan(jabatanService.getJabatanbById(id));
			return "hapusJabatanBerhasil";
		}
		
//		try {
//			jabatanService.deleteJabatan(jabatanService.getJabatanbById(id));
//			return "hapusJabatanBerhasil";
//		}
//		catch(Exception e) {
//			return "hapusJabatanGagal";
//		}
//		jabatanService.deleteJabatan(jabatanService.getJabatanbById(idJabatan));
////		flightService.deleteFlight(flightService.getFlightById(id));
//		model.addAttribute("message", "Jabatan Berhasil Terhapus");
//		return "hapusJabatanBerhasil";
	}
	
	@RequestMapping(value="/jabatan/viewall", method = RequestMethod.GET)
	private String viewAllJabatan(Model model) {
		List<JabatanModel> listOfJabatan = new ArrayList<>();
		listOfJabatan = jabatanService.listJabatan();
		model.addAttribute("listOfJabatan", listOfJabatan);
		return "viewall";
	}
}
