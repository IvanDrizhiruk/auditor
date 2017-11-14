package com.auditor.service.inspection;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
public class InspectionDTO implements Serializable {
    private String id;

    @NotNull
    @Size(max = 1024)
    private String name;

    @Size(max = 1024)
    private String description;

    @NotNull
    private String companyId;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InspectionDTO inspectionDTO = (InspectionDTO) o;
        if(inspectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inspectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "";
    }
}
