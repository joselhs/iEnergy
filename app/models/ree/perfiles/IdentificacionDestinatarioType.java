//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.10.17 at 09:14:39 PM CEST 
//


package models.ree.perfiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdentificacionDestinatarioType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentificacionDestinatarioType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="v" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="codificacion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentificacionDestinatarioType")
public class IdentificacionDestinatarioType {

    @XmlAttribute(name = "v")
    protected String v;
    @XmlAttribute(name = "codificacion")
    protected String codificacion;

    /**
     * Gets the value of the v property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getV() {
        return v;
    }

    /**
     * Sets the value of the v property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setV(String value) {
        this.v = value;
    }

    /**
     * Gets the value of the codificacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodificacion() {
        return codificacion;
    }

    /**
     * Sets the value of the codificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodificacion(String value) {
        this.codificacion = value;
    }

}
