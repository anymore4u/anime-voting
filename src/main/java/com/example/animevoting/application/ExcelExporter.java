package com.example.animevoting.application;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.example.animevoting.domain.Vote;
import com.example.animevoting.adapters.out.mongodb.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExporter {

    @Autowired
    private VoteRepository voteRepository;

    public void exportVotesToExcel(String filePath) throws IOException {
        List<Vote> votes = voteRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Votes");

        int rowCount = 0;
        Row headerRow = sheet.createRow(rowCount++);
        headerRow.createCell(0).setCellValue("Anime ID");
        headerRow.createCell(1).setCellValue("User ID");
        headerRow.createCell(2).setCellValue("User IP");

        for (Vote vote : votes) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(vote.getAnimeId());
            row.createCell(1).setCellValue(vote.getUserId());
            row.createCell(2).setCellValue(vote.getUserIp());
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}
