
package com.karaoke.service.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.karaoke.service.client package. 
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

    private final static QName _CrearCancion_QNAME = new QName("http://localhost:8080/", "crearCancion");
    private final static QName _CrearCancionResponse_QNAME = new QName("http://localhost:8080/", "crearCancionResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.karaoke.service.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CrearCancion }
     * 
     */
    public CrearCancion createCrearCancion() {
        return new CrearCancion();
    }

    /**
     * Create an instance of {@link CrearCancionResponse }
     * 
     */
    public CrearCancionResponse createCrearCancionResponse() {
        return new CrearCancionResponse();
    }

    /**
     * Create an instance of {@link Cancion }
     * 
     */
    public Cancion createCancion() {
        return new Cancion();
    }

    /**
     * Create an instance of {@link Pedido }
     * 
     */
    public Pedido createPedido() {
        return new Pedido();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CrearCancion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://localhost:8080/", name = "crearCancion")
    public JAXBElement<CrearCancion> createCrearCancion(CrearCancion value) {
        return new JAXBElement<CrearCancion>(_CrearCancion_QNAME, CrearCancion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CrearCancionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://localhost:8080/", name = "crearCancionResponse")
    public JAXBElement<CrearCancionResponse> createCrearCancionResponse(CrearCancionResponse value) {
        return new JAXBElement<CrearCancionResponse>(_CrearCancionResponse_QNAME, CrearCancionResponse.class, null, value);
    }

}
