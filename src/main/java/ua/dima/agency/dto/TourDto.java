package ua.dima.agency.dto;

import ua.dima.agency.domain.Tour;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

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
        return TourDto.builder()
                .id(tour.getId())
                .price(tour.getPrice())
                .amountDays(tour.getAmountDays())
                .dateDeparture(tour.getDateDeparture())
                .travelTypeDto(travelTypeDto)
                .countiesDto(countiesDto).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TourDto)) return false;
        TourDto tourDto = (TourDto) o;
        return Objects.equals(id, tourDto.id) &&
                Objects.equals(price, tourDto.price) &&
                Objects.equals(amountDays, tourDto.amountDays) &&
                Objects.equals(dateDeparture, tourDto.dateDeparture) &&
                Objects.equals(travelTypeDto, tourDto.travelTypeDto) &&
                Objects.equals(countiesDto, tourDto.countiesDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, amountDays, dateDeparture, travelTypeDto, countiesDto);
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

    public static Builder builder(){
        return new TourDto().new Builder();
    }

    public class Builder {

        private Builder() {
            //empty constructor
        }

        public Builder id(Long id) {
            TourDto.this.id = id;
            return this;
        }

        public Builder price(Double price) {
            TourDto.this.price = price;
            return this;
        }

        public Builder amountDays(Integer amountDays) {
            TourDto.this.amountDays = amountDays;
            return this;
        }

        public Builder dateDeparture(Instant dateDeparture) {
            TourDto.this.dateDeparture = dateDeparture;
            return this;
        }

        public Builder travelTypeDto(TravelTypeDto travelTypeDto) {
            TourDto.this.travelTypeDto = travelTypeDto;
            return this;
        }

        public Builder countiesDto(List<CountryDto> countiesDto) {
            TourDto.this.countiesDto = countiesDto;
            return this;
        }

        public TourDto build() {
            return TourDto.this;
        }
    }
}
