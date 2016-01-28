package service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import service.interfaces.IProcessControl;

public class ProcessControl implements IProcessControl{
	public class SubProcess{
		public List<String> list;
		public  SubProcess(List<String> list){
			this.list=list;
		}
		public  SubProcess(String l){
			String[] s=l.split(",");
			this.list=Arrays.asList(s);
		}
		public boolean checkValid(String cur,String after){
			for (int i = 0; i < this.list.size(); i++) {
				String s=this.list.get(i);
				if(s.equals(cur)){
					if((i+1)<this.list.size()&&this.list.get(i+1).equals(after)){
						return true;
					}
				}
			}
			return false;
		}
	} 
	List<SubProcess> list;
	public ProcessControl(List<SubProcess> list) {
		this.list=list;
	}
	public ProcessControl(String[] rules) {
		this.list=new ArrayList<>();
		for (int i = 0; i < rules.length; i++) {
			SubProcess sub=new SubProcess(rules[i]);
			list.add(sub);
		}
	}
	

	@Override
	public boolean isValid(String cur,String aft) {
		for (SubProcess subProcess : list) {
			if(subProcess.checkValid(cur, aft)) return true;
		}
		return false;
	}

}
