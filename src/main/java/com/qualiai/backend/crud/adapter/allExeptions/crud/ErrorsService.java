package com.qualiai.backend.crud.adapter.allExeptions.crud;

import com.qualiai.backend.crud.adapter.allExeptions.ErrorResponse;
import org.springframework.stereotype.Service;

@Service
public class ErrorsService {
    private final ErrorsRepository errorsRepository;

    public ErrorsService(ErrorsRepository errorsRepository) {
        this.errorsRepository = errorsRepository;
    }

    public void insertError(ErrorResponse error) {
        errorsRepository.inserir(error);
    }
}
