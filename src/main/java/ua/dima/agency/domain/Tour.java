package ua.dima.agency.domain;

import ua.dima.agency.dto.TourDto;

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

    public static Tour parse(TourDto tourDTO, Long companyId, Long travelTypeId) {
        return Tour.create()
                .withId(tourDTO.getId())
                .withPrice(tourDTO.getPrice())
                .withAmountDays(tourDTO.getAmountDays())
                .withDateDeparture(tourDTO.getDateDeparture())
                .withCompanyId(companyId)
                .withTravelTypeId(travelTypeId).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tour tour = (Tour) o;

        if (id != null ? !id.equals(tour.id) : tour.id != null) return false;
        if (!price.equals(tour.price)) return false;
        if (!amountDays.equals(tour.amountDays)) return false;
        if (!dateDeparture.equals(tour.dateDeparture)) return false;
        if (!companyId.equals(tour.companyId)) return false;
        return travelTypeId != null ? travelTypeId.equals(tour.travelTypeId) : tour.travelTypeId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + price.hashCode();
        result = 31 * result + amountDays.hashCode();
        result = 31 * result + dateDeparture.hashCode();
        result = 31 * result + companyId.hashCode();
        result = 31 * result + (travelTypeId != null ? travelTypeId.hashCode() : 0);
        return result;
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

    public static Builder create() {
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
