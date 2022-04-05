package com.emlakjet.videostore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupUser {

	private String firstName;
	private String lastName;
	private String email;
	private String password;

}
