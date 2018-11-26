package com.example.mohammedmansour.newsapp.API.Responses.Source;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class SourceResponse {

    @SerializedName("sources")
    private List<SourcesItem> sources;

    @SerializedName("status")
    private String status;

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSources(List<SourcesItem> sources) {
        this.sources = sources;
    }

    public List<SourcesItem> getSources() {
        return sources;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "SourceResponse{" +
                        "sources = '" + sources + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}