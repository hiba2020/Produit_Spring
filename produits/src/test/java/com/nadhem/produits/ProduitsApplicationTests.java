package com.nadhem.produits;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;


import com.nadhem.produits.entities.Produit;
import com.nadhem.produits.repository.ProduitRepository;
import com.nadhem.produits.service.ProduitService;


@SpringBootTest
class ProduitsApplicationTests {

	@Autowired
	private ProduitRepository produitRepository;

	@Autowired
	private ProduitService produitService;

	@Test
	public void testCreateProduit() {

		Produit prod = new Produit("HP", 2200.500, new Date());
		// produitRepository.save(prod);
		produitService.saveProduit(prod);
		System.out.println(prod);

	}

	@Test
	public void testFindProduit() {

		Produit p = produitRepository.findById(1L).get();
		
		/*
		 Produit p1=produitService.getProduit(2L);
		 
		System.out.println(p1);
		*/
		System.out.println("Produit p num 1 est :" + p);

	}

	
	@Test
	public void testDeleteProduit() {
		
		produitService.deleteProduitById(39L);
		System.out.println("le produit 39 est bien supprimer");
	}
	
	@Test
	public void testUpdateProduit() {

		Produit p = produitRepository.findById(1L).get();
		p.setPrixProduit(1000.0);
		produitRepository.save(p);
	}

	@Test
	public void testListeProduit() {

		//List<Produit> listeProduit = produitRepository.findAll();
		List<Produit> listeProduit =produitService.getAllProduits();
		for (Produit produit : listeProduit) {
			System.out.println(produit);
		}
	}

	@Test
	public void testListeProduitParPage() {

		Page<Produit> prods = produitService.getAllProduitsParPage(0, 2);//page 0 c à d page1 et chaque page il y a  produits
		System.out.println(prods.getSize());//taille de liste prods
		System.out.println(prods.getTotalElements());//nombre d'element de prods
		System.out.println(prods.getTotalPages());
		
		prods.getContent().forEach(p -> {
			System.out.println(p.toString());
		});
		/*
		 * ou bien for (Produit p : prods) { System.out.println(p); }
		 */

	}

}
