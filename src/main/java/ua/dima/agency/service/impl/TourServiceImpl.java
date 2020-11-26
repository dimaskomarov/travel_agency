package ua.dima.agency.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dima.agency.domain.Country;
import ua.dima.agency.domain.Tour;
import ua.dima.agency.dto.TourDto;
import ua.dima.agency.repositories.CountryRepository;
import ua.dima.agency.repositories.CountryTourRepository;
import ua.dima.agency.repositories.TourRepository;
import ua.dima.agency.repositories.TravelTypeRepository;
import ua.dima.agency.service.TourService;
import ua.dima.agency.utils.ParserUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {
    private CountryTourRepository countryTourRepository;
    private TravelTypeRepository travelTypeRepository;
    private CountryRepository countryRepository;
    private TourRepository tourRepository;

    public TourServiceImpl(CountryTourRepository countryTourRepository,
                           TravelTypeRepository travelTypeRepository,
                           CountryRepository countryRepository,
                           TourRepository tourRepository) {
        this.countryTourRepository = countryTourRepository;
        this.travelTypeRepository = travelTypeRepository;
        this.countryRepository = countryRepository;
        this.tourRepository = tourRepository;
    }

    @Override
    @Transactional
    public TourDto create(TourDto tourDTO, Long companyId) {
        List<Country> countries = tourDTO.getCountiesDto().stream().map(ParserUtil::parse).collect(Collectors.toList());
        List<Country> createdCountries = countryRepository.createAll(countries);

        createdCountries.forEach(country -> countryTourRepository.create(tourDTO.getId(), country.getId()));

        travelTypeRepository.create(ParserUtil.parse(tourDTO.getTravelTypeDto()));

        Tour tour = ParserUtil.parse(tourDTO, companyId);
        Optional<Tour> tourOptional = tourRepository.create(tour);

        return ParserUtil.parse(tourOptional.get());
    }

    @Override
    public TourDto get(Long id) {
        Optional<Tour> tourOptional = tourRepository.get(id);
        return ParserUtil.parse(tourOptional.get());
    }

    @Override
    public List<TourDto> getAll() {
        List<Tour> tours = tourRepository.getAll();
        return tours.stream().map(ParserUtil::parse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TourDto update(TourDto tourDTO, Long countryId) {
        Optional<Tour> tourOptional = tourRepository.update(tourDTO.getId(), ParserUtil.parse(tourDTO, countryId));
        return ParserUtil.parse(tourOptional.get());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        countryTourRepository.deleteByTourId(id);
        tourRepository.delete(id);
    }
}
