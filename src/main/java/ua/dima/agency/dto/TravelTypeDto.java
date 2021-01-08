package ua.dima.agency.dto;

import ua.dima.agency.domain.TravelType;

import java.util.Objects;

public class TravelTypeDto {
    private Long id;
    private String type;

    public TravelTypeDto() {
        //empty constructor
    }

    public static TravelTypeDto parse(TravelType travelType) {
        return TravelTypeDto.create()
                .withId(travelType.getId())
                .withType(travelType.getType()).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TravelTypeDto)) return false;
        TravelTypeDto that = (TravelTypeDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return "TravelTypeDto{" +
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

    public static Builder create(){
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
