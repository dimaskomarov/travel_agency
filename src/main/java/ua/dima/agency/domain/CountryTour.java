package ua.dima.agency.domain;

public class CountryTour {
    private Long countryId;
    private Long tourId;

    private CountryTour() {
        //empty constructor
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

    public static Builder createCountryTour() {
        return new CountryTour().new Builder();
    }

    public class Builder {
        private Builder() {
            //empty constructor
        }

        public Builder withTourId(Long tourId) {
            CountryTour.this.tourId = tourId;
            return this;
        }

        public CountryTour build() {
            return CountryTour.this;
        }
    }
}
