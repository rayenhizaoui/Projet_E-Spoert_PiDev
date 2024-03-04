package Entites;

import java.util.Date;

public class Historique {
    private int id;
    private String operationType;
    private String description;
    private Date operationDate;

    public Historique(int id, String operationType, String description, Date operationDate) {
        this.id = id;
        this.operationType = operationType;
        this.description = description;
        this.operationDate = operationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    @Override
    public String toString() {
        return "Historique{" +
                "id=" + id +
                ", operationType='" + operationType + '\'' +
                ", description='" + description + '\'' +
                ", operationDate=" + operationDate +
                '}';
    }
}
