package com.auditor.domain;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Document(collection = "items")
public class Items implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("inspection_id")
    private String inspectionId;

    @Field("list")
    private List<Item> list;

    @Field("count")
    private Integer count;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Items items = (Items) o;
        if (items.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), items.getId());
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
