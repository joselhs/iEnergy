//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.10.11 at 05:01:51 PM CEST 
//


package models.ree.desgloses;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdentificacionMensaje" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PVPCDesgloseHorario-esios-MP/}IdentificacionMensajeType"/>
 *         &lt;element name="VersionMensaje" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PVPCDesgloseHorario-esios-MP/}VersionMensajeType"/>
 *         &lt;element name="TipoMensaje" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PVPCDesgloseHorario-esios-MP/}TipoMensajeType"/>
 *         &lt;element name="TipoProceso" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PVPCDesgloseHorario-esios-MP/}TipoProcesoType"/>
 *         &lt;element name="TipoClasificacion" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PVPCDesgloseHorario-esios-MP/}TipoClasificacionType"/>
 *         &lt;element name="IdentificacionRemitente" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PVPCDesgloseHorario-esios-MP/}IdentificacionRemitenteType"/>
 *         &lt;element name="FuncionRemitente" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PVPCDesgloseHorario-esios-MP/}FuncionRemitenteType"/>
 *         &lt;element name="IdentificacionDestinatario" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PVPCDesgloseHorario-esios-MP/}IdentificacionDestinatarioType"/>
 *         &lt;element name="FuncionDestinatario" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PVPCDesgloseHorario-esios-MP/}FuncionDestinatarioType"/>
 *         &lt;element name="FechaHoraMensaje" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PVPCDesgloseHorario-esios-MP/}FechaHoraMensajeType"/>
 *         &lt;element name="Horizonte" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PVPCDesgloseHorario-esios-MP/}HorizonteType"/>
 *         &lt;element name="SeriesTemporales" type="{http://sujetos.esios.ree.es/schemas/2014/04/01/PVPCDesgloseHorario-esios-MP/}SeriesTemporalesType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "identificacionMensaje",
    "versionMensaje",
    "tipoMensaje",
    "tipoProceso",
    "tipoClasificacion",
    "identificacionRemitente",
    "funcionRemitente",
    "identificacionDestinatario",
    "funcionDestinatario",
    "fechaHoraMensaje",
    "horizonte",
    "seriesTemporales"
})
@XmlRootElement(name = "PVPCDesgloseHorario")
public class PVPCDesgloseHorario {

    @XmlElement(name = "IdentificacionMensaje", required = true)
    protected IdentificacionMensajeType identificacionMensaje;
    @XmlElement(name = "VersionMensaje", required = true)
    protected VersionMensajeType versionMensaje;
    @XmlElement(name = "TipoMensaje", required = true)
    protected TipoMensajeType tipoMensaje;
    @XmlElement(name = "TipoProceso", required = true)
    protected TipoProcesoType tipoProceso;
    @XmlElement(name = "TipoClasificacion", required = true)
    protected TipoClasificacionType tipoClasificacion;
    @XmlElement(name = "IdentificacionRemitente", required = true)
    protected IdentificacionRemitenteType identificacionRemitente;
    @XmlElement(name = "FuncionRemitente", required = true)
    protected FuncionRemitenteType funcionRemitente;
    @XmlElement(name = "IdentificacionDestinatario", required = true)
    protected IdentificacionDestinatarioType identificacionDestinatario;
    @XmlElement(name = "FuncionDestinatario", required = true)
    protected FuncionDestinatarioType funcionDestinatario;
    @XmlElement(name = "FechaHoraMensaje", required = true)
    protected FechaHoraMensajeType fechaHoraMensaje;
    @XmlElement(name = "Horizonte", required = true)
    protected HorizonteType horizonte;
    @XmlElement(name = "SeriesTemporales")
    protected List<SeriesTemporalesType> seriesTemporales;

    /**
     * Gets the value of the identificacionMensaje property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificacionMensajeType }
     *     
     */
    public IdentificacionMensajeType getIdentificacionMensaje() {
        return identificacionMensaje;
    }

    /**
     * Sets the value of the identificacionMensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificacionMensajeType }
     *     
     */
    public void setIdentificacionMensaje(IdentificacionMensajeType value) {
        this.identificacionMensaje = value;
    }

    /**
     * Gets the value of the versionMensaje property.
     * 
     * @return
     *     possible object is
     *     {@link VersionMensajeType }
     *     
     */
    public VersionMensajeType getVersionMensaje() {
        return versionMensaje;
    }

    /**
     * Sets the value of the versionMensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link VersionMensajeType }
     *     
     */
    public void setVersionMensaje(VersionMensajeType value) {
        this.versionMensaje = value;
    }

