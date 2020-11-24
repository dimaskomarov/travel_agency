package ua.dima.agency.service.impl;

import org.springframework.stereotype.Service;
import ua.dima.agency.domain.Country;
import ua.dima.agency.dto.CountryDto;
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
    public CountryDto create(CountryDto countryDTO) {
//        Country country = countryRepository.create();
        return null;
    }

    @Override
    public CountryDto get(Long id) {
        return null;
    }

    @Override
    public CountryDto update(Long id, CountryDto countryDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
