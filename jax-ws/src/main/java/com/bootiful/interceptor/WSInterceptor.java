package com.bootiful.interceptor;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.EndpointSelectionInterceptor;
import org.apache.cxf.binding.soap.interceptor.ReadHeadersInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;

@Component
public class WSInterceptor extends AbstractSoapInterceptor {

    public static final String SOAP_HEADER_NAME_ACTION = "SOAPAction";
    public static final String SOAP_HEADER_NAME_USERNAME = "UNAME";
    public static final String SOAP_HEADER_NAME_PASSWORD = "PWD";

    @Autowired
    private Environment environment;

    public WSInterceptor() {
        super(Phase.READ);
        addAfter(ReadHeadersInterceptor.class.getName());
        addAfter(EndpointSelectionInterceptor.class.getName());
    }

    public void handleMessage(SoapMessage message) throws Fault {
        if (message.getVersion() instanceof Soap11) {

            String soapAction = getHttpHeader(message, SOAP_HEADER_NAME_ACTION);

            if(soapAction != null){

                // it's a web service call

                String headerUname = getSoapHeader(message, SOAP_HEADER_NAME_USERNAME);
                String headerPwd = getSoapHeader(message, SOAP_HEADER_NAME_PASSWORD);

                if ((headerUname == null || headerUname.length() == 0)
                        || (headerPwd == null || headerPwd.length() == 0)) {

                    throwSoapFault();

                } else {

                    try {

                        String uname = environment.getRequiredProperty("ws.authority.username");
                        String pwd = environment.getRequiredProperty("ws.authority.password");

                        if(!uname.equals(headerUname) || !pwd.equals(headerPwd)){

                            throwSoapFault();

                        }

                    } catch (Exception e) {

                        throwSoapFault();

                    }

                }

            }

        } else if (message.getVersion() instanceof Soap12) {

        }
    }

    public static String getSoapHeader(Message message, String headerName) {

        Header header = ((SoapMessage) message).getHeader(new QName("", headerName));

        if(header != null){

            ElementNSImpl element = (ElementNSImpl) header.getObject();

            return element.getTextContent();

        }

        return null;
    }

    public static String getHttpHeader(SoapMessage message, String headerName){

        Map<String, List<String>> headers = CastUtils.cast((Map) message.get(Message.PROTOCOL_HEADERS));
        if (headers != null) {

            List<String> soapHeaders = headers.get(headerName);

            if (soapHeaders != null && soapHeaders.size() > 0) {

                String header = soapHeaders.get(0);
                if (header.startsWith("\"")) {
                    header = header.substring(1, header.length() - 1);
                }

                return header;
            }

        }

        return null;
    }

    private void throwSoapFault() throws Fault {

        QName qname = new QName("BootifulWebService", HttpStatus.UNAUTHORIZED.value() + "");

        throw new SoapFault("Invalid header parameters!", qname);

    }

}
