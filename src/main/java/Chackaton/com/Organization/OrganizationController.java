package Chackaton.com.Organization;

import Chackaton.com.Users.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService){
        this.organizationService = organizationService;
    }

    public ResponseEntity<?> createOrganization(@RequestBody Organization organization , Users users){

       try {
           Organization organization1 = organizationService.createOrganization(organization , users);
           return ResponseEntity.ok(organization1);
       }catch (Exception e){
           return ResponseEntity.badRequest().build();
       }


    }
}
