package com.bootiful.config;

import com.bootiful.interceptor.WSInterceptor;
import com.bootiful.ws.BootifulWSImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WSConfiguration {

    @Autowired
    Bus bus;

    @Autowired
    WSInterceptor wsInterceptor;

    @Bean
    public BootifulWSImpl getWebService(){
        return new BootifulWSImpl();
    }

    @Bean
    public Endpoint endpoint() {

        EndpointImpl endpoint = new EndpointImpl(bus, getWebService());
        endpoint.publish("/BootifulWS");
        endpoint.getBus().getInInterceptors().add(wsInterceptor);

        return endpoint;
    }

}
