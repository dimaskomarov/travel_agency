package ua.dima.agency.dto;

public class TravelTypeDto {
    private Long id;
    private String type;

    public TravelTypeDto() {
        //empty constructor
    }

    @Override
    public String toString() {
        return "TravelTypeDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
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

    public static Builder createTravelTypeDTO(){
        return new TravelTypeDto().new Builder();
    }

    public class Builder {

        private Builder() {
            //empty constructor
        }

        public Builder withId(Long id) {
            TravelTypeDto.this.id = id;
            return this;
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
