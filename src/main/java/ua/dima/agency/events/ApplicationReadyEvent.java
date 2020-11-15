package ua.dima.agency.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import ua.dima.agency.TravelAgencyApplication;
import ua.dima.agency.dto.Company;
import ua.dima.agency.repositories.CompanyRepository;

import java.util.Arrays;


@Configuration
public class ApplicationReadyEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEvent.class);

    private final CompanyRepository companyRepository;

    public ApplicationReadyEvent(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
        up();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void up() {
        Company defaultComapny = Company.createCompany()
                .withName("-1")
                .withAddress("-1")
                .withAge(-1)
                .build();

        Company travelForEveryone = Company.createCompany()
                .withName("Travel for everyone")
                .withAddress("Kharkov, st. Free 101")
                .withAge(10)
                .build();

        var companyNumberOne = companyRepository.getOne(1L).orElse(defaultComapny);
        LOGGER.info("First company: " + companyNumberOne);

        var allCompany = companyRepository.getAll().orElse(Arrays.asList(defaultComapny));
        LOGGER.info("All companies: " + allCompany);

        var createdCompany = companyRepository.create(travelForEveryone).orElse(defaultComapny);
        LOGGER.info("New company has been created: " + createdCompany);

        createdCompany.setAge(10_000);
        var updatedCompany = companyRepository.update(createdCompany.getId(), createdCompany).orElse(defaultComapny);
        LOGGER.info("Updated company: " + updatedCompany);

        companyRepository.delete(createdCompany.getId());
        LOGGER.info("Removed company: " + createdCompany);
    }
}
