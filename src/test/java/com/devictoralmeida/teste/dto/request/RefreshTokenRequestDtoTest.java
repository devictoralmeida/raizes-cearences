package com.devictoralmeida.teste.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class RefreshTokenRequestDtoTest {

    @Nested
    @DisplayName("Testes para o RefreshTokenRequestDto")
    class RefreshTokenRequestDtoTests {

        @Test
        @DisplayName("Deve criar o RefreshTokenRequestDto com sucesso")
        void test_criacao_refreshTokenRequestDto() {
            RefreshTokenRequestDto dto = getValidRefreshTokenRequest();

            Assertions.assertNotNull(dto);
            Assertions.assertEquals("refresh_token", dto.getGrant_type());
            Assertions.assertEquals("token123", dto.getRefresh_token());
        }

        @Test
        @DisplayName("Deve criar RefreshTokenRequestDto com refresh_token em branco")
        void test_refreshTokenRequestDto_com_refresh_token_em_branco() {
            RefreshTokenRequestDto dto = getInvalidRefreshTokenRequestWithBlankToken();

            Assertions.assertEquals("", dto.getRefresh_token());
        }
    }

    public static RefreshTokenRequestDto getValidRefreshTokenRequest() {
        return new RefreshTokenRequestDto("token123");
    }

    public static RefreshTokenRequestDto getInvalidRefreshTokenRequestWithNullFields() {
        return new RefreshTokenRequestDto(null);
    }

    public static RefreshTokenRequestDto getInvalidRefreshTokenRequestWithBlankToken() {
        return new RefreshTokenRequestDto("");
    }
}
