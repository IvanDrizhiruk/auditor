package com.auditor.service.dto;


import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InspectionDTO implements Serializable {
    private String id;

    @NotNull
    private String name;

    @NotNull
    @Size(max = 250)
    private String description;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

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
        return new Gson().toJson(this);
    }
}
