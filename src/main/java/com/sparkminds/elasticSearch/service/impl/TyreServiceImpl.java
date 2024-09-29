package com.sparkminds.elasticSearch.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.sparkminds.elasticSearch.dto.TyreRequestDto;
import com.sparkminds.elasticSearch.entity.TyreEntity;
import com.sparkminds.elasticSearch.entity.document.TyreDocument;
import com.sparkminds.elasticSearch.repository.TyreRepository;
import com.sparkminds.elasticSearch.repository.document.TyreDocumentRepository;
import com.sparkminds.elasticSearch.service.TyreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TyreServiceImpl implements TyreService {

    private final TyreRepository tyreRepository;
    private final TyreDocumentRepository tyreDocumentRepository;

    @Override
    @Transactional
    public void saveTyre(TyreRequestDto dto) {

        TyreEntity tyre = TyreEntity.builder()
                .name(dto.getName())
                .height(dto.getHeight())
                .width(dto.getWidth())
                .rim(dto.getRim())
                .loadIndex(dto.getLoadIndex())
                .year(dto.getYear())
                .brand(dto.getBrand())
                .pattern(dto.getPattern())
                .build();

        // Save the TyreEntity and retrieve the saved instance
        TyreEntity savedTyre = tyreRepository.save(tyre);

        // Get the ID of the saved entity
        Long tyreId = savedTyre.getId();

        TyreDocument tyreDocument = TyreDocument.builder()
                .id(tyreId)
                .name(dto.getName())
                .height(dto.getHeight())
                .width(dto.getWidth())
                .rim(dto.getRim())
                .loadIndex(dto.getLoadIndex())
                .year(dto.getYear())
                .brand(dto.getBrand())
                .pattern(dto.getPattern())
                .build();
        tyreDocumentRepository.save(tyreDocument);
    }

    @Transactional
    public void importTyresFromCSV(MultipartFile file) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] nextLine;
            // Skip the header line
            csvReader.readNext();
            while ((nextLine = csvReader.readNext()) != null) {
                TyreRequestDto dto = TyreRequestDto.builder()
                        .name(nextLine[0])
                        .width(Integer.parseInt(nextLine[1]))
                        .height(Integer.parseInt(nextLine[2]))
                        .rim(Integer.parseInt(nextLine[3]))
                        .loadIndex(nextLine[4])
                        .year(nextLine[6])  // Assuming year is in column 6
                        .brand(nextLine[5])  // Assuming brand is in column 5
                        .pattern(nextLine[7]) // Assuming pattern is in column 7
                        .build();

                saveTyre(dto);
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<TyreEntity> getTyreById(Long id) {
        return tyreRepository.findById(id);
    }

    @Override
    public Iterable<TyreEntity> getAllTyres() {
        return tyreRepository.findAll();
    }

    @Override
    public void deleteTyreById(Long id) {
        tyreRepository.deleteById(id);
    }
}
