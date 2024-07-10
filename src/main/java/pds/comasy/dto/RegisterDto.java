package pds.comasy.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RegisterDto {

    String username;

    String password;

    String role;

    Date entryDate;

    Date exitDate;
}
