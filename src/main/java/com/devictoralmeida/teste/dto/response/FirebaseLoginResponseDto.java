package com.devictoralmeida.teste.dto.response;

import com.devictoralmeida.teste.shared.constants.SchemaExamplesDtosConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class FirebaseLoginResponseDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -1857780050295696126L;

  @Schema(example = SchemaExamplesDtosConstants.EMAIL_RESPONSE)
  private String email;

  @Schema(example = SchemaExamplesDtosConstants.DISPLAY_NAME_RESPONSE)
  private String displayName;

  @Schema(example = SchemaExamplesDtosConstants.ID_TOKEN_RESPONSE)
  private String idToken;

  @Schema(example = SchemaExamplesDtosConstants.TOKEN_RESPONSE)
  private String refreshToken;

  @Schema(example = SchemaExamplesDtosConstants.EXPIRES_IN_RESPONSE)
  private String expiresIn;
}
