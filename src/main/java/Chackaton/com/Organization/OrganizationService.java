package Chackaton.com.Organization;

import Chackaton.com.Users.UserRepository;
import Chackaton.com.Users.Users;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private  final UserRepository userRepository;
    public OrganizationService(OrganizationRepository organizationRepository,
                               UserRepository userRepository) {
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Organization createOrganization(Organization organization, Users users ){
        if (organizationRepository.findByName(organization.getName()).isPresent()) {
            throw new IllegalArgumentException("Компания с таким названием уже существует");
        }

        organization.setOwner(users);
        organization.setCreatedAt(LocalDateTime.now());
        return organizationRepository.save(organization);
    }
}
