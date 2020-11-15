package ua.dima.agency.dto;

public class Country {
    private Long id;
    private String name;

    private Country() {
        //private constructor
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Builder createCountry() {
        return new Country().new Builder();
    }

    public class Builder {

        private Builder() {
            //private constructor
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
