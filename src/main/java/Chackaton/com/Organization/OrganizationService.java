package Chackaton.com.Organization;

import Chackaton.com.Users.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Transactional
    public Organization createOrganization(Organization organization, Users owner) {
        if (organizationRepository.findByName(organization.getName()).isPresent()) {
            throw new IllegalArgumentException("Компания с таким названием уже существует");
        }

        organization.setOwner(owner);
        return organizationRepository.save(organization);
    }
}
