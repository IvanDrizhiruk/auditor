package ua.dp.auditor.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "articul")
    private String articul;

    @Column(name = "jhi_label")
    private String label;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "expected_number")
    private Long expectedNumber;

    @Column(name = "audited_number")
    private Long auditedNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticul() {
        return articul;
    }

    public Employee articul(String articul) {
        this.articul = articul;
        return this;
    }

    public void setArticul(String articul) {
        this.articul = articul;
    }

    public String getLabel() {
        return label;
    }

    public Employee label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBarcode() {
        return barcode;
    }

    public Employee barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Long getExpectedNumber() {
        return expectedNumber;
    }

    public Employee expectedNumber(Long expectedNumber) {
        this.expectedNumber = expectedNumber;
        return this;
    }

    public void setExpectedNumber(Long expectedNumber) {
        this.expectedNumber = expectedNumber;
    }

    public Long getAuditedNumber() {
        return auditedNumber;
    }

    public Employee auditedNumber(Long auditedNumber) {
        this.auditedNumber = auditedNumber;
        return this;
    }

    public void setAuditedNumber(Long auditedNumber) {
        this.auditedNumber = auditedNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        if (employee.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", articul='" + articul + "'" +
            ", label='" + label + "'" +
            ", barcode='" + barcode + "'" +
            ", expectedNumber='" + expectedNumber + "'" +
            ", auditedNumber='" + auditedNumber + "'" +
            '}';
    }
}
