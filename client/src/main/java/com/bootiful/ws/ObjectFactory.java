
package com.bootiful.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bootiful.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Users_QNAME = new QName("http://ws.bootiful.com/", "users");
    private final static QName _UsersResponse_QNAME = new QName("http://ws.bootiful.com/", "usersResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bootiful.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Users }
     * 
     */
    public Users createUsers() {
        return new Users();
    }

    /**
     * Create an instance of {@link UsersResponse }
     * 
     */
    public UsersResponse createUsersResponse() {
        return new UsersResponse();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link BaseModel }
     * 
     */
    public BaseModel createBaseModel() {
        return new BaseModel();
    }

    /**
     * Create an instance of {@link Role }
     * 
     */
    public Role createRole() {
        return new Role();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Users }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.bootiful.com/", name = "users")
    public JAXBElement<Users> createUsers(Users value) {
        return new JAXBElement<Users>(_Users_QNAME, Users.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UsersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.bootiful.com/", name = "usersResponse")
    public JAXBElement<UsersResponse> createUsersResponse(UsersResponse value) {
        return new JAXBElement<UsersResponse>(_UsersResponse_QNAME, UsersResponse.class, null, value);
    }

}
