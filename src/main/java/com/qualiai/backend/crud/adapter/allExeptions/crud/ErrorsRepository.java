package com.qualiai.backend.crud.adapter.allExeptions.crud;

import com.qualiai.backend.crud.adapter.allExeptions.ErrorResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorsRepository {
    void inserir(ErrorResponse error);
}
