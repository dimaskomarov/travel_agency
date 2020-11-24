package ua.dima.agency.service.impl;

import org.springframework.stereotype.Service;
import ua.dima.agency.dto.CompanyDto;
import ua.dima.agency.repositories.*;
import ua.dima.agency.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    private TourRepository tourRepository;
    private CountryRepository countryRepository;
    private CountryTourRepository countryTourRepository;
    private TravelTypeRepository travelTypeRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository,
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
    public CompanyDto create(CompanyDto companyDTO) {
        return null;
    }

    @Override
    public CompanyDto get(Long id) {
        return null;
    }

    @Override
    public CompanyDto update(Long id, CompanyDto companyDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
