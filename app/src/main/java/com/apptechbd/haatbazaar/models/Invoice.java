package com.apptechbd.haatbazaar.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Invoice {
    private int invoiceNumber, totalAmount, commissionAmount;
    private String hutName, hutAddress, customerName, customerAddress, sellerName, saleDate;
    private HashMap<String, Integer> animalsPurchased = new HashMap<>();

    public Invoice() {
    }

    public Invoice(int invoiceNumber, String saleDate, int totalAmount, int commissionAmount, String hutName, String hutAddress, String customerName, String customerAddress, String sellerName, HashMap<String, Integer> animalsPurchased) {
        this.invoiceNumber = invoiceNumber;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
        this.commissionAmount = commissionAmount;
        this.hutName = hutName;
        this.hutAddress = hutAddress;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.sellerName = sellerName;
        this.animalsPurchased = animalsPurchased;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(int commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public String getHutName() {
        return hutName;
    }

    public void setHutName(String hutName) {
        this.hutName = hutName;
    }

    public String getHutAddress() {
        return hutAddress;
    }

    public void setHutAddress(String hutAddress) {
        this.hutAddress = hutAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public HashMap<String, Integer> getAnimalsPurchased() {
        return animalsPurchased;
    }

    public void setAnimalsPurchased(HashMap<String, Integer> animalsPurchased) {
        this.animalsPurchased = animalsPurchased;
    }

    @NonNull
    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceNumber=" + invoiceNumber +
                ", totalAmount=" + totalAmount +
                ", commissionAmount=" + commissionAmount +
                ", hutName='" + hutName + '\'' +
                ", hutAddress='" + hutAddress + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", saleDate='" + saleDate + '\'' +
                ", animalsPurchased=" + animalsPurchased.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", ")) +
                '}';
    }

}
