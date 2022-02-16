package com.example.smartmuseumapp.Ticketing.Model;
public class TicketModel {
    String Date;
    String ExpiryDate;
    String Payment;
    String Time;
    String adult;
    String amount;
    String member;
    String student;
    String ticket_id;
    String user_id;

    public TicketModel(String date, String expiryDate, String payment, String time, String adult, String amount, String member, String student, String ticket_id, String user_id) {
        this.Date = date;
        this.ExpiryDate = expiryDate;
        this.Payment = payment;
        this.Time = time;
        this.adult = adult;
        this.amount = amount;
        this.member = member;
        this.student = student;
        this.ticket_id = ticket_id;
        this.user_id = user_id;

    }

    public TicketModel() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
