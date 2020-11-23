package ua.dima.agency.dto;

import java.util.List;

public class CompanyDTO {
    private Long id;
    private String name;
    private String address;
    private Integer age;
    private List<TourDTO> tours;

    private CompanyDTO() {
        //empty constructor
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", tours=" + tours +
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

    public List<TourDTO> getTours() {
        return tours;
    }

    public void setTours(List<TourDTO> tours) {
        this.tours = tours;
    }

    public static Builder createCompanyDTO() {
        return new CompanyDTO().new Builder();
    }

    public class Builder {

        private Builder() {
            //empty constructor
        }

        public Builder withId(Long id) {
            CompanyDTO.this.id = id;
            return this;
        }

        public Builder withName(String name) {
            CompanyDTO.this.name = name;
            return this;
        }

        public Builder withAddress(String address) {
            CompanyDTO.this.address = address;
            return this;
        }

        public Builder withAge(Integer age) {
            CompanyDTO.this.age = age;
            return this;
        }

        public Builder withTours(List<TourDTO> tours) {
            CompanyDTO.this.tours = tours;
            return this;
        }

        public CompanyDTO build() {
            return CompanyDTO.this;
        }
    }
}
