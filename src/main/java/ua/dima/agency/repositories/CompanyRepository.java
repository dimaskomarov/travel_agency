package ua.dima.agency.repositories;

import ua.dima.agency.dto.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository {
    Optional<List<Company>> getAll();
    Optional<Company> getOne(Long id);
    Optional<Company> create(Company company);
    Optional<Company> update(Long id, Company company);
    void delete(Long id);
}
