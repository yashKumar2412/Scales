package tech.scales.model;

import java.util.Objects;

public class AddServerRequest {

    private String url;
    private String name;

    public AddServerRequest() {}

    public AddServerRequest(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AddServerRequest that = (AddServerRequest) o;
        return Objects.equals(url, that.url) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, name);
    }

    @Override
    public String toString() {
        return "AddServerRequest{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
