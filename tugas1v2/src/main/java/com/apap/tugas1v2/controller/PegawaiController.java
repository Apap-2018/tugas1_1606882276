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
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1v2.service.InstansiService;
import com.apap.tugas1v2.service.JabatanPegawaiService;
import com.apap.tugas1v2.service.JabatanService;
import com.apap.tugas1v2.service.ProvinsiService;
import com.apap.tugas1v2.model.InstansiModel;
import com.apap.tugas1v2.model.ProvinsiModel;
import com.apap.tugas1v2.model.JabatanModel;
import com.apap.tugas1v2.model.JabatanPegawaiModel;
import com.apap.tugas1v2.model.PegawaiModel;
import com.apap.tugas1v2.service.PegawaiService;


@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired 
	private ProvinsiService provinsiService;
	
	@Autowired 
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.listJabatan();
		List<InstansiModel> listInstansi = instansiService.listInstansi();
		List<ProvinsiModel> listProv = provinsiService.listProv();
		
		List<String> listNamaInstansi = new ArrayList<>();
		
		for(int i = 0; i < listProv.size(); i++) {
			List<InstansiModel> listInst = listProv.get(i).getListInstansi();
			for(int j = 0; j < listInst.size(); j++) {
				String namaInst = listInst.get(j).getNama();
				listNamaInstansi.add(namaInst + " - " + listProv.get(i).getNama());
			}
		}
		
//		for(String LNI : listNamaInstansi) {
//			System.out.println(LNI);
//		}
		
		model.addAttribute("listProv", listProv);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listNamaInstansi", listNamaInstansi);
		return "home";
	}
	
	@RequestMapping(value = "/pegawai/", method = RequestMethod.GET)
	public String viewPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip);	
		List<JabatanModel> listOfJabatanPegawai = pegawai.getListJabatan();
		ArrayList<String> jabatannya = new ArrayList<>();
		for(int i = 0; i < listOfJabatanPegawai.size(); i++) {
			jabatannya.add(listOfJabatanPegawai.get(i).getNama());
			
		}
		
		ArrayList<Double> gajinya = new ArrayList<>();
		for(int i = 0; i < listOfJabatanPegawai.size(); i++) {
			gajinya.add(listOfJabatanPegawai.get(i).getGaji_pokok() + (listOfJabatanPegawai.get(i).getGaji_pokok() * pegawai.getInstansi().getProvinsi().getPresentase_tunjangan()/100));
			//System.out.println(listOfJabatanPegawai.get(i).getJabatan().getGaji_pokok());
			//System.out.println(pegawai.getInstansi().getProvinsi().getPresentase_tunjangan());
			//System.out.println((listOfJabatanPegawai.get(i).getJabatan().getGaji_pokok() * pegawai.getInstansi().getProvinsi().getPresentase_tunjangan()));
		}
		
		double tempGaji = 0;
		for(int i = 0; i < gajinya.size(); i++) {
			if(gajinya.get(i) > tempGaji) {
				tempGaji=gajinya.get(i);
			}
		}
		int salary = (int) tempGaji;
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jabatannya", jabatannya);
		model.addAttribute("salary", salary);
		return "view-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String add (Model model) {
		List<JabatanModel> listJabatan = jabatanService.listJabatan();
		model.addAttribute("pegawai", new PegawaiModel());
		ArrayList<ProvinsiModel> listOfProv = (ArrayList<ProvinsiModel>) provinsiService.listProv();
		ArrayList<String> provNames = new ArrayList<>();
		for(ProvinsiModel prov : listOfProv) {
			provNames.add(prov.getNama());
		}
		model.addAttribute("provNames", provNames);
		model.addAttribute("listOfProv", listOfProv);
