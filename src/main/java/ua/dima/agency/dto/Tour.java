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

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmountDay() {
        return amountDay;
    }

    public void setAmountDay(Integer amountDay) {
        this.amountDay = amountDay;
    }

    public Instant getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(Instant dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", price=" + price +
                ", amountDay=" + amountDay +
                ", dateDeparture=" + dateDeparture +
                ", countries=" + countries +
                '}';
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
