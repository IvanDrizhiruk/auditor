package com.auditor.domain;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Document(collection = "items")
public class InspectionItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @Field("code")
    private String code;
    @Field("description")
    private String description;
    @Field("expected_count")
    private Double expectedCount;
    @Field("actual_count")
    private Double actualCount;
    @NotNull
    @Field("inspection_id")
    private String inspectionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InspectionItem inspectionItems = (InspectionItem) o;
        if (inspectionItems.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inspectionItems.getId());
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
