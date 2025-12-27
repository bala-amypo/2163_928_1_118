// package com.example.demo.dto;

// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Size;

// public class RegisterRequest {

//     @Email
//     @NotBlank
//     private String email;

//     @NotBlank
//     @Size(min = 6)
//     private String password;

//     @NotBlank
//     private String role;

//     public RegisterRequest() {}
//     // getters & setters
// }


package com.example.demo.dto;

import com.example.demo.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username; // Added to match Test
    private String email;
    private String password;
    private Role role;
}