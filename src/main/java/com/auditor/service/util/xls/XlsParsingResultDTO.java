package com.auditor.service.util.xls;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class XlsParsingResultDTO {
    private List<Integer> errorRecordsNumbers;
    private int totalRecords;

    public XlsParsingResultDTO(XlsParsingResult parsingResult) {
        this.errorRecordsNumbers = parsingResult.getErrorRecordsNumbers();
        this.totalRecords = parsingResult.getTotalRecords();
    }
}
