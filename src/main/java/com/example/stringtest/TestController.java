package com.example.stringtest;

import net.minidev.json.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class TestController {
    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot! WOW postman!";
    }


    @GetMapping("/users")
    public String users() {

        return LoanController.getUsers();

    }

    @GetMapping("/payments")
    public JSONArray payments() {
        return LoanController.getPayments();
    }

    @GetMapping("/getLoan/{id}")
    public JSONArray getLoan(@PathVariable("id") String id) {
        return LoanController.getLoan(id);
    }

    @GetMapping("/createLoan/{userId}/{principal}/{durration}/{interest}/{remaining}")
    public String createLoan(@PathVariable("userId") String userId, @PathVariable("principal") BigDecimal principal, @PathVariable("durration") Integer durration, @PathVariable("interest") BigDecimal interest, @PathVariable("remaining") BigDecimal remaining) {
        Loan l1 = new Loan();
        l1.setUserId(userId);
        l1.setPrincipal(principal);
        l1.setDurration(durration);
        l1.setInterest(interest);
        l1.setRemaining(remaining);
        return LoanController.createLoan(l1);
    }

    //totalBal, Double interest, Double durration,Double balloon

    @GetMapping("/createAmortisation/{userId}/{totalBal}/{interest}/{durration}/{balloon}")
    public Amortisation createAmortisation(@PathVariable("userId") String userId, @PathVariable("totalBal") BigDecimal totalBal, @PathVariable("durration") BigDecimal durration, @PathVariable("interest") BigDecimal interest, @PathVariable("balloon") BigDecimal balloon) {

        return LoanController.createSchedule(totalBal, interest, durration, userId, balloon);
    }



    @GetMapping("/getAmortisation/{id}")
    public Amortisation getAmortisation(@PathVariable("id") String id) {
        return LoanController.getAmortisation(id);
    }

    @GetMapping("/addUser/{sName}/{fName}")
    public String addUser(@PathVariable("sName") String sName, @PathVariable("fName") String fName) {
        User u1 = new User();
        u1.fName = fName;
        u1.sName = sName;



        return  LoanController.addUser(u1);
    }

}


