package de.jkueck.fire;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AlertMessage {

    private String city;

    private String street;

    private String object;

    private String category;

    private String keyword;

    private String keywordText;

    private String remark1;

    private String remark2;

    private String completeMessage;

    private LocalDateTime alertTimestamp;

    public static class Builder {

        private String city;

        private String street;

        private String object;

        private String category;

        private String keyword;

        private String keywordText;

        private String remark1;

        private String remark2;

        private String completeMessage;

        private LocalDateTime alertTimestamp;

        public Builder(final String completeMessage, LocalDateTime alertTimestamp) {
            this.completeMessage = completeMessage;
            this.alertTimestamp = alertTimestamp;
        }

        public Builder withCity(final String city) {
            this.city = city;
            return this;
        }

        public Builder withStreet(final String street) {
            this.street = street;
            return this;
        }

        public Builder withObject(final String object) {
            this.object = object;
            return this;
        }

        public Builder withCategory(final String category) {
            this.category = category;
            return this;
        }

        public Builder withKeyword(final String keyword) {
            this.keyword = keyword;
            return this;
        }

        public Builder withKeywordText(final String keywordText) {
            this.keywordText = keywordText;
            return this;
        }

        public Builder withRemark1(final String remark1) {
            this.remark1 = remark1;
            return this;
        }

        public Builder withRemark2(final String remark2) {
            this.remark2 = remark2;
            return this;
        }

        public AlertMessage build() {
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.city = this.city;
            alertMessage.street = this.street;
            alertMessage.object = this.object;
            alertMessage.category = this.category;
            alertMessage.keyword = this.keyword;
            alertMessage.keywordText = this.keywordText;
            alertMessage.remark1 = this.remark1;
            alertMessage.remark2 = this.remark2;
            alertMessage.completeMessage = this.completeMessage;
            alertMessage.alertTimestamp = this.alertTimestamp;
            return alertMessage;
        }

    }

}
