package ua.dima.agency.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dima.agency.domain.Tour;
import ua.dima.agency.dto.CompanyDto;
import ua.dima.agency.repositories.*;
import ua.dima.agency.service.CompanyService;
import ua.dima.agency.utils.ParserUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
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
    public CompanyDto create(CompanyDto companyDTO) {
        return ParserUtil.parse(companyRepository.create(ParserUtil.parse(companyDTO)).get());
    }

    @Override
    public CompanyDto get(Long id) {
        return ParserUtil.parse(companyRepository.get(id).get());
    }

    @Override
    public List<CompanyDto> getAll() {
        return companyRepository.getAll().stream().map(ParserUtil::parse).collect(Collectors.toList());
    }

    @Override
    public CompanyDto update(Long id, CompanyDto companyDTO) {
        return ParserUtil.parse(companyRepository.update(id, ParserUtil.parse(companyDTO)).get());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        List<Tour> tours = tourRepository.getByCompanyId(id);
        tours.forEach(tour -> countryTourRepository.deleteByTourId(tour.getId()));
        tourRepository.deleteByCompanyId(id);
        companyRepository.delete(id);
    }
}
