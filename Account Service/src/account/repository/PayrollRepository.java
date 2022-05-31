package account.repository;

import account.model.Payroll;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepository extends CrudRepository<Payroll, Long> {
    Optional<List<Payroll>> findAllByEmployeeIgnoreCaseOrderByPeriodDesc(String employee);
    Optional<List<Payroll>> findAllByEmployeeIgnoreCase(String employee);

    Optional<Payroll> findByEmployeeIgnoreCaseAndPeriod(String employee, String period);

}
