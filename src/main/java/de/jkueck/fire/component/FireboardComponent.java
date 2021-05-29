package de.jkueck.fire.component;

import de.jkueck.fire.AlertMessage;
import de.jkueck.fire.setting.SystemSettings;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Slf4j
@Component
public class FireboardComponent extends AlertBaseComponent {

    private final String fireboardSourceName = "DME Alarmdatenübernahme";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    @Override
    void onReceiveAlert(AlertMessage alertMessage) {
        String fireboardAuthKey = this.getSystemSettingService().getAsString(SystemSettings.SYSTEM_SETTINGS_FIREBOARD_AUTH_KEY);
        try {
            callFireboardApi(alertMessage);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    boolean isComponentEnabled() {
        return this.getSystemSettingService().getAsBoolean(SystemSettings.SYSTEM_SETTING_IS_FIREBOARD_ENABLED);
    }

    public void callFireboardApi(AlertMessage alertMessage) throws JAXBException {
        FireboardPayload payload = createFBPayload(alertMessage);

        String fireboardApiUrl = "https://login.fireboard.net/api" +
                "?" +
                "authkey" +
                "=" +
                SystemSettings.SYSTEM_SETTINGS_FIREBOARD_AUTH_KEY +
                "&" +
                "call" +
                "=" +
                "operation_data";

        JAXBContext jc = JAXBContext.newInstance(FireboardPayload.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(payload, stringWriter );

        log.info("call fireboard api from source "+this.fireboardSourceName+" with url("+fireboardApiUrl+") and the following payload:\n"+ stringWriter);

         RestTemplate restTemplate = new RestTemplate();
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_XML);
         HttpEntity<FireboardPayload> request = new HttpEntity<>(payload, headers);
         val strResponse = restTemplate.postForObject(fireboardApiUrl, request, String.class);

         log.info("send message to fireboard (" + fireboardApiUrl + ") with auth key (" + SystemSettings.SYSTEM_SETTINGS_FIREBOARD_AUTH_KEY + "), response (" + strResponse + ")");


    }

    /**
     * Construct the XML payload for fireboard
     * @param alertMessage The AlertMessage received from the input device
     * @return FireboardPayload Returns a Fireboard Payload Object which contains the necessary XML structure
     */
    private FireboardPayload createFBPayload(AlertMessage alertMessage) {
        FireboardPayload fbPayload = new FireboardPayload();
        PayloadBasicData fbPayloadBasicData = new PayloadBasicData();

        fbPayload.setVersion("1.01");
        fbPayload.setTest(false);
        fbPayload.setSource(this.fireboardSourceName);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        fbPayload.setUniqueID(sdf.format(timestamp));

        fbPayloadBasicData.setKeyword(alertMessage.getCategory()+alertMessage.getKeyword());
        fbPayloadBasicData.setAnnouncement(alertMessage.getRemark1()+","+alertMessage.getRemark2());
        fbPayloadBasicData.setLocation(alertMessage.getStreet()+", "+alertMessage.getCity());

        fbPayload.setBasicData(fbPayloadBasicData);

        return fbPayload;
    }
}

/**
 * This class represents  the payload for an fireboard api call according to
 * https://fireboard.net/wp-content/uploads/2020/07/handbuch_fireboard_schnittstelle_alarmdatenuebernahme.pdf
 */

@Getter
@XmlRootElement(name="fireboardOperation")
class FireboardPayload {

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

@Getter
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
