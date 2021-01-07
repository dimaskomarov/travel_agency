package ua.dima.agency.domain;

import ua.dima.agency.dto.CountryDto;

public class Country {
    private Long id;
    private String name;

    private Country() {
        //empty constructor
    }

    public static Country parse(CountryDto countryDTO) {
        return Country.create()
                .withId(countryDTO.getId())
                .withName(countryDTO.getName()).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        if (id != null ? !id.equals(country.id) : country.id != null) return false;
        return name.equals(country.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
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

    public static Builder create() {
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
