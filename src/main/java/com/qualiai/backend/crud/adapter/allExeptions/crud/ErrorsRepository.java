package com.qualiai.backend.crud.adapter.allExeptions.crud;

import org.springframework.stereotype.Repository;
import org.springframework.validation.Errors;

@Repository
public interface ErrorsRepository {
    public void inserir(Errors errors);
}
