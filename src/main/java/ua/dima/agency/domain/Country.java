package ua.dima.agency.domain;

import ua.dima.agency.dto.CountryDto;

public class Country {
    private Long id;
    private String name;

    private Country() {
        //empty constructor
    }

    public static Country parse(CountryDto countryDTO) {
        return Country.createCountry()
                .withId(countryDTO.getId())
                .withName(countryDTO.getName()).build();
    }

    @Override
    public String toString() {
        return "Country{" +
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

    public static Builder createCountry() {
        return new Country().new Builder();
    }

    public class Builder {
        private Builder() {
            //empty constructor
        }

        public Builder withId(Long id) {
            Country.this.id = id;
            return this;
        }

        public Builder withName(String name) {
            Country.this.name = name;
            return this;
        }

        public Country build() {
            return Country.this;
        }
    }
}
