package de.jkueck.fire;

import lombok.Getter;

public enum DiveraApiParameter {

    PARAMETER_NUMBER("number"),
    PARAMETER_TITLE("title"),
    PARAMETER_PRIORITY("priority"),
    PARAMETER_TEXT("text"),
    PARAMETER_ADDRESS("address"),
    PARAMETER_LAT("lat"),
    PARAMETER_LNG("lng"),
    PARAMETER_SCENE_OBJECT("scene_object"),
    PARAMETER_CALLER("caller"),
    PARAMETER_PATIENT("patient"),
    PARAMETER_REMARK("remark"),
    PARAMETER_UNITS("units"),
    PARAMETER_DESTINATION("destination"),
    PARAMETER_DESTINATION_ADDRESS("destination_address"),
    PARAMETER_DESTINATION_LAT("destination_lat"),
    PARAMETER_DESTINATION_LNG("destination_lng"),
    PARAMETER_ADDITIONAL_TEXT_1("additional_text_1"),
    PARAMETER_ADDITIONAL_TEXT_2("additional_text_2"),
    PARAMETER_ADDITIONAL_TEXT_3("additional_text_3"),
    PARAMETER_RIC("ric"),
    PARAMETER_VEHICLE("vehicle"),
    PARAMETER_PERSON("person"),
    PARAMETER_DELAY("delay"),
    PARAMETER_COORDINATES("coordinates"),
    PARAMETER_COORDINATES_X("coordinates-x"),
    PARAMETER_COORDINATES_Y("coordinates-y"),
    PARAMETER_ID("id"),
    PARAMETER_ALARM_CODE_ID("alarmcode_id");

    @Getter
    private String value;

    DiveraApiParameter(String value) {
        this.value = value;
    }

}
