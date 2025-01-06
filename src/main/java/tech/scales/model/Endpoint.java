package tech.scales.model;

import java.util.Objects;

import static tech.scales.util.Constants.STATUS_UNKNOWN;

public class Endpoint {

    private String endpointUrl;
    private String endpointName;
    private String endpointStatus;

    public Endpoint() {
        this.endpointUrl = null;
        this.endpointName = null;
        this.endpointStatus = STATUS_UNKNOWN;
    }

    public Endpoint(String endpointUrl, String endpointName) {
        this.endpointUrl = endpointUrl;
        this.endpointName = endpointName;
        this.endpointStatus = STATUS_UNKNOWN;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    public String getEndpointStatus() {
        return endpointStatus;
    }

    public void setEndpointStatus(String endpointStatus) {
        this.endpointStatus = endpointStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endpoint endpoint = (Endpoint) o;
        return Objects.equals(endpointUrl, endpoint.endpointUrl) && Objects.equals(endpointName, endpoint.endpointName) && Objects.equals(endpointStatus, endpoint.endpointStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endpointUrl, endpointName, endpointStatus);
    }

    @Override
    public String toString() {
        return "Endpoint{" +
                "endpointUrl='" + endpointUrl + '\'' +
                ", endpointName='" + endpointName + '\'' +
                ", endpointStatus='" + endpointStatus + '\'' +
                '}';
    }
}