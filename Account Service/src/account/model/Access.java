package account.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Access {
    @NotEmpty
    private String user;

    @NotNull
    private String operation;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }



}
