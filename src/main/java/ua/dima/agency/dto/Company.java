package ua.dima.agency.dto;

import java.util.Arrays;
import java.util.List;

public class Company {
    private Long id;
    private String name;
    private String address;
    private int age;
    private List<Tour> tours;

    private Company() {
        //private constructor
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public List<Tour> getTours() {
        return tours;
    }

    public static Builder createCompany() {
        return new Company().new Builder();
    }

    public class Builder {

        private Builder() {
            //private constructor
        }

        public Builder withId(Long id) {
            Company.this.id = id;
            return this;
        }

        public Builder withName(String name) {
            Company.this.name = name;
            return this;
        }

        public Builder withAddress(String address) {
            Company.this.address = address;
            return this;
        }

        public Builder withAge(Integer age) {
            Company.this.age = age;
            return this;
        }

        public Builder withTours(Tour ... tours) {
            Company.this.tours = Arrays.asList(tours);
            return this;
        }

        public Company build() {
            return Company.this;
        }
    }
}
