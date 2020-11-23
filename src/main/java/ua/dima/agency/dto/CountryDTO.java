package ua.dima.agency.dto;

public class CountryDTO {
    private Long id;
    private String name;

    public CountryDTO() {
        //empty constructor
    }

    @Override
    public String toString() {
        return "CountryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Builder createCountryDTO(){
        return new CountryDTO().new Builder();
    }

    public class Builder {

        private Builder() {
            //empty constructor
        }

        public Builder withId(Long id) {
            CountryDTO.this.id = id;
            return this;
        }

        public Builder withName(String name) {
            CountryDTO.this.name = name;
            return this;
        }

        public CountryDTO build() {
            return CountryDTO.this;
        }
    }
}
