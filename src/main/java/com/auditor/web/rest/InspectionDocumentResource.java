package com.auditor.web.rest;

import com.auditor.service.document.InspectionDocumentService;
import com.auditor.service.util.xls.XlsParser;
import com.auditor.service.util.xls.XlsParsingResult;
import com.auditor.service.util.xls.XlsParsingResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class InspectionDocumentResource {
    @Autowired
    private InspectionDocumentService documentService;

    @PostMapping("/companies/{companyId}/inspections/{inspectionId}")
    public XlsParsingResultDTO uploadDocument(@RequestParam("file") MultipartFile file,
                                              @PathVariable("companyId") String companyId,
                                              @PathVariable("inspectionId") String inspectionId) {
        XlsParsingResult result = XlsParser.parseFile(file);

        if (result.getErrorRecordsNumbers().isEmpty()) {
            result.getDocument().setCompanyId(companyId);
            result.getDocument().setInspectionId(inspectionId);

            documentService.insert(result.getDocument());
        }

        return new XlsParsingResultDTO(result);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception e) {
        return ResponseEntity.status(500).build();
    }
}
