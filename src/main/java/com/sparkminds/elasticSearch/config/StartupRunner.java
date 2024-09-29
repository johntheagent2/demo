package com.sparkminds.elasticSearch.config;

import com.sparkminds.elasticSearch.service.ElasticSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StartupRunner implements CommandLineRunner {

    private final ElasticSearchService elasticSearchService;

    @Override
    public void run(String... args) throws Exception {
        elasticSearchService.createOrUpdateIndexWithAlias();
    }
}
