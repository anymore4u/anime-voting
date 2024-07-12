package com.example.animevoting.application;

import com.example.animevoting.domain.Anime;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.example.animevoting.domain.Vote;
import com.example.animevoting.adapters.out.mongodb.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import com.example.animevoting.adapters.out.mongodb.AnimeRepository;

@Service
public class VoteExportService {

    private final VoteRepository voteRepository;
    private final AnimeRepository animeRepository;

    @Autowired
    public VoteExportService(VoteRepository voteRepository, AnimeRepository animeRepository) {
        this.voteRepository = voteRepository;
        this.animeRepository = animeRepository;
    }

    public void exportVotesToExcel(String templatePath, String filePath) throws IOException {
        // Carregar o template de Excel
        FileInputStream fis = new FileInputStream(templatePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        // Buscar todos os votos
        List<Vote> votes = voteRepository.findAll();
        int rowCount = 2; // Começar na linha 3 (índice 2)

        for (Vote vote : votes) {
            Row row = sheet.createRow(rowCount++);

            // Buscar informações adicionais do anime
            Anime anime = animeRepository.findById(vote.getAnimeId()).orElse(null);
            if (anime != null) {
                Cell cell1 = row.createCell(1); // Coluna B
                cell1.setCellValue(anime.getTitle());

                Cell cell2 = row.createCell(2); // Coluna C
                cell2.setCellValue(vote.getName());
            }
        }

        // Salvar o arquivo preenchido
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}

