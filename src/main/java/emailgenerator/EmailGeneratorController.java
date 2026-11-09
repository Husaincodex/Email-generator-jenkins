package emailgenerator;

import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmailGeneratorController {

    private static final Set<String> DEPARTMENTS =
            Set.of("C++", "Java", "Python", "None");

    @PostMapping("/generate")
    public ResponseEntity<?> generateEmail(@RequestBody EmployeeRequest request) {

        if (request.firstname() == null || request.firstname().isBlank()
                || request.lastname() == null || request.lastname().isBlank()
                || request.department() == null
                || !DEPARTMENTS.contains(request.department())) {

            return ResponseEntity.badRequest().body(
                    Map.of("error", "Please enter valid employee details.")
            );
        }

        if (request.firstname().length() > 50
                || request.lastname().length() > 50) {

            return ResponseEntity.badRequest().body(
                    Map.of("error", "Names must be 50 characters or fewer.")
            );
        }

        EmailBackProgram employee = new EmailBackProgram(
                request.firstname(),
                request.lastname(),
                request.department()
        );

        return ResponseEntity.ok(
                new EmployeeResponse(
                        employee.getDisplayName(),
                        employee.getEmail(),
                        employee.getPassword(),
                        employee.getMailboxCapacity() + "mb"
                )
        );
    }

    public record EmployeeRequest(
            String firstname,
            String lastname,
            String department
    ) {}

    public record EmployeeResponse(
            String displayName,
            String companyEmail,
            String password,
            String mailboxCapacity
    ) {}
}
