package ua.dima.agency.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dima.agency.dto.CompanyDto;
import ua.dima.agency.repositories.*;
import ua.dima.agency.service.CompanyService;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);
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
    @Transactional
    public CompanyDto create(CompanyDto companyDTO) {
        companyRepository.create(companyDTO)
    }

    @Override
    @Transactional
    public CompanyDto get(Long id) {
        return null;
    }

    @Override
    @Transactional
    public List<CompanyDto> getAll() {
        return null;
    }

    @Override
    @Transactional
    public CompanyDto update(Long id, CompanyDto companyDTO) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {

    }
}
