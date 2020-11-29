package ua.dima.agency.domain;

import java.time.Instant;

public class Tour {
    private Long id;
    private Double price;
    private Integer amountDays;
    private Instant dateDeparture;
    private Long companyId;
    private Long travelTypeId;

    private Tour() {
        //empty constructor
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", price=" + price +
                ", amountDay=" + amountDays +
                ", dateDeparture=" + dateDeparture +
                ", companyId=" + companyId +
                ", travelTypeId=" + travelTypeId +
                '}';
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

    public Integer getAmountDays() {
        return amountDays;
    }

    public void setAmountDays(Integer amountDays) {
        this.amountDays = amountDays;
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

    public static Builder createTour() {
        return new Tour().new Builder();
    }

    public class Builder {
        private Builder() {
            //empty constructor
        }

        public Builder withId(Long id) {
            Tour.this.id = id;
            return this;
        }

        public Builder withPrice(Double price) {
            Tour.this.price = price;
            return this;
        }

        public Builder withAmountDays(Integer amountDays) {
            Tour.this.amountDays = amountDays;
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
