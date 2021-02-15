package de.jkueck.fire.integrations.fireboard;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents  the payload for an fireboard api call according to
 * https://fireboard.net/wp-content/uploads/2020/07/handbuch_fireboard_schnittstelle_alarmdatenuebernahme.pdf
 */

@XmlRootElement(name="fireboardOperation")
public class FireboardPayload {

    private String version;
    private boolean test;
    private String uniqueID;
    private String source;

    private PayloadBasicData basicData;

    @XmlAttribute(name="version")
    public void setVersion(String version) {
        this.version = version;
    }

    @XmlAttribute(name="test")
    public void setTest(boolean test) {
        this.test = test;
    }

    @XmlElement(name="uniqueId")
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    @XmlElement(name="source")
    public void setSource(String source) {
        this.source = source;
    }

    @XmlElement(name="basicData")
    public void setBasicData(PayloadBasicData basicData) {
        this.basicData = basicData;
    }
}

@XmlRootElement(name="basicData")
class PayloadBasicData {

    private String keyword;
    private String announcement;
    private String location;

    @XmlElement(name="keyword")
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @XmlElement(name="announcement")
    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    @XmlElement(name="location")
    public void setLocation(String location) {
        this.location = location;
    }
}