package com.auditor.service.dto;


import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemsDTO implements Serializable {
    private String id;

    @NotNull
    private String inspectionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemsDTO itemsDTO = (ItemsDTO) o;
        if(itemsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemsDTO.getId());
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
