package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.guest.GuestSavings;
import com.example.demo.model.guest.GuestSavingsPurpose;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/savings")
public class GuestSavingsController {

	@GetMapping
	public String getAllSavings(HttpSession session, Model model, @ModelAttribute GuestSavings guestSavings) {
		List<GuestSavingsPurpose> purposeList =Optional.ofNullable((List<GuestSavingsPurpose>) session.getAttribute("purposeList"))
				.orElseGet(ArrayList::new);;
		if (purposeList== null || purposeList.isEmpty()) {
			purposeList = new ArrayList<>();
			purposeList.add(new GuestSavingsPurpose("rich dinner", 0.0, 5000.0));
			purposeList.add(new GuestSavingsPurpose("trip", 0.0, 100000.0));
			session.setAttribute("purposeList", purposeList);
		}
		return "guestSavings";
	}

	@PostMapping
	public String createSavings(@RequestParam("purpose") UUID purposeId, HttpSession session,
			GuestSavings enteredSavings, Model model) {
		//savingsListに追加
		//		List<GuestSavings> savingsList = (List<GuestSavings>)session.getAttribute("savingsList");
		//		if(savingsList== null) {
		//			savingsList= new ArrayList<>();
		//			savingsList.add(enteredSavings);
		//		}else {
		//			savingsList.add(enteredSavings);
		//		}
		//上記を書き換え
		List<GuestSavings> savingsList = Optional.ofNullable((List<GuestSavings>) session.getAttribute("savingsList"))
				.orElseGet(ArrayList::new);
		savingsList.add(enteredSavings);
		session.setAttribute("savingsList", savingsList);

		//purposeのcurrentAmmount更新
		List<GuestSavingsPurpose> purposeList = Optional
				.ofNullable((List<GuestSavingsPurpose>) session.getAttribute("purposeList"))
				.orElseGet(ArrayList::new);
		Optional<GuestSavingsPurpose> purposeListOptional = purposeList.stream()
				.filter(p -> p.getId().equals(purposeId)).findFirst();
		if (purposeListOptional.isPresent()) {
			//更新対象のpurposeを一度削除し、更新後再格納する
	        purposeList.removeIf(purpose -> purpose.getId().equals(purposeId));
			GuestSavingsPurpose purpose = purposeListOptional.get();
			purpose.updateCurrentAmount(enteredSavings.getAmount());
			purposeList.add(purpose);
			session.setAttribute("purposeList", purposeList);
		} else {

		}
		return "redirect:/api/savings";
	}

	@GetMapping("/delete/{id}")
	public String deleteSavings(@PathVariable int id, HttpSession session) {
		List<GuestSavings> savingsList = Optional
				.ofNullable((List<GuestSavings>) session.getAttribute("savingsList"))
				.orElseGet(ArrayList::new);
		savingsList.remove(id);
		session.setAttribute("savingsList", savingsList);

		return "redirect:/api/savings";
	}
	
	@GetMapping("/purpose/delete/{id}")
	public String deletePurpose(@PathVariable UUID id, HttpSession session) {
		List<GuestSavingsPurpose> purposeList = Optional
				.ofNullable((List<GuestSavingsPurpose>) session.getAttribute("purposeList"))
				.orElseGet(ArrayList::new);
        purposeList.removeIf(purpose -> purpose.getId().equals(id));
		session.setAttribute("purposeList", purposeList);

		return "redirect:/api/savings";
	}
}
