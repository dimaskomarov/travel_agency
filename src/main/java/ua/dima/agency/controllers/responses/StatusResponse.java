package ua.dima.agency.controllers.responses;

public class StatusResponse {
    private String status;

    public StatusResponse() {
        //empty constructor
    }

    public StatusResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
