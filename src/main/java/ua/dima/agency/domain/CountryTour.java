package ua.dima.agency.domain;

import java.util.Objects;

public class CountryTour {
    private Long countryId;
    private Long tourId;

    private CountryTour() {
        //empty constructor
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryTour)) return false;
        CountryTour that = (CountryTour) o;
        return Objects.equals(countryId, that.countryId) &&
                Objects.equals(tourId, that.tourId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, tourId);
    }

    @Override
    public String toString() {
        return "CountryTour{" +
                "CountryId=" + countryId +
                ", TourId=" + tourId +
                '}';
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public static Builder builder() {
        return new CountryTour().new Builder();
    }

    public class Builder {
        private Builder() {
            //empty constructor
        }

        public Builder countryId(Long countryId) {
            CountryTour.this.countryId = countryId;
            return this;
        }

        public Builder tourId(Long tourId) {
            CountryTour.this.tourId = tourId;
            return this;
        }

        public CountryTour build() {
            return CountryTour.this;
        }
    }
}
