package ua.dima.agency.dto;

import java.time.Instant;
import java.util.List;

public class TourDTO {
    private Long id;
    private Double price;
    private Integer amountDays;
    private Instant dateDeparture;
    private TravelTypeDTO travelType;
    private List<CountryDTO> counties;

    public TourDTO() {
        //empty constructor
    }

    @Override
    public String toString() {
        return "TourDTO{" +
                "id=" + id +
                ", price=" + price +
                ", amountDays=" + amountDays +
                ", dateDeparture=" + dateDeparture +
                ", travelType=" + travelType +
                ", counties=" + counties +
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

    public TravelTypeDTO getTravelType() {
        return travelType;
    }

    public void setTravelType(TravelTypeDTO travelType) {
        this.travelType = travelType;
    }

    public List<CountryDTO> getCounties() {
        return counties;
    }

    public void setCounties(List<CountryDTO> counties) {
        this.counties = counties;
    }

    public static Builder createTourDTO(){
        return new TourDTO().new Builder();
    }

    public class Builder {

        private Builder() {
            //empty constructor
        }

        public Builder withId(Long id) {
            TourDTO.this.id = id;
            return this;
        }

        public Builder withPrice(Double price) {
            TourDTO.this.price = price;
            return this;
        }

        public Builder withAmountDays(Integer amountDays) {
            TourDTO.this.amountDays = amountDays;
            return this;
        }

        public Builder withDateDeparture(Instant dateDeparture) {
            TourDTO.this.dateDeparture = dateDeparture;
            return this;
        }

        public Builder withTravelType(TravelTypeDTO travelType) {
            TourDTO.this.travelType = travelType;
            return this;
        }

        public Builder withCounties(List<CountryDTO> counties) {
            TourDTO.this.counties = counties;
            return this;
        }

        public TourDTO build() {
            return TourDTO.this;
        }
    }
}
