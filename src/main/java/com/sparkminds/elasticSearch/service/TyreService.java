package com.sparkminds.elasticSearch.service;

import com.sparkminds.elasticSearch.dto.TyreRequestDto;
import com.sparkminds.elasticSearch.entity.TyreEntity;
import com.sparkminds.elasticSearch.entity.document.TyreDocument;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface TyreService {
    void saveTyre(TyreRequestDto tyreEntity);

    Optional<TyreEntity> getTyreById(Long id);

    Iterable<TyreEntity> getAllTyres();

    void deleteTyreById(Long id);

    void importTyresFromCSV(MultipartFile file);

    void bulkUpload(List<TyreRequestDto> dtos);
}
