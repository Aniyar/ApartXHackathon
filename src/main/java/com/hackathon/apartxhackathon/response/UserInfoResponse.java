package com.hackathon.apartxhackathon.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackathon.apartxhackathon.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
	private String firstname;
	private String lastname;
	private String email;
	private String iin;
	private Role role;
}
