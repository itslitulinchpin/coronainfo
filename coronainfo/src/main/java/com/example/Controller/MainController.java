package com.example.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Entity.Youtuber;
import com.example.Service.JDBC_FillMatrix;
import com.example.Service.JDBC_IdToTag;
import com.example.Service.JDBC_TagSize;
import com.example.Service.JDBC_YoutuberSize;
import com.example.Service.Youtuber_db;


@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class MainController {

	private Youtuber_db youtuber_db;
	private JDBC_IdToTag idToTag;
	private JDBC_TagSize jdbc_TagSize;
	private JDBC_YoutuberSize jdbc_YoutuberSize;
	private JDBC_FillMatrix fillMatrix;
	int[] tag_list;
	int[][] youtuber_list;
	String search;
	String choosed_youtuber_name;
	@Autowired
	public MainController(Youtuber_db youtuber_db, JDBC_IdToTag idToTag, JDBC_TagSize jdbc_TagSize, JDBC_YoutuberSize jdbc_YoutuberSize, JDBC_FillMatrix fillMatrix) {
		this.youtuber_db = youtuber_db;
		this.idToTag = idToTag;
		this.jdbc_TagSize = jdbc_TagSize;
		this.jdbc_YoutuberSize = jdbc_YoutuberSize;
		this.fillMatrix = fillMatrix;
	}

	/*
	 * @RequestMapping("/home") public String
	 * home(@RequestParam(name="searching_youtuber", defaultValue = "gamst") String
	 * search, Model model) throws ClassNotFoundException, SQLException {
	 * ArrayList<Youtuber> youtuber = youtuber_db.getList(search); // data ��������
	 * 
	 * 
	 * if(youtuber.isEmpty()) { model.addAttribute("youtuber_id", "nothing"); //
	 * data�� ���� �� nothing ������� } else { model.addAttribute("youtuber_id",
	 * youtuber.get(0).id); model.addAttribute("youtuber_image",
	 * youtuber.get(0).image);
	 * model.addAttribute("youtuber_kor_name",youtuber.get(0).kor_name);
	 * model.addAttribute("youtuber", youtuber); } return "home"; }
	 */

	@RequestMapping("/home")
	public String home() throws ClassNotFoundException, SQLException {
		int col = jdbc_YoutuberSize.find_youtubersize();
		int row = jdbc_TagSize.find_tagsize();
		System.out.print(col+" "+row);
		//youtuber_list = fillMatrix.fill_matrix(col, row);
		tag_list = new int[row];
		return "home";
	}
	
	@RequestMapping(value="/search_youtuber", method=RequestMethod.POST)
	public Map<String,Object> search_youtuber(@RequestBody Map<String,Object> map) throws ClassNotFoundException, SQLException {
		search = (String) map.get("name");
		return map;
	}  
	
	@RequestMapping(value="/choose_youtuber", method=RequestMethod.POST)
	public Map<String,Object> choose_youtuber(@RequestBody Map<String,Object> map) throws ClassNotFoundException, SQLException {
		choosed_youtuber_name = (String) map.get("name");
		ArrayList<Integer> current_tag_list= idToTag.fun_idtotag(choosed_youtuber_name);
		
		for(int i = 0 ; i < current_tag_list.size() ;i++) {
			tag_list[current_tag_list.get(i)] += 1;
		}
		
		return map;
	}  
	
	@ResponseBody
	   @RequestMapping(value="/searched_result_youtuber", method=RequestMethod.POST)
	   public HashMap<String, JSONObject> searched_result_youtuber() throws ClassNotFoundException, SQLException {
	      
	      ArrayList<Youtuber> youtuber = youtuber_db.getList(search);
	      HashMap<String, JSONObject> jsonall = new HashMap<>();
	      
	      for(int i = 0 ; i < youtuber.size() ; i++) {
	         String id = youtuber.get(i).id;
	         if(jsonall.containsKey(id)) {

	            JSONObject exist_json = jsonall.get(id); //���� �����ϴ� object �ޱ�

	            JSONArray exist_jsonarr = (JSONArray) exist_json.get("tag"); //object�ȿ� �ִ� array�ޱ�
	            exist_jsonarr.add(youtuber.get(i).tag); //array�� ���ο� �±� �� �߰�
	            //System.out.print(exist_jsonarr);
	         }
	         else {
	         
	        	 
	        	JSONObject json = new JSONObject();
	            JSONArray jsonarray = new JSONArray();
	            
	            json.put("id", id);
	            json.put("image", youtuber.get(i).image);
	            jsonarray.add(youtuber.get(i).tag);
	            json.put("tag", jsonarray);
	            json.put("kor_name", youtuber.get(i).kor_name);
	            json.put("id_num", youtuber.get(i).id_num);
	            jsonall.put(id, json);            
	         }
	      }
	            
	      return jsonall;
	   }
	
	
	
}