package ua.dima.agency.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dima.agency.domain.Company;
import ua.dima.agency.domain.Tour;
import ua.dima.agency.dto.CompanyDto;
import ua.dima.agency.dto.TourDto;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.exceptions.SQLException;
import ua.dima.agency.repositories.*;
import ua.dima.agency.service.CompanyService;
import ua.dima.agency.utils.CreatorMissingRecords;
import ua.dima.agency.utils.Parser;

import java.util.ArrayList;
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
            return Parser.parse(company.get());
        }
        LOGGER.warn("Company with id={} doesn't exist.", id);
        throw new NoDataException(String.format("Company with id=%d doesn't exist.", id));
    }

    @Override
    public List<CompanyDto> getAll() {
        List<Company> companies = companyRepository.getAll();
        if(!companies.isEmpty()) {
            return companies.stream().map(Parser::parse).collect(Collectors.toList());
        }
        LOGGER.warn("There aren't any companies in database.");
        throw new NoDataException("There aren't any companies in database.");
    }

    @Override
    @Transactional
    public CompanyDto create(CompanyDto companyDTO) {
        Optional<Company> createdCompany = companyRepository.create(Parser.parse(companyDTO));

        if(createdCompany.isPresent()) {
            companyDTO.getToursDto()
                    .forEach(tourDto -> createTour(createdCompany.get().getId(), tourDto));

            return Parser.parse(createdCompany.get());
        }
        LOGGER.warn("{} wasn't created.", companyDTO);
        throw new SQLException(String.format("%s wasn't created.", companyDTO));
    }

    @Override
    @Transactional
    public CompanyDto update(Long id, CompanyDto companyDTO) {
        deleteToursOfCompany(id);
        Optional<Company> updatedCompany = companyRepository.update(id, Parser.parse(companyDTO));

        if(updatedCompany.isPresent()) {
            companyDTO.getToursDto().forEach(tourDto -> createTour(id, tourDto));
            return Parser.parse(updatedCompany.get());
        }
        LOGGER.warn("{} wasn't updated.", companyDTO);
        throw new SQLException(String.format("%s wasn't updated.", companyDTO));
    }

    private void createTour(Long companyId, TourDto tourDto) {
        CreatorMissingRecords.createMissingCountries(tourDto.getCountiesDto());
        CreatorMissingRecords.createMissingTravelTypes(tourDto.getTravelTypeDto());

        Optional<Tour> createdTour = tourRepository.create(Parser.parse(tourDto, companyId));
        createdTour.ifPresent(tour -> CreatorMissingRecords.createMissingCountryTour(tourDto.getCountiesDto(), tour.getId()));
    }

    private void deleteToursOfCompany(Long companyId) {
        get(companyId).getToursDto().stream()
                .forEach(tourDto -> countryTourRepository.deleteByTourId(tourDto.getId()));
        tourRepository.deleteByCompanyId(companyId);
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
