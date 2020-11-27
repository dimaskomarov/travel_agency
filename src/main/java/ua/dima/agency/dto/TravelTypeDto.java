package ua.dima.agency.dto;

public class TravelTypeDto {
    private String type;

    public TravelTypeDto() {
        //empty constructor
    }

    @Override
    public String toString() {
        return "TravelTypeDto{" +
                "type='" + type + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Builder createTravelTypeDTO(){
        return new TravelTypeDto().new Builder();
    }

    public class Builder {

        private Builder() {
            //empty constructor
        }

        public Builder withType(String type) {
            TravelTypeDto.this.type = type;
            return this;
        }

        public TravelTypeDto build() {
            return TravelTypeDto.this;
        }
    }
}
