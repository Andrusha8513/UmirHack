package Chackaton.com.Warehouse;

import Chackaton.com.Organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse , Long> {

    boolean existsByNameAndOrganization(String name, Organization organization);

    int countByOrganizationId(Long organizationId);
    int countByOrganization(Organization organization);

    List<Warehouse> findByOrganization(Organization organization);


}
