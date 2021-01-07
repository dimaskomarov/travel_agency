package ua.dima.agency.domain;

public class CountryTour {
    private Long countryId;
    private Long tourId;

    private CountryTour() {
        //empty constructor
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryTour that = (CountryTour) o;

        if (!countryId.equals(that.countryId)) return false;
        return tourId.equals(that.tourId);
    }

    @Override
    public int hashCode() {
        int result = countryId.hashCode();
        result = 31 * result + tourId.hashCode();
        return result;
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

    public static Builder create() {
        return new CountryTour().new Builder();
    }

    public class Builder {
        private Builder() {
            //empty constructor
        }

        public Builder withCountryId(Long countryId) {
            CountryTour.this.countryId = countryId;
            return this;
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
