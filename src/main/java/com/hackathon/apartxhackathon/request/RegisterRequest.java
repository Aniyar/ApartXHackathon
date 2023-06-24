package com.hackathon.apartxhackathon.request;

import com.hackathon.apartxhackathon.user.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  @NotNull
  private String email;
  @NotNull
  private String firstname;
  @NotNull
  private String lastname;
  @NotNull
  private String iin;
  @NotNull
  private String password;
  @NotNull
  private Role role;
}
