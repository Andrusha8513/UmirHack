package Chackaton.com.Organization;

import Chackaton.com.Users.UserRepository;
import Chackaton.com.Users.Users;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final OrganizationMembershipRepository organizationMembershipRepository;

    public OrganizationService(OrganizationRepository organizationRepository,
                               UserRepository userRepository,
                               OrganizationMembershipRepository organizationMembershipRepository) {
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.organizationMembershipRepository = organizationMembershipRepository;
    }

    @Transactional
    public Organization createOrganization(Organization organization, Users users) {
        if (organizationRepository.findByName(organization.getName()).isPresent()) {
            throw new IllegalArgumentException("Компания с таким названием уже существует");
        }

//        organization.setOwner(users);

        organization.setCreatedAt(LocalDateTime.now());
        Organization saveOrganization = organizationRepository.save(organization);

        OrganizationMembership membership = new OrganizationMembership();
        membership.setUser(users);
        membership.setOrganization(saveOrganization);
        membership.setRoles(Set.of(OrganizationRole.ORG_ADMIN));
        organizationMembershipRepository.save(membership);

        return saveOrganization;
    }

    public List<Organization> getAllOrganization(){
        return organizationRepository.findAll();
    }
}
