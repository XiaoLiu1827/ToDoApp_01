package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostPersist;
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

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "savings_box_id", referencedColumnName = "id", nullable = false)
	private SavingsBox savingsBox;

	public UserAccount() {
	}

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

	@PostPersist
	public void updateSavingsBoxUserId() {
		if (this.savingsBox != null) {
			this.savingsBox.setUserId(this.id);
		}
	}
}