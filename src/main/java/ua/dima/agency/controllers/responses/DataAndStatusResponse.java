package ua.dima.agency.controllers.responses;

public class DataAndStatusResponse {
    private String status;
    private Object data;

    public DataAndStatusResponse() {
        //empty constructor
    }

    public DataAndStatusResponse(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
