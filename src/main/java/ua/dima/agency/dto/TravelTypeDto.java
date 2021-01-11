package ua.dima.agency.dto;

import ua.dima.agency.domain.TravelType;

import java.util.Objects;

public class TravelTypeDto {
    private Long id;
    private String name;

    public TravelTypeDto() {
        //empty constructor
    }

    public static TravelTypeDto parse(TravelType travelType) {
        return TravelTypeDto.builder()
                .id(travelType.getId())
                .name(travelType.getName()).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TravelTypeDto)) return false;
        TravelTypeDto that = (TravelTypeDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "TravelTypeDto{" +
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

    public static Builder builder(){
        return new TravelTypeDto().new Builder();
    }

    public class Builder {

        private Builder() {
            //empty constructor
        }

        public Builder id(Long id) {
            TravelTypeDto.this.id = id;
            return this;
        }

        public Builder name(String name) {
            TravelTypeDto.this.name = name;
            return this;
        }

        public TravelTypeDto build() {
            return TravelTypeDto.this;
        }
    }
}
