package ua.dima.agency.repositories;

import ua.dima.agency.domain.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository {
    List<Company> getAll();
    Optional<Company> getOne(Long id);
    Optional<Company> create(Company company);
    Optional<Company> update(Long id, Company company);
    void delete(Long id);
}
