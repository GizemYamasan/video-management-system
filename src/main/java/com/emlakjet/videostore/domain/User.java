package com.emlakjet.videostore.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Subscription currentSubscription;

}
