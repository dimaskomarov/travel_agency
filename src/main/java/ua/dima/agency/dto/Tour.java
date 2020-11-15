package ua.dima.agency.dto;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class Tour {
    private Long id;
    private Double price;
    private Integer amountDay;
    private Instant dateDeparture;
    private List<Country> countries;

    private Tour() {
        //private constructor
    }

    public Long getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getAmountDay() {
        return amountDay;
    }

    public Instant getDateDeparture() {
        return dateDeparture;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public static Builder createTour() {
        return new Tour().new Builder();
    }

    public class Builder {

        private Builder() {
            //private constructor
        }

        public Builder withId(Long id) {
            Tour.this.id = id;
            return this;
        }

        public Builder withPrice(Double price) {
            Tour.this.price = price;
            return this;
        }

        public Builder withAmountDay(Integer amountDay) {
            Tour.this.amountDay = amountDay;
            return this;
        }

        public Builder withDateDeparture(Instant dateDeparture) {
            Tour.this.dateDeparture = dateDeparture;
            return this;
        }

        public Builder withCountries(Country ... countries) {
            Tour.this.countries = Arrays.asList(countries);
            return this;
        }

        public Tour build() {
            return Tour.this;
        }
    }
}
