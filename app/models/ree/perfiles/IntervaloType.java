//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.10.17 at 09:14:39 PM CEST 
//


package models.ree.perfiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IntervaloType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IntervaloType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Pos" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PerfilConsumo-esios-MP/}PosType"/>
 *         &lt;element name="Ctd" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PerfilConsumo-esios-MP/}CtdType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntervaloType", propOrder = {
    "pos",
    "ctd"
})
public class IntervaloType {

    @XmlElement(name = "Pos", required = true)
    protected PosType pos;
    @XmlElement(name = "Ctd", required = true)
    protected CtdType ctd;

    /**
     * Gets the value of the pos property.
     * 
     * @return
     *     possible object is
     *     {@link PosType }
     *     
     */
    public PosType getPos() {
        return pos;
    }

    /**
     * Sets the value of the pos property.
     * 
     * @param value
     *     allowed object is
     *     {@link PosType }
     *     
     */
    public void setPos(PosType value) {
        this.pos = value;
    }

    /**
     * Gets the value of the ctd property.
     * 
     * @return
     *     possible object is
     *     {@link CtdType }
     *     
     */
    public CtdType getCtd() {
        return ctd;
    }

    /**
     * Sets the value of the ctd property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtdType }
     *     
     */
    public void setCtd(CtdType value) {
        this.ctd = value;
    }

}
