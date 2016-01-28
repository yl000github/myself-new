package service.interfaces;

import java.util.List;
import java.util.Map;

import domain.Affair;
import exception.BasicException;


public interface IAffairService {
	public List<Affair> getAll();
	public boolean createOne(Affair affair);
	public boolean startOne(int id,String how) throws BasicException;
	public boolean completeOne(int id,String comment) throws BasicException;
	public boolean terminateOne(int id,String reason) throws BasicException;
	public Map<String,String> getTypes();
}
