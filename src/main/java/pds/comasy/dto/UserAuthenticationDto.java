package pds.comasy.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@Data
@AllArgsConstructor
public class UserAuthenticationDto {

    String username;

    String password;

    String role;

    Date entryDate;

    Date exitDate;
}
