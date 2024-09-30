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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public void bulkUpload(List<TyreRequestDto> dtos) {
        List<TyreEntity> tyreEntities = dtos.stream()
                .map(dto -> TyreEntity.builder()
                        .name(dto.getName())
                        .height(dto.getHeight())
                        .width(dto.getWidth())
                        .rim(dto.getRim())
                        .loadIndex(dto.getLoadIndex())
                        .year(dto.getYear())
                        .brand(dto.getBrand())
                        .pattern(dto.getPattern())
                        .build())
                .collect(Collectors.toList());

        // Bulk save the TyreEntities and retrieve the saved instances
        List<TyreEntity> savedTyres = tyreRepository.saveAll(tyreEntities);

        // Prepare documents for bulk insertion into Elasticsearch
        List<TyreDocument> tyreDocuments = savedTyres.stream()
                .map(tyre -> TyreDocument.builder()
                        .id(tyre.getId())
                        .name(tyre.getName())
                        .height(tyre.getHeight())
                        .width(tyre.getWidth())
                        .rim(tyre.getRim())
                        .loadIndex(tyre.getLoadIndex())
                        .year(tyre.getYear())
                        .brand(tyre.getBrand())
                        .pattern(tyre.getPattern())
                        .build())
                .collect(Collectors.toList());

        // Bulk save TyreDocuments to Elasticsearch
        tyreDocumentRepository.saveAll(tyreDocuments);
    }

    @Transactional
    public void importTyresFromCSV(MultipartFile file) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] nextLine;
            List<TyreRequestDto> tyreDtos = new ArrayList<>();
            int batchSize = 200; // Adjust the batch size as needed

            // Skip the header line
            csvReader.readNext();
            while ((nextLine = csvReader.readNext()) != null) {
                TyreRequestDto dto = TyreRequestDto.builder()
                        .name(nextLine[0])
                        .width(Integer.parseInt(nextLine[1]))
                        .height(Integer.parseInt(nextLine[2]))
                        .rim(Integer.parseInt(nextLine[3]))
                        .loadIndex(nextLine[4])
                        .year(nextLine[7])  // Assuming year is in column 6
                        .brand(nextLine[6])  // Assuming brand is in column 5
                        .pattern(nextLine[5]) // Assuming pattern is in column 7
                        .build();

                tyreDtos.add(dto);

                // Process in batches
                if (tyreDtos.size() == batchSize) {
                    bulkUpload(tyreDtos);
                    tyreDtos.clear(); // Clear the list after saving the batch
                }
            }

            // Save remaining items if any
            if (!tyreDtos.isEmpty()) {
                bulkUpload(tyreDtos);
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
