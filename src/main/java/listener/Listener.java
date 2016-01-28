package listener;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;



public class Listener implements ApplicationListener<ContextRefreshedEvent>{
	private static transient Log log=LogFactory.getLog(Listener.class);
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大.
	        //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			log.info("Listener");
	    }
	}

}
