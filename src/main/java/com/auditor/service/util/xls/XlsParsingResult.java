package com.auditor.service.util.xls;

import com.auditor.domain.InspectionDocument;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class XlsParsingResult {
    InspectionDocument document = new InspectionDocument();
    List<Integer> errorRecordsNumbers = new ArrayList<>();
    int totalRecords;
}
