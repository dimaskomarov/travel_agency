package ua.dima.agency.dto;

public class CountryDto {
    private String name;

    public CountryDto() {
        //empty constructor
    }

    @Override
    public String toString() {
        return "CountryDto{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Builder createCountryDTO(){
        return new CountryDto().new Builder();
    }

    public class Builder {

        private Builder() {
            //empty constructor
        }

        public Builder withName(String name) {
            CountryDto.this.name = name;
            return this;
        }

        public CountryDto build() {
            return CountryDto.this;
        }
    }
}
