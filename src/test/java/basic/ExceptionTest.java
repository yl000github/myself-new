package basic;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import exception.BasicException;
import exception.SqlException;

public class ExceptionTest {
	private static transient Log log = LogFactory.getLog(ExceptionTest.class);
	@Test
	public void ex(){
		try {
			throw new SqlException("hello");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e);
		}
	}
	@Test
	public void ex1(){
//		BasicConfigurator.configure();
		try {
			throw new BasicException("hello");
		} catch (BasicException e) {
			// TODO: handle exception
//			e.printStackTrace( 
			log.error(e.getMessage());
			System.err.println(e.hint());
		}
		
//		log.debug("123");
	}
}
