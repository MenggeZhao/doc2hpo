package edu.columbia.dbmi.doc2hpo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.columbia.dbmi.doc2hpo.pojo.ParseJob;
import edu.columbia.dbmi.doc2hpo.pojo.ParsingResults;
import edu.columbia.dbmi.doc2hpo.service.ACTrieParser;
import edu.columbia.dbmi.doc2hpo.service.MetaMapParser;
import edu.columbia.dbmi.doc2hpo.service.NcboParser;
import edu.columbia.dbmi.doc2hpo.util.Obo;

@Controller
@RequestMapping("/parse")
public class ParseController {

	private MetaMapParser mmp;
	private NcboParser ncbo;
	private ACTrieParser actp;

	@Value("#{configProperties['MetamapBinPath']}")
	private String metamapBinPath;

	@Value("#{configProperties['NcboApiKey']}")
	private String NcboApiKey;
	
	@Value("#{configProperties['Proxy']}")
	private String proxy;
	
	@Value("#{configProperties['Port']}")
	private int port;

	@PostConstruct
	public void init() {
		this.mmp = new MetaMapParser();
		this.actp = new ACTrieParser();
		this.ncbo = new NcboParser(NcboApiKey,proxy,port);
	}

	@RequestMapping("/acdat")
	@ResponseBody
	public Map<String, Object> getTerm2(HttpSession httpSession, @RequestBody ParseJob pj) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ParsingResults> hmName2Id = new ArrayList<ParsingResults>();
		String content = pj.getNote();
		
		hmName2Id = this.actp.parse(this.actp, content);
		
		httpSession.setAttribute("hmName2Id", hmName2Id);
		map.put("hmName2Id", hmName2Id);
		map.put("hpoOption", false);
		return map;
	}

	@RequestMapping("/ncbo")
	@ResponseBody
	public Map<String, Object> getTerm3(HttpSession httpSession, @RequestBody ParseJob pj) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ParsingResults> hmName2Id = new ArrayList<ParsingResults>();
		List<String> theOptions = pj.getOption();
		String content = pj.getNote();
		
		hmName2Id = this.ncbo.parse(content, theOptions);
		
		httpSession.setAttribute("hmName2Id", hmName2Id);
		map.put("hmName2Id", hmName2Id);
		map.put("hpoOption", false);
		return map;
	}

	@RequestMapping("/metamap")
	@ResponseBody
	public Map<String, Object> getTerm1(HttpSession httpSession, @RequestBody ParseJob pj) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ParsingResults> hmName2Id = new ArrayList<ParsingResults>();
		List<String> theOptions = pj.getOption();
		String content = pj.getNote();
		
		hmName2Id = this.mmp.parse(content, theOptions);
		
		httpSession.setAttribute("hmName2Id", hmName2Id);
		map.put("hmName2Id", hmName2Id);
		map.put("hpoOption", false);
		return map;
	}
	
	
//	@RequestMapping("/metamap2")
//	@ResponseBody
//	public Map<String, Object> getTerm(HttpSession httpSession, @RequestBody ParseJob pj) throws Exception {
//		Map<String, Object> map = new HashMap<String, Object>();
//		List<ParsingResults> hmName2Id = new ArrayList<ParsingResults>();
//		String content = pj.getNote();
//		
//		
//		HashMap<String, String> hmCui2Hpo = this.o.hmCui2Hpo;
//		HashMap<String, String> hmHpo2Name = this.o.hmHpo2Name;
//
//		System.out.println("mmp path +++++: " + metamapBinPath);
//
//		// get hpo options
//		List<String> theOptions = pj.getOption();
//		// System.out.println("Metamap Options:");
//		// System.out.println(theOptions);
//		String note = pj.getNote();
//		Boolean hpoOption = pj.getMmpgeneral().getHo();
//
//		String mmpResult = this.mmp.runCmdMetamap(note, theOptions);
//		HashMap<String, String> hmCui = this.mmp.extractCui(mmpResult);
//		Map<String, Object> map = new HashMap<String, Object>();
//		Map<String, String> hmName2Id = new HashMap<String, String>();
//		for (String cuiId : hmCui.keySet()) {
//			if (hpoOption == true) {
//				if (hmCui2Hpo.get(cuiId) != null) {
//					// get hpo id and name.
//					String hpoIdStr = hmCui2Hpo.get(cuiId);
//					// in case one CUI mapping to multiple HPO.
//					String[] hpoIdList = hpoIdStr.split("\\|");
//					for (String hid : hpoIdList) {
//						String hpoName = hmHpo2Name.get(hid);
//						if (hpoName != null) {
//							hmName2Id.put(hpoName, hid);
//						} else {
//							System.out.println(hid + "not found in hmHpo2Name");
//						}
//
//					}
//				} else {
//					System.out.println(cuiId + "not found in hmCui2Hpo");
//				}
//			} else {
//				String cuiName = hmCui.get(cuiId);
//				hmName2Id.put(cuiName, cuiId);
//			}
//		}
//		httpSession.setAttribute("hmName2Id", hmName2Id);
//		map.put("hmName2Id", hmName2Id);
//		map.put("hpoOption", hpoOption);
//		return map;
//	}
}
