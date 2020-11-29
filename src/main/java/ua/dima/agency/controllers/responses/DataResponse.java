package ua.dima.agency.controllers.responses;

public class DataResponse {
    private Object data;

    public DataResponse() {
        //empty constructor
    }

    public DataResponse(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
