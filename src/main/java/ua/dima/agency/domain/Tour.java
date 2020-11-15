package ua.dima.agency.domain;

import java.time.Instant;

public class Tour {
    private Long id;
    private Double price;
    private Integer amountDay;
    private Instant dateDeparture;
    private Long companyId;
    private Long travelTypeId;

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getTravelTypeId() {
        return travelTypeId;
    }

    public void setTravelTypeId(Long travelTypeId) {
        this.travelTypeId = travelTypeId;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", price=" + price +
                ", amountDay=" + amountDay +
                ", dateDeparture=" + dateDeparture +
                ", companyId=" + companyId +
                ", travelTypeId=" + travelTypeId +
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

        public Builder withCompanyId(Long companyId) {
            Tour.this.companyId = companyId;
            return this;
        }

        public Builder withTravelTypeId(Long travelTypeId) {
            Tour.this.travelTypeId = travelTypeId;
            return this;
        }

        public Tour build() {
            return Tour.this;
        }
    }
}
