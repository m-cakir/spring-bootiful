package com.bootiful;

import com.bootiful.ws.BootifulWS;
import com.bootiful.ws.User;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.headers.Header;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientTest {

    static final String ENDPOINT_URL = "http://localhost:8080/Services/BootifulWS";

    static final String SOAP_HEADER_NAME_USERNAME = "UNAME";
    static final String SOAP_HEADER_NAME_PASSWORD = "PWD";

    static final QName QNAME_USERNAME = new QName("", SOAP_HEADER_NAME_USERNAME);
    static final QName QNAME_PASSWORD = new QName("", SOAP_HEADER_NAME_PASSWORD);

    public static void main(String[] args) throws Exception {

        ClientProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(BootifulWS.class);
        factory.setAddress(ENDPOINT_URL);

        BootifulWS service = (BootifulWS) factory.create();

        Client proxy = ClientProxy.getClient(service);

        List<Header> headers = new ArrayList<Header>();

        Header unameHeader = new Header(QNAME_USERNAME, "bootiful", new JAXBDataBinding(String.class));
        Header pwdHeader = new Header(QNAME_PASSWORD, "spring", new JAXBDataBinding(String.class));

        headers.add(unameHeader);
        headers.add(pwdHeader);

        proxy.getRequestContext().put(Header.HEADER_LIST, headers);

        try {

            // get users

            System.out.println("------------------------");
            System.out.println("[START] users service...");
            System.out.println("------------------------");

            List<User> users = service.users();

            for (User user : users) {

                System.out.println(new Date() + " id: " + user.getId() + ", username: " + user.getUsername());

            }

        } catch (Exception e) {

            System.out.println(new Date() + " e: " + e);

        }

        System.out.println("------------------------");
        System.out.println("[FINISH] users service..");
        System.out.println("------------------------");

    }

}
