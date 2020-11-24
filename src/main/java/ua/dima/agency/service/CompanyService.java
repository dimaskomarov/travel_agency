package ua.dima.agency.service;

import ua.dima.agency.dto.CompanyDto;

public interface CompanyService {
    CompanyDto create(CompanyDto companyDTO);
    CompanyDto get(Long id);
    CompanyDto update(Long id, CompanyDto companyDTO);
    void delete(Long id);
}
