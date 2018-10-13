package com.bootiful.ws;

import com.bootiful.framework.domain.User;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "BootifulWS")
public interface BootifulWS {

    @WebMethod(operationName="users")
    @WebResult(name = "user")
    List<User> users();

}
