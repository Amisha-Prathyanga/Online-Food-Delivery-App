package com.example.foodapp;

//Create cdmodel class
public class Cdmodel {
    private int id;
    private int userid;


    private String CardName, CardNumber, Month,Year, cvv;


    public Cdmodel() {

    }

    public Cdmodel(int id, String cardNumber, String cardName, String Month,String Year, String cvv) {
        this.id = id;
        this.CardNumber = cardNumber;
        this.CardName = cardName;
        this.Month = Month;
        this.Year = Year;
        this.cvv = cvv;
    }

    public Cdmodel(String cardNumber, String cardName,String Month,String Year,String cvv,int userid) {
        this.CardNumber = cardNumber;
        this.CardName = cardName;
        this.Month = Month;
        this.Year = Year;
        this.cvv = cvv;
        this.userid = userid;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }


    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }



}