//		ArrayList<String> listJabatan = new ArrayList<>();
//		for(int i = 0; i < jabatanDb)
		
		ArrayList<String> instNames = new ArrayList<>();
		for(ProvinsiModel inst : listOfProv) {
			List<InstansiModel> listInstansi = inst.getListInstansi();
			for(InstansiModel inst2 : listInstansi) {
				instNames.add(inst2.getNama());
			}
		}
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("instNames", instNames);
		return "addPegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		System.out.println(pegawaiService.generateNip(pegawai));
		System.out.println(pegawai.getTanggalLahir().toString());
		String nipnya = pegawaiService.generateNip(pegawai);
		pegawai.setNip(nipnya);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("nip", nipnya);
		return "suksesTambah";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
    private String update(@RequestParam("nip") String nip, Model model) {
        PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip);

        
        List<JabatanModel> listJabatan = jabatanService.listJabatan();
		model.addAttribute("pegawai", new PegawaiModel());
		ArrayList<ProvinsiModel> listOfProv = (ArrayList<ProvinsiModel>) provinsiService.listProv();
		ArrayList<String> provNames = new ArrayList<>();
		for(ProvinsiModel prov : listOfProv) {
			provNames.add(prov.getNama());
		}
		model.addAttribute("provNames", provNames);
		model.addAttribute("listOfProv", listOfProv);
