package ua.dima.agency.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dima.agency.domain.Company;
import ua.dima.agency.domain.Tour;
import ua.dima.agency.dto.CompanyDto;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.SQLException;
import ua.dima.agency.repositories.*;
import ua.dima.agency.service.CompanyService;
import ua.dima.agency.utils.ParserUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);
    private CompanyRepository companyRepository;
    private TourRepository tourRepository;
    private CountryTourRepository countryTourRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              TourRepository tourRepository,
                              CountryTourRepository countryTourRepository) {
        this.companyRepository = companyRepository;
        this.tourRepository = tourRepository;
        this.countryTourRepository = countryTourRepository;
    }

    @Override
    public CompanyDto get(Long id) {
        Optional<Company> company = companyRepository.get(id);
        if(company.isPresent()) {
            return ParserUtil.parse(company.get());
        }
        LOGGER.warn("Company with id={} doesn't exist.", id);
        throw new NoDataException(String.format("Company with id=%d doesn't exist.", id));
    }

    @Override
    public List<CompanyDto> getAll() {
        List<Company> companies = companyRepository.getAll();
        if(!companies.isEmpty()) {
            return companies.stream().map(ParserUtil::parse).collect(Collectors.toList());
        }
        LOGGER.warn("There aren't any companies in database.");
        throw new NoDataException("There aren't any companies in database.");
    }

    @Override
    public CompanyDto create(CompanyDto companyDTO) {
        Optional<Company> createdCompany = companyRepository.create(ParserUtil.parse(companyDTO));
        if(createdCompany.isPresent()) {
            return ParserUtil.parse(createdCompany.get());
        }
        LOGGER.warn("{} wasn't created.", companyDTO);
        throw new SQLException(String.format("%s wasn't created.", companyDTO));
    }

    @Override
    public CompanyDto update(Long id, CompanyDto companyDTO) {
        Optional<Company> updatedCompany = companyRepository.update(id, ParserUtil.parse(companyDTO));
        if(updatedCompany.isPresent()) {
            return ParserUtil.parse(updatedCompany.get());
        }
        LOGGER.warn("{} wasn't updated.", companyDTO);
        throw new SQLException(String.format("%s wasn't updated.", companyDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            List<Tour> tours = tourRepository.getByCompanyId(id);
            tours.forEach(tour -> countryTourRepository.deleteByTourId(tour.getId()));
            tourRepository.deleteByCompanyId(id);
            companyRepository.delete(id);
        } catch(RuntimeException e) {
            LOGGER.warn("Company with id={} wasn't deleted.", id);
            throw new SQLException(String.format("Company with id=%d wasn't deleted.", id));
        }
    }
}
