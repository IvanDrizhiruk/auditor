package com.auditor.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Document(collection = "inspections")
public class Inspection implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("name")
    private String name;

    @Size(max = 1024)
    @Field("description")
    private String description;

    @NotNull
    @Field("company_id")
    private String companyId;

    @NotNull
    @Field("start_date")
    private Instant startDate;

    @NotNull
    @Field("end_date")
    private Instant endDate;
}
