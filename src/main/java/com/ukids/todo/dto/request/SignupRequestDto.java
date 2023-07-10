package com.ukids.todo.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequestDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String name;

}
