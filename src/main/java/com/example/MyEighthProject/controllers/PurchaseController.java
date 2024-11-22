package com.example.MyEighthProject.controllers;

import com.example.MyEighthProject.model.Purchase;
import com.example.MyEighthProject.repository.PurchaseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private final PurchaseRepository purchaseRepository;
    public PurchaseController(
            PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }
    @PostMapping
    public void storePurchase(@RequestBody Purchase purchase) {

        purchaseRepository.storePurchase(purchase);
    }
    @GetMapping
    public List<Purchase> findPurchases() {

        return purchaseRepository.findAllPurchases();
    }

    @DeleteMapping("/{id}")
    public void deletePurchase(@PathVariable int id) {
        purchaseRepository.deletePurchaseById(id);
    }

    @PutMapping("/{id}/price")
    public void updatePurchasePrice(@PathVariable int id, @RequestBody UpdatePriceRequest request) {
        purchaseRepository.updatePurchasePrice(id, request.getPrice());
    }

    // Вспомогательный класс для передачи новых данных цены
    public static class UpdatePriceRequest {
        private double price;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
