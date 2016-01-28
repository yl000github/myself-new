package web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import utils.JsonUtil;

@Controller
public class TestController {
	private static Log log=LogFactory.getLog(TestController.class);
	@RequestMapping(value="/test/r1")
	public ModelAndView r1(ModelAndView mv){
		log.info(mv.getViewName());
		Map<String,Object> m=mv.getModel();
		log.error(JsonUtil.ob2json(m));
		Map<String,Object> m1=new HashMap<>();
		m1.put("hi", "dddd");
		return new ModelAndView("redirect:/test/r2",m1);
	}
	@RequestMapping(value="/test/r2")
	public ModelAndView r2(ModelAndView mv){
		log.info(mv.getViewName());
		Map<String,Object> m=mv.getModel();
		log.error(JsonUtil.ob2json(m));
		return new ModelAndView("/affair/test");
	}
	@ResponseBody
	@RequestMapping(value="/test/exception")
	public String r3(){
		log.info("info");
		log.warn("warn");
		log.error("error");
		try{
			throw new Exception("一个新的异常");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "hello";
	}
}
