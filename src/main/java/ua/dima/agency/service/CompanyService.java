package ua.dima.agency.service;

import ua.dima.agency.dto.CompanyDto;

import java.util.List;

public interface CompanyService {
    CompanyDto create(CompanyDto companyDTO);
    CompanyDto get(Long id);
    List<CompanyDto> getAll();
    CompanyDto update(Long id, CompanyDto companyDTO);
    void delete(Long id);
}
