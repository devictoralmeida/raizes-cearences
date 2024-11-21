package com.devictoralmeida.teste.shared.exceptions;

import com.devictoralmeida.teste.dto.response.ResponseDto;
import com.devictoralmeida.teste.shared.constants.MessageCommonsConstants;
import com.devictoralmeida.teste.shared.constants.validation.AnexoValidationMessages;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler({DataIntegrityViolationException.class})
  public ResponseEntity<?> handlePSQLException(DataIntegrityViolationException ex, WebRequest request) {
    LOGGER.error(" =============== DataIntegrityViolationException ==========================");

    String fieldName = ex.getMessage();
    String errorMsg;

    if (fieldName != null) {
      errorMsg = fieldName;
    } else {
      errorMsg = "Violação de integridade: " + getMessage(ex.getMessage());
    }

    ResponseDto<?> obj = ResponseDto.fromData(errorMsg, HttpStatus.CONFLICT, errorMsg, Arrays.asList(ex.getMessage()));
    return handleExceptionInternal(ex, obj, new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  public String getMessage(String message) {
    return getTable(message) + " : " + getField(message);
  }

  public String getField(String errorMessage) {
    int indexOfConstraint = errorMessage.indexOf("constraint [");
    int indexOfEndOfField = errorMessage.indexOf("\" of relation", indexOfConstraint);
    return errorMessage.substring(indexOfConstraint + "constraint [".length(), indexOfEndOfField);
  }

  public String getTable(String errorMessage) {
    int indexOfRelation = errorMessage.indexOf("\" of relation");
    int indexOfEndOfTable = errorMessage.indexOf("];", indexOfRelation);
    return errorMessage.substring(indexOfRelation + "\" of relation \"".length(), indexOfEndOfTable);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    LOGGER.error(" =============== Campos do DTO que não passaram na validação ==========================");

    List<String> erros = ex.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList();

    ResponseDto<?> res = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST,
            MessageCommonsConstants.MENSAGEM_VERIFICAR_CAMPOS, erros);

    return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
          HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    Throwable rootCause = ex.getRootCause();
    if (rootCause instanceof InvalidFormatException) {
      LOGGER.error(" =============== HttpMessageNotReadableException validation ENUM ==========================");

      InvalidFormatException invalidFormatException = (InvalidFormatException) rootCause;
      String invalidValue = invalidFormatException.getValue().toString();
      String enumType = invalidFormatException.getTargetType().getSimpleName();
      List<String> enumValues = Arrays.stream(invalidFormatException.getTargetType().getEnumConstants())
              .map(Object::toString)
              .collect(Collectors.toList());

      String errorMessage = String.format(
              MessageCommonsConstants.MENSAGEM_VALOR_INVALIDO,
              enumType, invalidValue, enumValues);

      ResponseDto<?> res = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage);
      return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
    } else if (rootCause instanceof DateTimeParseException) {
      LOGGER.error(" =============== HttpMessageNotReadableException validation LocalDate or LocalDateTime ==========================");

      DateTimeParseException dateTimeParseException = (DateTimeParseException) rootCause;
      String invalidValue = dateTimeParseException.getParsedString();
      String errorMessage = "Formato de data inválido: " + invalidValue + ". ";

      if (ex.getLocalizedMessage().contains("LocalDate")) {
        errorMessage += "O formato esperado é YYYY-MM-DD";
      } else {
        errorMessage += "Use os formatos apropriados para data e hora.";
      }

      ResponseDto<?> res = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage);
      return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
    }

    return handleExceptionInternal(ex, null, headers, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
          MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    LOGGER.error(" =============== MissingServletRequestParameterException ==========================");

    String field;
    String error;

    if ("tipoDocumento".equals(ex.getParameterName())) {
      field = "tipoDocumento";
      error = AnexoValidationMessages.TIPO_DOCUMENTO_OBRIGATORIO;
    } else {
      field = ex.getParameterName();
      error = "Parâmetro obrigatório não está presente: " + ex.getParameterName();
    }

    ResponseDto<?> res = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, error, Arrays.asList(field));
    return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
          MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    LOGGER.error(" =============== MissingServletRequestPartException ==========================");

    String field = "arquivo";
    String error = AnexoValidationMessages.ARQUIVO_OBRIGATORIO;

    ResponseDto<?> res = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, error, Arrays.asList(field));
    return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    LOGGER.error(" =============== MaxUploadSizeExceededException ==========================");

    String field = "anexo";
    String error = AnexoValidationMessages.ARQUIVO_TAMANHO_MAXIMO;

    ResponseDto<?> res = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, error, Arrays.asList(field));
    return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({NegocioException.class})
  public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
    LOGGER.error(" =============== NegocioException ==========================");

    String field = ex.getMessage();
    String error = ex.getMessage();

    Object obj = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, error, Arrays.asList(field));
    return handleExceptionInternal(ex, obj, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({RecursoNaoEncontradoException.class})
  public ResponseEntity<?> handleRecursoNaoEncontradoException(RecursoNaoEncontradoException ex, WebRequest request) {
    LOGGER.error(" =============== RecursoNaoEncontradoException ==========================");

    String field = ex.getMessage();
    String error = ex.getMessage();

    Object obj = ResponseDto.fromData(null, HttpStatus.NOT_FOUND, error, Arrays.asList(field));
    return handleExceptionInternal(ex, obj, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler({SemAutenticacaoException.class})
  public ResponseEntity<?> handleSemAutenticacaoExceptionException(SemAutenticacaoException ex, WebRequest request) {
    LOGGER.error(" =============== SemAutenticacaoException ==========================");

    String field = ex.getMessage();
    String error = ex.getMessage();

    Object obj = ResponseDto.fromData(null, HttpStatus.UNAUTHORIZED, error, Arrays.asList(field));
    return handleExceptionInternal(ex, obj, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
  }

  @ExceptionHandler({SemAutorizacaoException.class})
  public ResponseEntity<?> handleSemAutorizacaoExceptionException(SemAutorizacaoException ex, WebRequest request) {
    LOGGER.error(" =============== SemAutorizacaoException ==========================");

    String field = ex.getMessage();
    String error = ex.getMessage();

    Object obj = ResponseDto.fromData(null, HttpStatus.FORBIDDEN, error, Arrays.asList(field));
    return handleExceptionInternal(ex, obj, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
  }
}