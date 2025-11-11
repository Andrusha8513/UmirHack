package Chackaton.com.Organization;

import Chackaton.com.Users.UserRepository;
import Chackaton.com.Users.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/organization")
public class OrganizationController {

    private final OrganizationService organizationService;
private final UserRepository userRepository;
    public OrganizationController(OrganizationService organizationService ,
                                  UserRepository userRepository){
        this.organizationService = organizationService;
        this.userRepository = userRepository;
    }
    @PostMapping("/createOrganization")
    public ResponseEntity<?> createOrganization(@RequestBody Organization organization) {
        try {
            // Получаем текущего аутентифицированного пользователя из контекста безопасности
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            Users currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

            Organization organization1 = organizationService.createOrganization(organization, currentUser);
            return ResponseEntity.ok(organization1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //збс
    @GetMapping("/all")
    public ResponseEntity<List<Organization>> getAllOrganization(){
        List<Organization>  organizations = organizationService.getAllOrganization();
       return ResponseEntity.ok(organizations);
    }

    }

