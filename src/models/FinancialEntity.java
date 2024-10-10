package models;

import java.math.BigDecimal;

/**
 * Abstract class representing a financial entity with basic attributes.
 */
public abstract class FinancialEntity {
    private String entityId;
    private String name;

    /**
     * Constructor for FinancialEntity.
     *
     * @param entityId Unique identifier for the financial entity.
     * @param name     Name of the financial entity.
     */
    public FinancialEntity(String entityId, String name) {
        this.entityId = entityId;
        this.name = name;
    }

    // Getters and Setters
    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Abstract method to calculate the balance of the financial entity.
     *
     * @return The calculated balance as a BigDecimal.
     */
    public abstract BigDecimal calculateBalance();

    @Override
    public String toString() {
        return "FinancialEntity{" +
                "entityId='" + entityId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
