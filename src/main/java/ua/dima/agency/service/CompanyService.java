package ua.dima.agency.service;

import ua.dima.agency.dto.CompanyDTO;

public interface CompanyService {
    CompanyDTO create(CompanyDTO companyDTO);
    CompanyDTO get(Long id);
    CompanyDTO update(Long id, CompanyDTO companyDTO);
    void delete(Long id);
}
