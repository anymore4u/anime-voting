package com.example.animevoting;

import com.example.animevoting.application.ExcelExporter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ExportCommandTests {

    @Mock
    private ExcelExporter excelExporter;

    @InjectMocks
    private ExportCommand exportCommand;

    @Test
    public void shouldExportVotesToDefaultFileWhenOnlyExportVotesArgumentIsProvided() throws Exception {
        exportCommand.run("--exportVotes");
        verify(excelExporter).exportVotesToExcel("votes.xlsx");
    }

    @Test
    public void shouldExportVotesWhenArgumentIsExportVotes() throws Exception {
        exportCommand.run("--exportVotes", "customVotes.xlsx");
        verify(excelExporter).exportVotesToExcel("customVotes.xlsx");
    }

    @Test
    public void shouldNotExportVotesWhenNoArgumentsAreProvided() throws Exception {
        exportCommand.run();
        verify(excelExporter, never()).exportVotesToExcel(anyString());
    }

    @Test
    public void shouldNotExportVotesWhenFirstArgumentIsNotExportVotes() throws Exception {
        exportCommand.run("someOtherArgument");
        verify(excelExporter, never()).exportVotesToExcel(anyString());
    }
}
