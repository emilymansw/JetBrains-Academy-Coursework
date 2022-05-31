package account.service;

import account.model.Payroll;
import account.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PayrollService {
    @Autowired
    PayrollRepository payrollRepo;

    @Transactional
    public Payroll save(Payroll toSave){
        return payrollRepo.save(toSave);
    }

    public Payroll loadPayrollByEmployeeAndPeriod(String employee, String period)  {
        Optional<Payroll> payroll = payrollRepo.findByEmployeeIgnoreCaseAndPeriod(employee, period);
        if (payroll.isPresent()){
            return payroll.get();
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Payroll not found");
        }
    }

    public List<Payroll> loadAllPayrollsByEmployee(String employee)  {
        Optional<List<Payroll>> payrolls = payrollRepo.findAllByEmployeeIgnoreCaseOrderByPeriodDesc(employee);
        if (payrolls.isPresent()){
            return payrolls.get();
        }else{
            return Collections.emptyList();
        }
    }
}
