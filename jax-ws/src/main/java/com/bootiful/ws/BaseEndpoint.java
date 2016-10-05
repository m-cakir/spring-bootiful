package com.bootiful.ws;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

public abstract class BaseEndpoint {
	
    protected String getClientIP(){
        
    	Message message = PhaseInterceptorChain.getCurrentMessage();
    	HttpServletRequest request = (HttpServletRequest)message.get(AbstractHTTPDestination.HTTP_REQUEST);

        String remoteIp = request.getRemoteAddr();
        String proxyIp = request.getHeader("X-Forwarded-For");
        
        if (proxyIp == null || proxyIp.length() == 0)
            return remoteIp;
        
        else {
            String forwardedIp = proxyIp;
            String [] ipTokens = forwardedIp.split(",");
            if(ipTokens != null && ipTokens.length > 1) forwardedIp = ipTokens[ipTokens.length -2].trim();
            return (proxyIp == null || proxyIp.length() == 0) ? remoteIp : forwardedIp;
        }
    }

    protected String getServerIP(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        } catch(Exception ex){
            return null;
        }
    }
    
}
