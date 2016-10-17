//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.10.17 at 09:14:39 PM CEST 
//


package models.ree.perfiles;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SeriesTemporalesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SeriesTemporalesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdentificacionSeriesTemporales" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PerfilConsumo-esios-MP/}IdentificacionSeriesTemporalesType"/>
 *         &lt;element name="TipoPrecio" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PerfilConsumo-esios-MP/}TipoPrecioType"/>
 *         &lt;element name="Periodo" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PerfilConsumo-esios-MP/}PeriodoType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SeriesTemporalesType", propOrder = {
    "identificacionSeriesTemporales",
    "tipoPrecio",
    "periodo"
})
public class SeriesTemporalesType {

    @XmlElement(name = "IdentificacionSeriesTemporales", required = true)
    protected IdentificacionSeriesTemporalesType identificacionSeriesTemporales;
    @XmlElement(name = "TipoPrecio", required = true)
    protected TipoPrecioType tipoPrecio;
    @XmlElement(name = "Periodo")
    protected List<PeriodoType> periodo;

    /**
     * Gets the value of the identificacionSeriesTemporales property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificacionSeriesTemporalesType }
     *     
     */
    public IdentificacionSeriesTemporalesType getIdentificacionSeriesTemporales() {
        return identificacionSeriesTemporales;
    }

    /**
     * Sets the value of the identificacionSeriesTemporales property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificacionSeriesTemporalesType }
     *     
     */
    public void setIdentificacionSeriesTemporales(IdentificacionSeriesTemporalesType value) {
        this.identificacionSeriesTemporales = value;
    }

    /**
     * Gets the value of the tipoPrecio property.
     * 
     * @return
     *     possible object is
     *     {@link TipoPrecioType }
     *     
     */
    public TipoPrecioType getTipoPrecio() {
        return tipoPrecio;
    }

    /**
     * Sets the value of the tipoPrecio property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoPrecioType }
     *     
     */
    public void setTipoPrecio(TipoPrecioType value) {
        this.tipoPrecio = value;
    }

    /**
     * Gets the value of the periodo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the periodo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPeriodo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PeriodoType }
     * 
     * 
     */
    public List<PeriodoType> getPeriodo() {
        if (periodo == null) {
            periodo = new ArrayList<PeriodoType>();
        }
        return this.periodo;
    }

}
