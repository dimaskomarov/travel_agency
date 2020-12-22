package ua.dima.agency.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dima.agency.domain.Company;
import ua.dima.agency.dto.CompanyDto;
import ua.dima.agency.dto.TourDto;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.SQLException;
import ua.dima.agency.repositories.CompanyRepository;
import ua.dima.agency.repositories.CountryTourRepository;
import ua.dima.agency.service.CompanyService;
import ua.dima.agency.service.TourService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);
    private final CountryTourRepository countryTourRepository;
    private final CompanyRepository companyRepository;
    private final TourService tourService;

    public CompanyServiceImpl(CountryTourRepository countryTourRepository,
                              CompanyRepository companyRepository,
                              TourService tourService) {
        this.countryTourRepository = countryTourRepository;
        this.companyRepository = companyRepository;
        this.tourService = tourService;
    }

    @Override
    public CompanyDto get(Long id) {
        Optional<Company> company = companyRepository.get(id);
        if(company.isPresent()) {
            return getCompanyWithTours(company.get());
        }
        LOGGER.debug("Company with id={} doesn't exist.", id);
        throw new NoDataException(String.format("Company with id=%d doesn't exist.", id));
    }

    @Override
    public List<CompanyDto> getAll() {
        List<Company> companies = companyRepository.getAll();
        if(!companies.isEmpty()) {
            return companies.stream().map(this::getCompanyWithTours).collect(Collectors.toList());
        }
        LOGGER.debug("There aren't any companies in database.");
        throw new NoDataException("There aren't any companies in database.");
    }

    @Override
    @Transactional
    public CompanyDto create(CompanyDto companyDTO) {
        Optional<Company> createdCompany = companyRepository.create(Company.parse(companyDTO));

        if(createdCompany.isPresent()) {
            companyDTO.getToursDto()
                    .forEach(tourDto -> tourService.create(createdCompany.get().getId(), tourDto));

            return getCompanyWithTours(createdCompany.get());
        }
        LOGGER.debug("{} wasn't created.", companyDTO);
        throw new SQLException(String.format("%s wasn't created.", companyDTO));
    }

    @Override
    @Transactional
    public CompanyDto update(Long id, CompanyDto companyDTO) {
        checkForExistence(id);

        deleteToursOfCompany(id);
        Optional<Company> updatedCompany = companyRepository.update(id, Company.parse(companyDTO));

        if(updatedCompany.isPresent()) {
            companyDTO.getToursDto().forEach(tourDto -> tourService.create(id, tourDto));
            return getCompanyWithTours(updatedCompany.get());
        }
        LOGGER.debug("{} wasn't updated.", companyDTO);
        throw new SQLException(String.format("%s wasn't updated.", companyDTO));
    }

    private void deleteToursOfCompany(Long companyId) {
        get(companyId).getToursDto()
                .forEach(tourDto -> countryTourRepository.deleteByTourId(tourDto.getId()));
        tourService.delete(companyId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        checkForExistence(id);

        try {
            List<TourDto> toursDto = tourService.getAll(id);
            toursDto.forEach(tourDto -> countryTourRepository.deleteByTourId(tourDto.getId()));
            tourService.delete(id);
            companyRepository.delete(id);
        } catch(SQLException e) {
            LOGGER.debug("Company with id={} wasn't deleted.", id);
            throw new SQLException(String.format("Company with id=%d wasn't deleted.", id));
        }
    }

    private void checkForExistence(Long companyId) {
        Optional<Company> company = companyRepository.get(companyId);
        if(company.isEmpty()) {
            LOGGER.debug("The company with id={} doesn't exist", companyId);
            throw new NoDataException(String.format("The company with id=%d doesn't exist", companyId));
        }
    }

    public CompanyDto getCompanyWithTours(Company company) {
        List<TourDto> toursDto = tourService.getAll(company.getId());
        return CompanyDto.parse(company, toursDto);
    }
}
