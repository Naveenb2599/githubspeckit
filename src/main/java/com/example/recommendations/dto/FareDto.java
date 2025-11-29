package com.example.recommendations.dto;

public class FareDto {
    private String fareType;
    private double price;
    private String currency;
    private boolean refundable;
    private String rules;

    public String getFareType() { return fareType; }
    public void setFareType(String fareType) { this.fareType = fareType; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public boolean isRefundable() { return refundable; }
    public void setRefundable(boolean refundable) { this.refundable = refundable; }
    public String getRules() { return rules; }
    public void setRules(String rules) { this.rules = rules; }
}
