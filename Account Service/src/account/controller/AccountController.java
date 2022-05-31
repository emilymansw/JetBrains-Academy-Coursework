package account.controller;

import account.model.Payroll;
import account.model.User;
import account.service.PayrollService;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Validated
@RestController
public class AccountController {
    @Autowired
    UserService userservice;

    @Autowired
    PayrollService payrollService;

    @PostMapping("api/acct/payments")
    public Map<String, String> uploadPayrolls(@RequestBody @Valid List<Payroll> payrolls){
        for (Payroll payroll : payrolls) {
            payrollService.save(payroll);
        }
        return Map.of("status", "Added successfully!");
    }

    @PutMapping("api/acct/payments")
    public Map<String, String> updatePayrolls(@RequestBody @Valid Payroll payroll){
        Payroll toUpdate = payrollService.loadPayrollByEmployeeAndPeriod(payroll.getEmployee(), payroll.getPeriod());
        toUpdate.setSalary(payroll.getSalary());
        payrollService.save(toUpdate);
        return Map.of("status", "Updated successfully!");
    }
}
