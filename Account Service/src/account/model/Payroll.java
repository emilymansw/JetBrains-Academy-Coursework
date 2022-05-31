package account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Entity
@Table(uniqueConstraints={
@UniqueConstraint(name = "periodAndEmployee", columnNames = {"period", "employee"})})
public class Payroll implements Comparable<Payroll> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Min(value = 0,message = "Salary must be non negative!")
    private long salary;

    @Pattern(regexp = "(0[1-9]|1[0-2])-\\d\\d\\d\\d",message = "Wrong date!")
    private String period;

    private String employee;

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }


    @Override
    public int compareTo(Payroll payroll) {
        return (YearMonth.parse(getPeriod(), DateTimeFormatter.ofPattern("MM-yyyy"))
                .compareTo(YearMonth.parse(payroll.getPeriod(), DateTimeFormatter.ofPattern("MM-yyyy"))));
    }
}
