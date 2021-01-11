package ua.dima.agency.domain;

import ua.dima.agency.dto.TravelTypeDto;

import java.util.Objects;

public class TravelType {
    private Long id;
    private String name;

    private TravelType() {
        //empty constructor
    }

    public static TravelType parse(TravelTypeDto travelTypeDto) {
        return TravelType.builder()
                .id(travelTypeDto.getId())
                .name(travelTypeDto.getName())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TravelType)) return false;
        TravelType that = (TravelType) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "TravelType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Builder builder() {
        return new TravelType().new Builder();
    }

    public class Builder {
        private Builder() {
            //empty constructor
        }

        public Builder id(Long id) {
            TravelType.this.id = id;
            return this;
        }

        public Builder name(String name) {
            TravelType.this.name = name;
            return this;
        }

        public TravelType build() {
            return TravelType.this;
        }
    }
}
