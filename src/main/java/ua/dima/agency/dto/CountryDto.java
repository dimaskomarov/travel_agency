package ua.dima.agency.dto;

import ua.dima.agency.domain.Country;

public class CountryDto {
    private Long id;
    private String name;

    public CountryDto() {
        //empty constructor
    }

    public static CountryDto parse(Country country) {
        return CountryDto.createCountryDTO()
                .withId(country.getId())
                .withName(country.getName()).build();
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

    public static Builder createCountryDTO(){
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
