package ua.dima.agency.dto;

public class TravelTypeDTO {
    private Long id;
    private String type;

    public TravelTypeDTO() {
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
        return new TravelTypeDTO().new Builder();
    }

    public class Builder {

        private Builder() {
            //empty constructor
        }

        public Builder withId(Long id) {
            TravelTypeDTO.this.id = id;
            return this;
        }

        public Builder withType(String type) {
            TravelTypeDTO.this.type = type;
            return this;
        }

        public TravelTypeDTO build() {
            return TravelTypeDTO.this;
        }
    }
}
