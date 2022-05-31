package account.controller;

import account.model.Payroll;
import account.model.User;
import account.service.PayrollService;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Validated
@RestController
public class EmployeeController {
    @Autowired
    UserService userservice;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PayrollService payrollService;

    @GetMapping(value = "api/empl/payment")
    public List<Map<String, String>> checkPayment(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CopyOnWriteArrayList<Map<String, String>> userPayrolls = new CopyOnWriteArrayList<>();
        List<Payroll> payrolls = payrollService.loadAllPayrollsByEmployee(user.getEmail());
        if (payrolls.isEmpty()){
            return Collections.emptyList();
        }
        for(Payroll payroll : payrolls)
        {
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("name", user.getName());
            map.put("lastname", user.getLastname());
            map.put("period", String.format("%s-%s",
                            Month.of(Integer.parseInt(payroll.getPeriod().substring(0,2)))
                                    .getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                            payroll.getPeriod().substring(3)));
            map.put("salary", String.format("%d dollar(s) %d cent(s)",
                    payroll.getSalary()/100,
                    payroll.getSalary()%100));

            userPayrolls.add(map);
        }
        return userPayrolls;
    }

    @GetMapping(value = "api/empl/payment", params = "period")
    public Map<String, String> checkPaymentByPeriod(@RequestParam String period){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Payroll payroll = payrollService.loadPayrollByEmployeeAndPeriod(user.getEmail(), period);
        return Map.of(
                "name", user.getName(),
                "lastname", user.getLastname(),
                "period", String.format("%s-%s", Month.of(Integer.parseInt(period.substring(0,2)))
                                                    .getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                                                     period.substring(3)),
                "salary", String.format("%d dollar(s) %d cent(s)",
                        payroll.getSalary()/100,
                        payroll.getSalary()%100
                ));

    }
}
