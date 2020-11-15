package ua.dima.agency.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.dima.agency.dto.Company;
import ua.dima.agency.repositories.CompanyRepository;
import ua.dima.agency.repositories.CountryRepository;
import ua.dima.agency.repositories.TourRepository;
import ua.dima.agency.repositories.TravelTypeRepository;
import ua.dima.agency.service.AgencyService;

import java.util.List;
import java.util.Optional;

public class AgencyServiceImpl implements AgencyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AgencyService.class);

    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;
    private final TravelTypeRepository travelTypeRepository;
    private final TourRepository tourRepository;

    public AgencyServiceImpl(CompanyRepository companyRepository,
                             TourRepository tourRepository,
                             CountryRepository countryRepository,
                             TravelTypeRepository travelTypeRepository) {
        this.companyRepository = companyRepository;
        this.tourRepository = tourRepository;
        this.countryRepository = countryRepository;
        this.travelTypeRepository = travelTypeRepository;
    }

    @Override
    public Optional<List<Company>> getAll() {
        return Optional.empty();
    }

    @Override
    public Optional<Company> getOne(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Company> create(Company company) {
        return Optional.empty();
    }

    @Override
    public Optional<Company> update(Long id, Company company) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
