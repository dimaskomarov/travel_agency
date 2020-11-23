package ua.dima.agency.service.impl;

import org.springframework.stereotype.Service;
import ua.dima.agency.dto.TravelTypeDTO;
import ua.dima.agency.repositories.*;
import ua.dima.agency.service.TravelTypeService;

@Service
public class TravelTypeServiceImpl implements TravelTypeService {

    private CompanyRepository companyRepository;
    private TourRepository tourRepository;
    private CountryRepository countryRepository;
    private CountryTourRepository countryTourRepository;
    private TravelTypeRepository travelTypeRepository;

    public TravelTypeServiceImpl(CompanyRepository companyRepository,
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
    public TravelTypeDTO create(TravelTypeDTO travelTypeDTO) {
        return null;
    }

    @Override
    public TravelTypeDTO get(Long id) {
        return null;
    }

    @Override
    public TravelTypeDTO update(Long id, TravelTypeDTO travelTypeDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
