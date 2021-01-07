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
        return CountryDto.create()
                .withId(country.getId())
                .withName(country.getName()).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryDto that = (CountryDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
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

    public static Builder create(){
        return new CountryDto().new Builder();
    }

    public class Builder {

        private Builder() {
            //empty constructor
        }

        public Builder withId(Long id) {
            CountryDto.this.id = id;
            return this;
        }

        public Builder withName(String name) {
            CountryDto.this.name = name;
            return this;
        }

        public CountryDto build() {
            return CountryDto.this;
        }
    }
}
