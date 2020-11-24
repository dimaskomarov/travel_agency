package ua.dima.agency.service.impl;

import org.springframework.stereotype.Service;
import ua.dima.agency.dto.TourDto;
import ua.dima.agency.repositories.*;
import ua.dima.agency.service.TourService;

@Service
public class TourServiceImpl implements TourService {

    private CompanyRepository companyRepository;
    private TourRepository tourRepository;
    private CountryRepository countryRepository;
    private CountryTourRepository countryTourRepository;
    private TravelTypeRepository travelTypeRepository;

    public TourServiceImpl(CompanyRepository companyRepository,
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
    public TourDto create(TourDto tourDTO) {
        return null;
    }

    @Override
    public TourDto get(Long id) {
        return null;
    }

    @Override
    public TourDto update(Long id, TourDto tourDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
