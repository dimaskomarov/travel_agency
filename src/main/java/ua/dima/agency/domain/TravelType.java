package ua.dima.agency.domain;

import ua.dima.agency.dto.TravelTypeDto;

import java.util.Objects;

public class TravelType {
    private Long id;
    private String type;

    private TravelType() {
        //empty constructor
    }

    public static TravelType parse(TravelTypeDto travelTypeDto) {
        return TravelType.create()
                .withId(travelTypeDto.getId())
                .withType(travelTypeDto.getType())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TravelType)) return false;
        TravelType that = (TravelType) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return "TravelType{" +
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

    public static Builder create() {
        return new TravelType().new Builder();
    }

    public class Builder {
        private Builder() {
            //empty constructor
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
