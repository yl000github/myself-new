package service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.BeautyMapper;
import domain.Beauty;
import exception.BasicException;
import service.interfaces.IBeautyService;
import utils.DateUtil;
import utils.PropertiesUtil;

@Service
public class BeautyService implements IBeautyService{
	private static Log log=LogFactory.getLog(Beauty.class);
	public static final String picDesDir=PropertiesUtil.getValueD("beauty_pic_dir");
	public static final String TYPE_PIC="图片";
	public static final String TYPE_TXT="文本";
	@Autowired
	private BeautyMapper beautyMapper;

	/**
	 * 似乎专门有封装，在controller层就可以完成保存工作了。
	 */
	@Override
	public boolean uploadPic(InputStream is, String link, String comment) throws BasicException {
		log.info("hello");
		return false;
	}
	
	@Override
	public boolean uploadPic(String filePath,String link, String comment) throws BasicException {
		Beauty beauty=new Beauty();
		beauty.setComment(comment);
		beauty.setLink(link);
		beauty.setContent(filePath);
		beauty.setType(TYPE_PIC);
		beauty.setCreateTime(DateUtil.getNow());
		beautyMapper.insert(beauty);
		return true;
	}
	@Override
	public boolean uploadTxt(String content, String link, String comment) throws BasicException {
		Beauty beauty=new Beauty();
		beauty.setComment(comment);
		beauty.setLink(link);
		beauty.setContent(content);
		beauty.setType(TYPE_TXT);
		beauty.setCreateTime(DateUtil.getNow());
		beautyMapper.insert(beauty);
		return true;
	}

	@Override
	public List<Beauty> getAll() throws BasicException {
		return beautyMapper.selectAll();
	}

	@Override
	public List<Beauty> getTxt() throws BasicException {
		List<Beauty> list=getAll();
		List<Beauty> nL=new ArrayList<>();
		for (Beauty beauty : list) {
			if(beauty.getType().equals(TYPE_TXT)) nL.add(beauty);
		}
		return nL;
	}

	@Override
	public List<Beauty> getPic() throws BasicException {
		List<Beauty> list=getAll();
		List<Beauty> nL=new ArrayList<>();
		for (Beauty beauty : list) {
			if(beauty.getType().equals(TYPE_PIC)) nL.add(beauty);
		}
		return nL;
	}

	

}
