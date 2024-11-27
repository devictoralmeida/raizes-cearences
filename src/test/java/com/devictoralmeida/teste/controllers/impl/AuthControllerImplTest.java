package com.devictoralmeida.teste.controllers.impl;

import com.devictoralmeida.teste.dto.request.LoginRequestDto;
import com.devictoralmeida.teste.dto.request.LoginRequestDtoTest;
import com.devictoralmeida.teste.dto.response.FirebaseLoginResponseDto;
import com.devictoralmeida.teste.dto.response.ResponseDto;
import com.devictoralmeida.teste.services.AuthService;
import com.devictoralmeida.teste.shared.constants.MessageCommonsConstants;
import com.devictoralmeida.teste.shared.exceptions.GlobalExceptionHandler;
import com.devictoralmeida.teste.shared.exceptions.SemAutenticacaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith({ MockitoExtension.class })
@Import({GlobalExceptionHandler.class})
@DisplayName("Testes para o endpoint /auth")
class AuthControllerImplTest {

    @InjectMocks
    private AuthControllerImpl authController;

    @Mock
    private AuthService authService;

    @Mock
    private GlobalExceptionHandler globalExceptionHandler;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authService)
                .setControllerAdvice(new GlobalExceptionHandler())
                .alwaysDo(print()).build();
    }

    @Nested
    @DisplayName("Testes para o endpoint /auth/login")
    class Login {

        @Test
        @DisplayName("Deve retornar status 201 ao realizar login")
        void deveRetornarStatus201AoRealizarLogin() {
            LoginRequestDto loginRequestDto = LoginRequestDtoTest.getValidLoginRequest();
            FirebaseLoginResponseDto mockResponse = new FirebaseLoginResponseDto();
            when(authService.login(loginRequestDto)).thenReturn(mockResponse);

            ResponseEntity<ResponseDto<FirebaseLoginResponseDto>> response = authController.login(loginRequestDto);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertNotNull(response.getBody().getData());
            assertEquals(mockResponse, response.getBody().getData());
            assertEquals(MessageCommonsConstants.MENSAGEM_LOGIN_SUCESSO, response.getBody().getMensagem());
        }

        @Test
        @DisplayName("Deve retornar status 401 ao realizar login com credenciais inválidas")
        void deveRetornarStatus401AoRealizarLoginComCredenciaisInvalidas() {
            LoginRequestDto loginRequestDto = LoginRequestDtoTest.getInvalidLoginRequestWithNullFields();
            when(authService.login(loginRequestDto)).thenThrow(new SemAutenticacaoException("Credenciais inválidas"));
            try {
                authController.login(loginRequestDto);
            } catch (SemAutenticacaoException e) {
                assertEquals("Credenciais inválidas", e.getMessage());
            }
        }

        @Test
        @DisplayName("Deve retornar erro 401 ao realizar login sem usar senha")
        void deveRetornarStatus401AoRealizarLoginSemUsarSenha() {
            LoginRequestDto loginRequestDto = LoginRequestDtoTest.getInvalidLoginRequestWithBlankSenha();
            when(authService.login(loginRequestDto)).thenThrow(new SemAutenticacaoException("Credenciais inválidas"));
            try {
                authController.login(loginRequestDto);
            } catch (SemAutenticacaoException e) {
                assertEquals("Credenciais inválidas", e.getMessage());
            }
        }

        @Test
        @DisplayName("Deve retornar erro 401 ao realizar login sem usar login")
        void deveRetornarStatus401AoRealizarLoginSemUsarLogin() {
            LoginRequestDto loginRequestDto = LoginRequestDtoTest.getInvalidLoginRequestWithBlankLogin();
            when(authService.login(loginRequestDto)).thenThrow(new SemAutenticacaoException("Credenciais inválidas"));
            try {
                authController.login(loginRequestDto);
            } catch (SemAutenticacaoException e) {
                assertEquals("Credenciais inválidas", e.getMessage());
            }
        }
    }
}