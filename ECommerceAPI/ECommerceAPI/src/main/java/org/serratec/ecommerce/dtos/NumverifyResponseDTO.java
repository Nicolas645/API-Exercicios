package org.serratec.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NumverifyResponseDTO {

    @JsonProperty("valid")
    private boolean valid;

    @JsonProperty("number")
    private String number;

    @JsonProperty("local_format")
    private String localFormat;

    @JsonProperty("international_format")
    private String internationalFormat;

    @JsonProperty("country_prefix")
    private String countryPrefix;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("location")
    private String location;

    @JsonProperty("carrier")
    private String carrier;

    @JsonProperty("line_type")
    private String lineType;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocalFormat() {
        return localFormat;
    }

    public void setLocalFormat(String localFormat) {
        this.localFormat = localFormat;
    }

    public String getInternationalFormat() {
        return internationalFormat;
    }

    public void setInternationalFormat(String internationalFormat) {
        this.internationalFormat = internationalFormat;
    }

    public String getCountryPrefix() {
        return countryPrefix;
    }

    public void setCountryPrefix(String countryPrefix) {
        this.countryPrefix = countryPrefix;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }
}
