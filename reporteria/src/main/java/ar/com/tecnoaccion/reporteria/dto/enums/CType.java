package ar.com.tecnoaccion.reporteria.dto.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CType {
    pdf("pdf","application/pdf"),
    xls("xls","application/vnd.ms-excel"),
    html("html","text/html");

    private String name;
    private String contentType;

    CType(String name, String contentType) {
        this.name = name;
        this.contentType = contentType;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }
}
