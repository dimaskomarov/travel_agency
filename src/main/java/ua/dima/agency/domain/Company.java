package ua.dima.agency.domain;

import ua.dima.agency.dto.CompanyDto;

public class Company {
    private Long id;
    private String name;
    private String address;
    private int age;

    private Company() {
        //empty constructor
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static Company parse(CompanyDto companyDTO) {
        return Company.createCompany()
                .withId(companyDTO.getId())
                .withName(companyDTO.getName())
                .withAddress(companyDTO.getAddress())
                .withAge(companyDTO.getAge()).build();
    }

    public static Builder createCompany() {
        return new Company().new Builder();
    }

    public class Builder {
        private Builder() {
            //empty constructor
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

        public Company build() {
            return Company.this;
        }
    }
}