    /**
     * Gets the value of the tipoMensaje property.
     * 
     * @return
     *     possible object is
     *     {@link TipoMensajeType }
     *     
     */
    public TipoMensajeType getTipoMensaje() {
        return tipoMensaje;
    }

    /**
     * Sets the value of the tipoMensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoMensajeType }
     *     
     */
    public void setTipoMensaje(TipoMensajeType value) {
        this.tipoMensaje = value;
    }

    /**
     * Gets the value of the tipoProceso property.
     * 
     * @return
     *     possible object is
     *     {@link TipoProcesoType }
     *     
     */
    public TipoProcesoType getTipoProceso() {
        return tipoProceso;
    }

    /**
     * Sets the value of the tipoProceso property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoProcesoType }
     *     
     */
    public void setTipoProceso(TipoProcesoType value) {
        this.tipoProceso = value;
    }

    /**
     * Gets the value of the tipoClasificacion property.
     * 
     * @return
     *     possible object is
     *     {@link TipoClasificacionType }
     *     
     */
    public TipoClasificacionType getTipoClasificacion() {
        return tipoClasificacion;
    }

    /**
     * Sets the value of the tipoClasificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoClasificacionType }
     *     
     */
    public void setTipoClasificacion(TipoClasificacionType value) {
        this.tipoClasificacion = value;
    }

    /**
     * Gets the value of the identificacionRemitente property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificacionRemitenteType }
     *     
     */
    public IdentificacionRemitenteType getIdentificacionRemitente() {
        return identificacionRemitente;
    }

    /**
     * Sets the value of the identificacionRemitente property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificacionRemitenteType }
     *     
     */
    public void setIdentificacionRemitente(IdentificacionRemitenteType value) {
        this.identificacionRemitente = value;
    }

    /**
     * Gets the value of the funcionRemitente property.
     * 
     * @return
     *     possible object is
     *     {@link FuncionRemitenteType }
     *     
     */
    public FuncionRemitenteType getFuncionRemitente() {
        return funcionRemitente;
    }

    /**
     * Sets the value of the funcionRemitente property.
     * 
     * @param value
     *     allowed object is
     *     {@link FuncionRemitenteType }
     *     
     */
    public void setFuncionRemitente(FuncionRemitenteType value) {
        this.funcionRemitente = value;
    }

    /**
     * Gets the value of the identificacionDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificacionDestinatarioType }
     *     
     */
    public IdentificacionDestinatarioType getIdentificacionDestinatario() {
        return identificacionDestinatario;
    }

    /**
     * Sets the value of the identificacionDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificacionDestinatarioType }
     *     
     */
    public void setIdentificacionDestinatario(IdentificacionDestinatarioType value) {
        this.identificacionDestinatario = value;
    }

    /**
     * Gets the value of the funcionDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link FuncionDestinatarioType }
     *     
     */
    public FuncionDestinatarioType getFuncionDestinatario() {
        return funcionDestinatario;
    }

    /**
     * Sets the value of the funcionDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link FuncionDestinatarioType }
     *     
     */
    public void setFuncionDestinatario(FuncionDestinatarioType value) {
        this.funcionDestinatario = value;
    }

    /**
     * Gets the value of the fechaHoraMensaje property.
     * 
     * @return
     *     possible object is
     *     {@link FechaHoraMensajeType }
     *     
     */
    public FechaHoraMensajeType getFechaHoraMensaje() {
        return fechaHoraMensaje;
    }

    /**
     * Sets the value of the fechaHoraMensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link FechaHoraMensajeType }
     *     
     */
    public void setFechaHoraMensaje(FechaHoraMensajeType value) {
        this.fechaHoraMensaje = value;
    }

    /**
     * Gets the value of the horizonte property.
     * 
     * @return
     *     possible object is
     *     {@link HorizonteType }
     *     
     */
    public HorizonteType getHorizonte() {
        return horizonte;
    }

    /**
     * Sets the value of the horizonte property.
     * 
     * @param value
     *     allowed object is
     *     {@link HorizonteType }
     *     
     */
    public void setHorizonte(HorizonteType value) {
        this.horizonte = value;
    }

    /**
     * Gets the value of the seriesTemporales property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the seriesTemporales property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSeriesTemporales().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SeriesTemporalesType }
     * 
     * 
     */
    public List<SeriesTemporalesType> getSeriesTemporales() {
        if (seriesTemporales == null) {
            seriesTemporales = new ArrayList<SeriesTemporalesType>();
        }
        return this.seriesTemporales;
    }

}
