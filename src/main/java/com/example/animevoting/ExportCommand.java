package com.example.animevoting;

import com.example.animevoting.application.ExcelExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ExportCommand implements CommandLineRunner {

    @Autowired
    private ExcelExporter excelExporter;

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0 && args[0].equals("--exportVotes")) {
            String filePath = args.length > 1 ? args[1] : "votes.xlsx";
            excelExporter.exportVotesToExcel(filePath);
            System.out.println("Votes exported to " + filePath);
        }
    }
}
