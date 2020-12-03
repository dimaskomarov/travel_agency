package ua.dima.agency.dto;

import ua.dima.agency.domain.Tour;

import java.time.Instant;
import java.util.List;

public class TourDto {
    private Long id;
    private Double price;
    private Integer amountDays;
    private Instant dateDeparture;
    private TravelTypeDto travelTypeDto;
    private List<CountryDto> countiesDto;

    public TourDto() {
        //empty constructor
    }

    public static TourDto parse(Tour tour, TravelTypeDto travelTypeDto, List<CountryDto> countiesDto) {
        return TourDto.createTourDTO()
                .withId(tour.getId())
                .withPrice(tour.getPrice())
                .withAmountDays(tour.getAmountDays())
                .withDateDeparture(tour.getDateDeparture())
                .withTravelTypeDto(travelTypeDto)
                .withCountiesDto(countiesDto).build();
    }

    @Override
    public String toString() {
        return "TourDto{" +
                "id=" + id +
                ", price=" + price +
                ", amountDays=" + amountDays +
                ", dateDeparture=" + dateDeparture +
                ", travelTypeDto=" + travelTypeDto +
                ", countiesDto=" + countiesDto +
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

    public TravelTypeDto getTravelTypeDto() {
        return travelTypeDto;
    }

    public void setTravelTypeDto(TravelTypeDto travelTypeDto) {
        this.travelTypeDto = travelTypeDto;
    }

    public List<CountryDto> getCountiesDto() {
        return countiesDto;
    }

    public void setCountiesDto(List<CountryDto> countiesDto) {
        this.countiesDto = countiesDto;
    }

    public static Builder createTourDTO(){
        return new TourDto().new Builder();
    }

    public class Builder {

        private Builder() {
            //empty constructor
        }

        public Builder withId(Long id) {
            TourDto.this.id = id;
            return this;
        }

        public Builder withPrice(Double price) {
            TourDto.this.price = price;
            return this;
        }

        public Builder withAmountDays(Integer amountDays) {
            TourDto.this.amountDays = amountDays;
            return this;
        }

        public Builder withDateDeparture(Instant dateDeparture) {
            TourDto.this.dateDeparture = dateDeparture;
            return this;
        }

        public Builder withTravelTypeDto(TravelTypeDto travelTypeDto) {
            TourDto.this.travelTypeDto = travelTypeDto;
            return this;
        }

        public Builder withCountiesDto(List<CountryDto> countiesDto) {
            TourDto.this.countiesDto = countiesDto;
            return this;
        }

        public TourDto build() {
            return TourDto.this;
        }
    }
}
