package com.devictoralmeida.teste.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class LoginRequestDtoTest {

    @Nested
    @DisplayName("Testes para o LoginRequestDto")
    class LoginRequestDtoTests {

        @Test
        @DisplayName("Deve criar o LoginRequestDto com sucesso")
        void test_criacao_loginRequestDto() {
            LoginRequestDto dto = getValidLoginRequest();

            Assertions.assertNotNull(dto);
            Assertions.assertEquals("usuario@teste.com", dto.getLogin());
            Assertions.assertEquals("senha123", dto.getSenha());
        }

        @Test
        @DisplayName("Deve criar LoginRequestDto com campos nulos")
        void test_loginRequestDto_com_campos_nulos() {
            LoginRequestDto dto = getInvalidLoginRequestWithNullFields();

            Assertions.assertNull(dto.getLogin());
            Assertions.assertNull(dto.getSenha());
        }

        @Test
        @DisplayName("Deve criar LoginRequestDto com login em branco")
        void test_loginRequestDto_com_login_em_branco() {
            LoginRequestDto dto = getInvalidLoginRequestWithBlankLogin();

            Assertions.assertEquals("", dto.getLogin());
            Assertions.assertEquals("senha123", dto.getSenha());
        }

        @Test
        @DisplayName("Deve criar LoginRequestDto com senha em branco")
        void test_loginRequestDto_com_senha_em_branco() {
            LoginRequestDto dto = getInvalidLoginRequestWithBlankSenha();

            Assertions.assertEquals("usuario@teste.com", dto.getLogin());
            Assertions.assertEquals("", dto.getSenha());
        }
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
