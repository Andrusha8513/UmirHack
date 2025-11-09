package Chackaton.com.Warehouse;

import Chackaton.com.Organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse , Long> {

    boolean existsByNameAndOrganization(String name, Organization organization);

    int countByOrganizationId(Long organizationId);
    int countByOrganization(Organization organization);


}
