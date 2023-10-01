package br.com.btg.desafioengenharia.response;

import br.com.btg.desafioengenharia.exception.ObjectError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ErrorResponse {
  private String code;
  private String message;
  private List<ObjectError> errorList;

  public static final String ERROR_RESPONSE_VALIDATION_MODEL = "{\n"
      + "  \"code\": \"string\",\n"
      + "  \"message\": \"string\",\n"
      + "  {\n"
      + "     \"message\": \"string\",\n"
      + "     \"field\": \"string\"\n"
      + "  }\n"
      + "}";

  public static final String ERROR_RESPONSE_EXCEPTION_MODEL = "{\n"
      + "  \"code\": \"string\",\n"
      + "  \"message\": \"string\",\n"
      + "}";

}
