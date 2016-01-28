package basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ietf.jgss.Oid;
import org.junit.Test;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import utils.FileUtil;
import utils.JsonUtil;
import utils.PropertiesUtil;

public class Utils {
	@Test
	public void json(){
		List<Map<String, String>> l=new ArrayList<Map<String,String>>();
		Map<String, String> m=new HashMap<String, String>();
		m.put("1", "tt");
		l.add(m);
		l.add(m);
		String json=JsonUtil.ob2json(l);
		System.out.println(json);
		JsonElement je=JsonUtil.json2je(json);
		JsonArray ja=je.getAsJsonArray();
		for (JsonElement jsonElement : ja) {
			JsonObject jo=jsonElement.getAsJsonObject();
			System.out.print(jo.get("1")+"  ");
			System.out.println();
		}
	}
	enum a {
		BLUE,
		YELLOW};
	enum code{
		SUCCESS("1"),FAIL("0");
		String value;
		code(String i){
			this.value=i;
		}
		public String getValue(){
			return this.value;
		}
		@Override
		public String toString(){
			return this.value;
		}
	}
	@Test
	public void enum_test(){
		for (a c : a.values()) {
			System.out.println(c);
		}
//		ResponseMsg msg=new ResponseMsg(code.SUCCESS, "123", "123");
		code d=code.SUCCESS;
		System.out.println(d);
		Map<String, Object> m=new HashMap<>(); 
		m.put("enum", d);
		System.out.println(JsonUtil.ob2json(m)); 
	}
	@Test
	public void gson(){
		System.out.println(JsonUtil.ob2json(new Date()));
	}
	@Test
	public void properties(){
		System.out.println(PropertiesUtil.getValueD("beauty_pic_dir"));
	}
	@Test
	public void file(){
		String src="E:/seat.xlsx";
		String des="F:/seat.xlsx";
		FileUtil.copy(src, des);
	}
}
