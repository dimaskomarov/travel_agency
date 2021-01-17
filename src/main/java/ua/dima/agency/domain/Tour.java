package ua.dima.agency.domain;

import ua.dima.agency.dto.TourDto;

import java.time.Instant;
import java.util.Objects;

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

    public static Tour parse(TourDto tourDTO, Long companyId, Long travelTypeId) {
        return Tour.builder()
                .id(tourDTO.getId())
                .price(tourDTO.getPrice())
                .amountDays(tourDTO.getAmountDays())
                .dateDeparture(tourDTO.getDateDeparture())
                .companyId(companyId)
                .travelTypeId(travelTypeId).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tour)) return false;
        Tour tour = (Tour) o;
        return Objects.equals(id, tour.id) &&
                Objects.equals(price, tour.price) &&
                Objects.equals(amountDays, tour.amountDays) &&
                Objects.equals(dateDeparture, tour.dateDeparture) &&
                Objects.equals(companyId, tour.companyId) &&
                Objects.equals(travelTypeId, tour.travelTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, amountDays, dateDeparture, companyId, travelTypeId);
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

    public static Builder builder() {
        return new Tour().new Builder();
    }

    public class Builder {
        private Builder() {
            //empty constructor
        }

        public Builder id(Long id) {
            Tour.this.id = id;
            return this;
        }

        public Builder price(Double price) {
            Tour.this.price = price;
            return this;
        }

        public Builder amountDays(Integer amountDays) {
            Tour.this.amountDays = amountDays;
            return this;
        }

        public Builder dateDeparture(Instant dateDeparture) {
            Tour.this.dateDeparture = dateDeparture;
            return this;
        }

        public Builder companyId(Long companyId) {
            Tour.this.companyId = companyId;
            return this;
        }

        public Builder travelTypeId(Long travelTypeId) {
            Tour.this.travelTypeId = travelTypeId;
            return this;
        }

        public Tour build() {
            return Tour.this;
        }
    }
}
