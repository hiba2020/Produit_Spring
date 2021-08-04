package com.nadhem.produits.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.nadhem.produits.entities.Produit;
import com.nadhem.produits.service.ProduitService;


@Controller
public class ProduitController {

	@Autowired
	ProduitService produitService;
	
	@RequestMapping("/showCreate")
	public String showCreate()
	{
		return "createProduit";
	}
	
	@RequestMapping("/saveProduit")
	public String saveProduit(	@ModelAttribute(value="produit") Produit produit,
								@RequestParam("date") String date, 
								ModelMap modelMap ) throws ParseException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
		Date dateCreation = dateFormat.parse(String.valueOf(date));
		produit.setDateCreation(dateCreation);
		
		Produit saveProduit = produitService.saveProduit(produit);
		
		String msg = "Produit enregistré avec ID "+saveProduit.getIdProduit();
	
		modelMap.addAttribute("msg",msg);
		
		return ("createProduit");
	}
	
	
	@RequestMapping("/listeProduits0")
	public String listeProduits0(ModelMap modelMap) {
		
		List<Produit> listeProduit= produitService.getAllProduits();
		modelMap.addAttribute("produits",listeProduit);
		return "listeProduits";
	}
	
	
	@RequestMapping("/supprimerProduit0")
	public String supprimerProduit0(@RequestParam("id") Long id, ModelMap modelMap) {
		
		produitService.deleteProduitById(id);
		
		listeProduits0( modelMap);
		//modelMap.addAttribute("msg",msg);
		
		return "listeProduits";
	}
	
	
	@RequestMapping("/modifierProduit")
	public String modifierProduit(@RequestParam("id") Long id, ModelMap modelMap) {
		
		Produit produit = produitService.getProduit(id);
		modelMap.addAttribute("produit", produit);
		return "editerProduit";
	}
	
	
	
	@RequestMapping("/updateProduit")
	public String editerProduit(@ModelAttribute("produit") Produit produit,
								@RequestParam("date") String date,
								ModelMap modelMap) throws ParseException{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
		Date dateCreation = dateFormat.parse(String.valueOf(date));
		produit.setDateCreation(dateCreation);
		
		produitService.updateProduit(produit);
		
		listeProduits0(modelMap);
		return "listeProduits";
		
	}
	
	@RequestMapping("/listeProduits1")
	public String listeProduits1(ModelMap modelMap,
							@RequestParam (name="page", defaultValue = "0")int page,
							@RequestParam (name="size", defaultValue = "2") int size) {
		
		Page<Produit> listePageProduit= produitService.getAllProduitsParPage(page, size);
		//List<Produit> listeProduit= produitService.getAllProduits();
		modelMap.addAttribute("produits",listePageProduit);
		modelMap.addAttribute("pages",new int[listePageProduit.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);		
		modelMap.addAttribute("size", size);	
	
		return "listeProduits";
	}
	
	@RequestMapping("/supprimerProduit1")
	public String supprimerProduit1(@RequestParam("id") Long id, 
									ModelMap modelMap,
									@RequestParam (name="page", defaultValue = "0")int page,
									@RequestParam (name="size", defaultValue = "2") int size) {
								
		
		produitService.deleteProduitById(id);
		Page<Produit> listePageProduit= produitService.getAllProduitsParPage(page, size);
		//List<Produit> listeProduit= produitService.getAllProduits();
		modelMap.addAttribute("produits",listePageProduit);
		modelMap.addAttribute("pages",new int[listePageProduit.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);		
		modelMap.addAttribute("size", size);	
		
		return "listeProduits";
	}
}





