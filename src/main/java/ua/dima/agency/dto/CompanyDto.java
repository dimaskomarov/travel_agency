package ua.dima.agency.dto;

import ua.dima.agency.domain.Company;

import java.util.List;
import java.util.Objects;

public class CompanyDto {
    private Long id;
    private String name;
    private String address;
    private Integer age;
    private List<TourDto> toursDto;

    private CompanyDto() {
        //empty constructor
    }

    public static CompanyDto parse(Company company, List<TourDto> toursDto) {
        return CompanyDto.create()
                .withId(company.getId())
                .withName(company.getName())
                .withAddress(company.getAddress())
                .withAge(company.getAge())
                .withToursDto(toursDto).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyDto)) return false;
        CompanyDto that = (CompanyDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(age, that.age) &&
                Objects.equals(toursDto, that.toursDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, age, toursDto);
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", toursDto=" + toursDto +
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<TourDto> getToursDto() {
        return toursDto;
    }

    public void setToursDto(List<TourDto> toursDto) {
        this.toursDto = toursDto;
    }

    public static Builder create() {
        return new CompanyDto().new Builder();
    }

    public class Builder {

        private Builder() {
            //empty constructor
        }

        public Builder withId(Long id) {
            CompanyDto.this.id = id;
            return this;
        }

        public Builder withName(String name) {
            CompanyDto.this.name = name;
            return this;
        }

        public Builder withAddress(String address) {
            CompanyDto.this.address = address;
            return this;
        }

        public Builder withAge(Integer age) {
            CompanyDto.this.age = age;
            return this;
        }

        public Builder withToursDto(List<TourDto> toursDto) {
            CompanyDto.this.toursDto = toursDto;
            return this;
        }

        public CompanyDto build() {
            return CompanyDto.this;
        }
    }
}
