package com.auditor.service.util.xls;

import com.auditor.domain.InspectionDocumentItem;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;

@Component
public class XlsParser {
    public static XlsParsingResult parseFile(MultipartFile file) {
        XlsParsingResult result = new XlsParsingResult();

        HSSFWorkbook wb = null;

        try {
            wb = new HSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            return result;
        }

        Iterator<HSSFRow> rowIterator = wb.getSheetAt(0).iterator();

        int currentRowIndex = 0;

        while(rowIterator.hasNext()) {
            HSSFRow row = rowIterator.next();

            if(currentRowIndex == 0) {
                currentRowIndex++;
                continue;
            }

            currentRowIndex++;

            InspectionDocumentItem documentItem = parseDocumentItem(row);

            if (null == documentItem) {
                result.errorRecordsNumbers.add(currentRowIndex);
            } else {
                result.document.getItems().add(documentItem);
            }
        }

        result.totalRecords = currentRowIndex;

        return result;
    }

    private static InspectionDocumentItem parseDocumentItem(HSSFRow row) {
        try {
            return new InspectionDocumentItem(
                row.getCell(0).getRichStringCellValue().getString(),
                row.getCell(1).getRichStringCellValue().getString(),
                Double.valueOf(row.getCell(2).getNumericCellValue()).intValue(),
                row.getCell(3).getRichStringCellValue().getString());
        } catch (Exception e) {
            return null;
        }
    }
}
