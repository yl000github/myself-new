package filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class CharsetFilter extends OncePerRequestFilter{
	private static transient Log log=LogFactory.getLog(Character.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.setCharacterEncoding("utf-8");
		log.info("my charset filter");
		response.addHeader("Content-Type", "text/plain;charset=utf-8");
		filterChain.doFilter(request, response);
	}

}
