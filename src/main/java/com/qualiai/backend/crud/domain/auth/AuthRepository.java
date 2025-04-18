package com.qualiai.backend.crud.domain.auth;

import com.qualiai.backend.dtos.auth.UpdateSenhaDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository {

    void updateSenha(UpdateSenhaDTO data);

    void tentativaLoginIncorreto(String emailUsuario, int tentativas);

    void controlarBloqueioContaUsuario(String emailUsuario, boolean operacao, boolean ativo);

}
