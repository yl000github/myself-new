package basic;

import org.apache.log4j.Logger;
import org.junit.Test;

public class BaeTest {
	Logger logger = Logger.getLogger("stdout");    
	@Test
	public void log(){
		logger.trace("this is trace message from java");
		logger.debug("this is debug message from java");
		logger.info("this is info message from java");
		logger.fatal("this is fatal message from java");
		logger.error("this is error message from java");
		try {
		    int a = 1/0;
		} catch (Exception e) {
		    logger.warn("exception occurred", e);
		}
	}
}
