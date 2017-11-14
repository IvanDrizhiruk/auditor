package com.auditor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection="inspectionDocuments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InspectionDocument {
    @Id
    private String id;

    private List<InspectionDocumentItem> items = new ArrayList<>();

    @Field("company_id")
    private String companyId;

    @Field("inspection_id")
    private String inspectionId;
}
