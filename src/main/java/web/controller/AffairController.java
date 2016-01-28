package web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import domain.Affair;
import exception.BasicException;
import service.interfaces.IAffairService;
import web.message.ResponseMsg;

@Controller
public class AffairController {
	//一个小的框架
	interface Method{
		public boolean run () throws BasicException;
	}
	public String framework(Method method,String err){
		try {
			if(method.run()){
				return new ResponseMsg(1,"","").toString();
			}else{
				return new ResponseMsg(0,err,"").toString();
			}
		} catch (BasicException e) {
			log.error(e.getMessage());
			return new ResponseMsg(0,e.hint(),"").toString();
		}
	}
	private transient static Log log=LogFactory.getLog(Affair.class);
	@Resource
	private IAffairService affairService;
	
	@RequestMapping(value="/affair/testPage")
	public String testPage(){
		return "/affair/test";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/affair/test",produces = {"text/plain;charset=utf-8"})
	public String test(){
		StringBuffer sb=new StringBuffer();
		List<Affair> list=affairService.getAll();
		for (Affair affair : list) {
			sb.append(affair.getWhat());
		}
		log.info(sb.toString());
		return sb.toString();
	}
	
	@RequestMapping(value="/affair/index")
	public String affairIndex(){
		return "/affair/affair";
	}
	/**
	 * 读取所有的任务，返回json数组
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/affair/getAll",produces = {"text/plain;charset=utf-8"})
	public String getAll(){
		List<Affair> list=affairService.getAll();
		if(list.size()==0){
			return new ResponseMsg(0,"您当前还没有创建任务",null).toString();
		}else{
			return new ResponseMsg(1,null,list).toString();
		}
	}
	/**
	 * 读取部分任务，json数组
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="/affair/getPart",produces = {"text/plain;charset=utf-8"})
	public String getPart(String n){
		return "not implented";
	}

	/**
	 * 创建一个任务，返回成功与否
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/affair/createOne",produces = {"text/plain;charset=utf-8"})
	public String createOne(String what,String why,String how,String type,String comment){
		Affair af=new Affair();
		af.setWhat(what);
		af.setWhy(why);
		af.setHow(how);
		if(type==null) type="其他";
		af.setType(type);
		af.setComment(comment);
		if(affairService.createOne(af)){
			return new ResponseMsg(1,"","").toString();
		}else{
			return new ResponseMsg(0,"创建任务失败","").toString();
		}
	}
	/**
	 * 开始一个任务,返回成功与否
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/affair/startOne",produces = {"text/plain;charset=utf-8"})
	public String startOne(final String id,final String how){
		return framework(new Method(){
			@Override
			public boolean run() throws BasicException {
				return affairService.startOne(Integer.parseInt(id), how);
			}
		}, "开始任务失败");
	}
	/**
	 * 完成一个任务，返回成功与否
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/affair/completeOne",produces = {"text/plain;charset=utf-8"})
	public String completeOne(final String id,final String comment){
		return framework(new Method(){
			@Override
			public boolean run() throws BasicException {
				return affairService.completeOne(Integer.parseInt(id), comment);
			}
		}, "完成任务失败");
	}
	/**
	 * 终止一个任务，返回成功与否
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/affair/terminateOne",produces = {"text/plain;charset=utf-8"})
	public String terminateOne(final String id,final String reason){
		return framework(new Method(){
			@Override
			public boolean run() throws BasicException {
				return affairService.terminateOne(Integer.parseInt(id), reason);
			}
		}, "终止任务失败");
	}
	/**
	 * 查看所有的类型，返回数组
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/affair/getTypes",produces = {"text/plain;charset=utf-8"})
	public String getTypes(){
		Map<String,String>m=affairService.getTypes();
		if(!m.isEmpty()){
			return new ResponseMsg(1,"",m).toString();
		}else{
			return new ResponseMsg(0,"获取类型失败","").toString();
		}
	}
	
	
	
	
	
	
	
	
	
}