//		ArrayList<String> listJabatan = new ArrayList<>();
//		for(int i = 0; i < jabatanDb)
		
		ArrayList<String> instNames = new ArrayList<>();
		for(ProvinsiModel inst : listOfProv) {
			List<InstansiModel> listInstansi = inst.getListInstansi();
			for(InstansiModel inst2 : listInstansi) {
				instNames.add(inst2.getNama());
			}
		}
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("instNames", instNames);
        model.addAttribute("pegawai", pegawai);
        return "updatePegawai";
    }
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	private String updatePegawaiSubmit(@RequestParam("nip") String nip, @ModelAttribute PegawaiModel pegawaiU, Model model) {
		System.out.println(pegawaiU.getNama());
		pegawaiService.updatePegawai(nip, pegawaiU);
		String nipLama = pegawaiU.getNip();
		model.addAttribute("nipLama", nipLama);
		String nipBaru = pegawaiService.generateNip(pegawaiU);
		model.addAttribute("nipBaru", nipBaru);
		return "suksesUpdate";
	}
	
	@RequestMapping(value = "/pegawai/instansi", method = RequestMethod.GET)
	@ResponseBody
	public List<InstansiModel> findAllInstansi(@RequestParam(value="provId", required = true) long provId){
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA");
		ProvinsiModel prov = provinsiService.getProvinsiById(provId);
		return prov.getListInstansi();
	}
	
	@RequestMapping(value="/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String viewTertuaTermuda(@RequestParam("idInstansi") long idInstansi, Model model) {
		InstansiModel instansi = instansiService.getInstansiById(idInstansi);
		List<PegawaiModel> sorted = pegawaiService.getPegawaiTertua(instansi);
//		System.out.println(sorted.get(0).getNama()); //tertua
//		System.out.println(sorted.get(sorted.size()-1).getNama()); //termuda
		PegawaiModel tertua = sorted.get(0);
		PegawaiModel termuda = sorted.get(sorted.size()-1);
		model.addAttribute("tertua", tertua);
		model.addAttribute("termuda", termuda);
		
		List<JabatanModel> listJabatanTermuda = termuda.getListJabatan();
		ArrayList<String> jabatannyaTermuda = new ArrayList<>();
		for(int i = 0; i < listJabatanTermuda.size(); i++) {
			jabatannyaTermuda.add(listJabatanTermuda.get(i).getNama());
		}
		model.addAttribute("jabatannyaTermuda", jabatannyaTermuda);
		
		List<JabatanModel> listJabatanTertua = tertua.getListJabatan();
		ArrayList<String> jabatannyaTertua = new ArrayList<>();
		for(int i = 0; i < listJabatanTertua.size(); i++) {
			jabatannyaTertua.add(listJabatanTertua.get(i).getNama());
		}
		model.addAttribute("jabatannyaTertua", jabatannyaTertua);
		
		return "tertua-termuda";
	}
	
	@RequestMapping(value="/pegawai/cari", method = RequestMethod.GET)
	private String cariPegawai(@RequestParam(value = "idProvinsi", required = false) String idProvinsi, @RequestParam(value = "idInstansi", required = false) String idInstansi, @RequestParam(value = "idJabatan", required = false) String idJabatan ,Model model) {
		List<ProvinsiModel> listProvinsi = provinsiService.listProv();
		List<InstansiModel> listInstansi = instansiService.listInstansi();
		List<JabatanModel> listJabatan = jabatanService.listJabatan();
		
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		
		List<PegawaiModel> emp = new ArrayList<PegawaiModel>();
		
		if(idProvinsi != null || idInstansi != null || idJabatan != null) {
			System.out.println("MASUK SINI");
			if(idProvinsi != null && idInstansi == null && idJabatan == null) {
				System.out.println("PROV DOANG");
				ProvinsiModel prov = provinsiService.getProvinsiById(Long.parseLong(idProvinsi));
				List <InstansiModel> inst = prov.getListInstansi();
				for (InstansiModel i: inst) {
					for (PegawaiModel pegawai : i.getListPegawai()) {
						emp.add(pegawai);
					}
				}
			}
			
			if(idProvinsi == null && idInstansi != null && idJabatan == null) {
				System.out.println("INST DOANG");
				InstansiModel inst = instansiService.getInstansiById(Long.parseLong(idInstansi));
				for(PegawaiModel i : inst.getListPegawai()){
					emp.add(i);
				}
			}
			
			if(idProvinsi != null && idInstansi != null && idJabatan == null) {
				System.out.println("PROV + INSTANSI");
				InstansiModel inst = instansiService.getInstansiById(Long.parseLong(idInstansi));
				for(PegawaiModel i : inst.getListPegawai()){
					emp.add(i);
				}
			}
			
			if(idProvinsi == null && idInstansi == null && idJabatan != null) {
				System.out.println("JABATAN DOANG");
				JabatanModel jabatan = jabatanService.getJabatanbById(Long.parseLong(idJabatan));
				for(PegawaiModel i : jabatan.getListPegawai()){
					emp.add(i);
				}
			}
			
			if(idProvinsi != null && idInstansi == null && idJabatan != null) {
				System.out.println("PROV + JABATAN");
				JabatanModel jabatan = jabatanService.getJabatanbById(Long.parseLong(idJabatan));
				for(PegawaiModel i : jabatan.getListPegawai()){
					if(i.getInstansi().getProvinsi().getId() == Long.parseLong(idProvinsi)) {
						emp.add(i);
					}
				}
				
//				for(int i = 0; i < emp.size(); i++) {
//					if(emp.get(i).getInstansi().getProvinsi().getId() != Long.parseLong(idProvinsi)) {
//						emp.remove(emp.get(i));
//						System.out.println("DIREMOVE");
//					}
//				}
			}
			
			if(idProvinsi == null && idInstansi != null && idJabatan != null) {
				System.out.println("INSTANSI + JABATAN");
				JabatanModel jabatan = jabatanService.getJabatanbById(Long.parseLong(idJabatan));
				for(PegawaiModel i : jabatan.getListPegawai()){
					if(i.getInstansi().getId() == Long.parseLong(idInstansi)) {
						emp.add(i);
					}
						
					}
			}
			
			if(idProvinsi != null && idInstansi != null && idJabatan != null) {
				System.out.println("SEMUANYA");
				JabatanModel jabatan = jabatanService.getJabatanbById(Long.parseLong(idJabatan));
				for(PegawaiModel i : jabatan.getListPegawai()){
					if(i.getInstansi().getId() == Long.parseLong(idInstansi) && i.getInstansi().getProvinsi().getId() == Long.parseLong(idProvinsi)) {
						emp.add(i);
					}
						
					}
			}
		}
		
		model.addAttribute("emp", emp);
		return "cariPegawai";
	}
	
}
