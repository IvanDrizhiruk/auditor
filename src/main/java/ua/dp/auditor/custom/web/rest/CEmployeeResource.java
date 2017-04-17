package ua.dp.auditor.custom.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dp.auditor.domain.Employee;
import ua.dp.auditor.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Employee.
 */
@RestController
@RequestMapping("/api")
public class CEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(CEmployeeResource.class);

    private final EmployeeRepository employeeRepository;

    public CEmployeeResource(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    /**
     * GET  /employees/find/:query : get the "query" employee.
     *
     * @param query the query of the employee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employee, or with status 404 (Not Found)
     */
    @GetMapping("/employees/find/{query}")
    @Timed
    public ResponseEntity<List<Employee>> findEmployee(@PathVariable String query) {
        log.debug("REST request to find Employee : {}", query);


        List<Employee> employees = employeeRepository.findByArticulOrLabelIgnoreCaseContainingOrBarcode(query, query, query);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employees));
    }
}
