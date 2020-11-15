package ua.dima.agency.dto;

public class TravelType {
    private Long id;
    private String type;

    private TravelType() {
        //private constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TravelType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    public static Builder createTravelType() {
        return new TravelType().new Builder();
    }

    public class Builder {

        private Builder() {
            //private constructor
        }

        public Builder withId(Long id) {
            TravelType.this.id = id;
            return this;
        }

        public Builder withType(String type) {
            TravelType.this.type = type;
            return this;
        }

        public TravelType build() {
            return TravelType.this;
        }
    }
}
