package hr.fitbit.demo.fitbitconnect.apimodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "User information")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @ApiModelProperty(value = "user id", position = 1, example = "80264b81-3048-45c8-bc02-5c63a2372d3e")
    private UUID userId;

    @ApiModelProperty(value = "type", position = 2, example = "USER")
    private UserType type;

    @ApiModelProperty(value = "username", position = 3, example = "username")
    private String username;

    @ApiModelProperty(value = "name", position = 5, example = "name")
    private String name;

    @ApiModelProperty(value = "last name", position = 6, example = "lastname")
    private String lastName;

    @ApiModelProperty(value = "email", position = 7, example = "test@test.com")
    private String email;

    @ApiModelProperty(value = "age", position = 8, example = "24")
    private Integer age;

}

