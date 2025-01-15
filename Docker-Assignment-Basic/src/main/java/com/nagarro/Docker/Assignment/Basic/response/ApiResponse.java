package com.nagarro.Docker.Assignment.Basic.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    public String message;
    public boolean status;

}
