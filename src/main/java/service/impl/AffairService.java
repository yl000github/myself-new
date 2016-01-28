package service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.AffairMapper;
import domain.Affair;
import exception.BasicException;
import service.interfaces.IAffairService;
import service.interfaces.IProcessControl;
import utils.DateUtil;
import utils.StringUtil;
@Service
public class AffairService implements IAffairService{
	public final static String STATUS_CREATE="0";
	public final static String STATUS_START="1";
	public final static String STATUS_COMPLETE="2";
	public final static String STATUS_TERMINATE="3";
	public final static String[] types=new String[]{
		"编程","人际","游戏","旅游","其他"	
	};
	private IProcessControl processControl;
	@Autowired 
	private AffairMapper affairDao;
	public List<Affair> getAll() { 
		return affairDao.selectAll();
	}
	public AffairService() {
		String [] rules=new String[]{
			"0,1,2","0,1,3","0,3"	
		};
		processControl=new ProcessControl(rules);
	}
	@Override
	public boolean createOne(Affair affair) {
		affair.setCreateTime(DateUtil.getNow());
		if(!StringUtil.checkValid(affair.getWhy())){
			affair.setWhy("原因还是很重要的");
		}
		affair.setStatus(STATUS_CREATE);
		affairDao.insert(affair);
		return true;
	}
	@Override
	public boolean startOne(int id, String how) throws BasicException {
		Affair af=affairDao.selectByPrimaryKey(id);
		if(!processControl.isValid(af.getStatus(), STATUS_START)) throw new BasicException("无法开始");
		af.setStatus(STATUS_START);
		if(!StringUtil.checkValid(how)) how="尚未找到合适的方法";
		af.setHow(how);
		af.setStartTime(DateUtil.getNow());
		return affairDao.updateByPrimaryKeySelective(af)>0;
	}
	@Override
	public boolean completeOne(int id, String comment) throws BasicException {
		Affair af=affairDao.selectByPrimaryKey(id);
		if(!processControl.isValid(af.getStatus(), STATUS_COMPLETE)) throw new BasicException("无法完成");
		af.setStatus(STATUS_COMPLETE);
		if(!StringUtil.checkValid(comment)) comment="尚未分享评价";
		af.setComment(comment);
		Date startDate=af.getStartTime();
		Date endDate=new Date();
		af.setDoneTime(endDate);
		String duration=DateUtil.diffString(startDate, endDate);
		af.setDuration(duration);
		return affairDao.updateByPrimaryKeySelective(af)>0;
	}
	@Override
	public boolean terminateOne(int id, String reason) throws BasicException {
		Affair af=affairDao.selectByPrimaryKey(id);
		if(!processControl.isValid(af.getStatus(), STATUS_TERMINATE)) throw new BasicException("无法终止");
		af.setStatus(STATUS_TERMINATE);
		if(!StringUtil.checkValid(reason)) reason="太忙了，都忘了留下reason了";
		af.setComment(reason);
		Date startDate=af.getStartTime();
		Date endDate=new Date();
		af.setDoneTime(endDate);
		String duration=DateUtil.diffString(startDate, endDate);
		af.setDuration(duration);
		return affairDao.updateByPrimaryKeySelective(af)>0;
	}
	@Override
	public Map<String, String> getTypes() {
		Map<String, String> m=new HashMap<>();
		for (int i = 0; i < types.length; i++) {
			m.put(String.valueOf(i), types[i]);
		}
		return m;
	}

}
