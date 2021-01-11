package ua.dima.agency.domain;

import ua.dima.agency.dto.CompanyDto;

import java.util.Objects;

public class Company {
    private Long id;
    private String name;
    private String address;
    private int age;

    private Company() {
        //empty constructor
    }

    public static Company parse(CompanyDto companyDTO) {
        return Company.builder()
                .id(companyDTO.getId())
                .name(companyDTO.getName())
                .address(companyDTO.getAddress())
                .age(companyDTO.getAge()).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return age == company.age &&
                Objects.equals(id, company.id) &&
                Objects.equals(name, company.name) &&
                Objects.equals(address, company.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, age);
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

    public static Builder builder() {
        return new Company().new Builder();
    }

    public class Builder {
        private Builder() {
            //empty constructor
        }

        public Builder id(Long id) {
            Company.this.id = id;
            return this;
        }

        public Builder name(String name) {
            Company.this.name = name;
            return this;
        }

        public Builder address(String address) {
            Company.this.address = address;
            return this;
        }

        public Builder age(Integer age) {
            Company.this.age = age;
            return this;
        }

        public Company build() {
            return Company.this;
        }
    }
}
