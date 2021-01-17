package ua.dima.agency.dto;

import ua.dima.agency.domain.Country;

import java.util.Objects;

public class CountryDto {
    private Long id;
    private String name;

    public CountryDto() {
        //empty constructor
    }

    public static CountryDto parse(Country country) {
        return CountryDto.builder()
                .id(country.getId())
                .name(country.getName()).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryDto)) return false;
        CountryDto that = (CountryDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CountryDto{" +
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
        return new CountryDto().new Builder();
    }

    public class Builder {

        private Builder() {
            //empty constructor
        }

        public Builder id(Long id) {
            CountryDto.this.id = id;
            return this;
        }

        public Builder name(String name) {
            CountryDto.this.name = name;
            return this;
        }

        public CountryDto build() {
            return CountryDto.this;
        }
    }
}
