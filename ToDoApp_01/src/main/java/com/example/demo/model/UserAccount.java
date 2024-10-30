package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class UserAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<WishItem> purposeList = new ArrayList<>();
	
	public UserAccount() {}

	public UserAccount(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public void addPurpose(WishItem purpose) {
		purposeList.add(purpose);
		purpose.setUser(this);
	}
	
	public void removePurpose(WishItem purpose) {
		purposeList.remove(purpose);
		purpose.setUser(null);
	}
}