package com.example.pcbuilder.utils.events;

public class CancelOrderEvent implements Event{
    private String buildName;
    private String reason;

    private Boolean pc;

    public CancelOrderEvent(String buildName, Boolean pc) {
        this.buildName = buildName;
        this.pc = pc;
    }

    public CancelOrderEvent(String buildName, String reason) {
        this.buildName = buildName;
        this.reason = reason;
    }

    public CancelOrderEvent(String buildName){
        this.buildName = buildName;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getPc() {
        return pc;
    }

    public void setPc(Boolean pc) {
        this.pc = pc;
    }
}
