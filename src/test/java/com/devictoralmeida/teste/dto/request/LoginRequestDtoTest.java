package com.devictoralmeida.teste.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginRequestDtoTest {

    @Test
    public void test_criacao_loginRequestDto() {
        LoginRequestDto dto = getValidLoginRequest();

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("usuario@teste.com", dto.getLogin());
        Assertions.assertEquals("senha123", dto.getSenha());
    }

    @Test
    public void test_loginRequestDto_com_campos_nulos() {
        LoginRequestDto dto = getInvalidLoginRequestWithNullFields();

        Assertions.assertNull(dto.getLogin());
        Assertions.assertNull(dto.getSenha());
    }

    public static LoginRequestDto getValidLoginRequest() {
        return new LoginRequestDto("usuario@teste.com", "senha123");
    }

    public static LoginRequestDto getInvalidLoginRequestWithBlankLogin() {
        return new LoginRequestDto("", "senha123");
    }

    public static LoginRequestDto getInvalidLoginRequestWithBlankSenha() {
        return new LoginRequestDto("usuario@teste.com", "");
    }

    public static LoginRequestDto getInvalidLoginRequestWithNullFields() {
        return new LoginRequestDto(null, null);
    }
}
