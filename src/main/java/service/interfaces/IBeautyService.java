package service.interfaces;

import java.io.InputStream;
import java.util.List;

import domain.Beauty;
import exception.BasicException;

public interface IBeautyService {
	public boolean uploadPic(InputStream is,String link,String comment) throws BasicException;
	public boolean uploadPic(String filePath,String link,String comment) throws BasicException;
	public boolean uploadTxt(String content,String link,String comment) throws BasicException;
	public List<Beauty> getAll() throws BasicException;
	public List<Beauty> getTxt() throws BasicException;
	public List<Beauty> getPic() throws BasicException;
}
