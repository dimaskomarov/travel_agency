package ua.dima.agency.service.impl;

import org.springframework.stereotype.Service;
import ua.dima.agency.dto.CountryDTO;
import ua.dima.agency.repositories.*;
import ua.dima.agency.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

    private CompanyRepository companyRepository;
    private TourRepository tourRepository;
    private CountryRepository countryRepository;
    private CountryTourRepository countryTourRepository;
    private TravelTypeRepository travelTypeRepository;

    public CountryServiceImpl(CompanyRepository companyRepository,
                              TourRepository tourRepository,
                              CountryRepository countryRepository,
                              CountryTourRepository countryTourRepository,
                              TravelTypeRepository travelTypeRepository) {
        this.companyRepository = companyRepository;
        this.tourRepository = tourRepository;
        this.countryRepository = countryRepository;
        this.countryTourRepository = countryTourRepository;
        this.travelTypeRepository = travelTypeRepository;
    }

    @Override
    public CountryDTO create(CountryDTO countryDTO) {
        return null;
    }

    @Override
    public CountryDTO get(Long id) {
        return null;
    }

    @Override
    public CountryDTO update(Long id, CountryDTO countryDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
