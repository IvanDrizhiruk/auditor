package ua.dp.auditor.repository;

import ua.dp.auditor.domain.Employee;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Employee entity.
 */
@SuppressWarnings("unused")
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

//    @Query(value="select e from EMPLOYEE e " +
//        "where e.articul = query " +
//        "or e.jhi_label like:query " +
//        "or e.barcode = query")
//    List<Employee> findAllByQuery(String query);


    List<Employee> findByLabelIgnoreCaseContaining(String query);

    List<Employee> findByArticulOrLabelIgnoreCaseContainingOrBarcode(String articul, String labelPart, String barcode);
}
