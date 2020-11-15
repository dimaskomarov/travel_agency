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

    public String getType() {
        return type;
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
