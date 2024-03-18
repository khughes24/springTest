/*
* Simple code to connect to a SQLite3 database and print a single
* row from a table.
* 
* Based on http://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
*/

package com.example.stringtest;
 
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class LoanController {
     /**
     * Connect to a sample database
     */

     // Set precision to 5
     static MathContext mc = new MathContext(2);
    public static void testCanConnect() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:testdb.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

            // Ensure we can query the actors table
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users LIMIT 1;");

            while ( rs.next() ) {

                String  name = rs.getString("fname") + rs.getString("sname");

                System.out.println(String.format("Found %s", name));
            }
            
            rs.close();  
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static String getUsers(){
        Connection conn = null;
        try{
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:testdb.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            if (conn == null){
                return "-1";
            }
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users LIMIT 1;");

            while ( rs.next() ) {

                String  name = rs.getString("fname") + " " + rs.getString("sname");


                return name;
            }

            rs.close();
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return "-1";

    }
    public static JSONArray getPayments(){
        Connection conn = null;
        try{
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:testdb.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            if (conn == null){
                return null;
            }
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM payments;");
            //Creating a JSONObject object
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            while ( rs.next() ) {

                //String  name = rs.getString("fname") + " " + rs.getString("sname");
                JSONObject record = new JSONObject();
                //Inserting key-value pairs into the json object
                record.put("paymentId", rs.getInt("paymentId"));
                record.put("loadId", rs.getString("loanId"));
                record.put("userId", rs.getString("userId"));
                record.put("amount", rs.getString("amount"));
                record.put("princple", rs.getString("princple"));
                record.put("interest", rs.getString("interest"));
                record.put("balance", rs.getString("balance"));
                array.add(record);



            }


            rs.close();
            return array;
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }


    public static String addUser(User u1){
        Connection conn = null;
        try{
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:testdb.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            if (conn == null){
                return null;
            }
            Statement stmt = conn.createStatement();
            PreparedStatement prepared = conn.prepareStatement("INSERT INTO users(fName,sName) VALUES(?,?);");
            prepared.setString(1, u1.fName);
            prepared.setString(2, u1.sName);
            prepared.executeUpdate();
            return "OK";
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String createLoan(Loan l1){
        Connection conn = null;
        try{
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:testdb.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            if (conn == null){
                return null;
            }
            Statement stmt = conn.createStatement();
            PreparedStatement prepared = conn.prepareStatement("INSERT INTO loans(userId,principal,durration,interest,remaining) VALUES(?,?,?,?,?);");
            prepared.setString(1, l1.userId);
            prepared.setBigDecimal(2, l1.principal);
            prepared.setInt(3, l1.durration);
            prepared.setBigDecimal(4, l1.interest);
            prepared.setBigDecimal(5, l1.remaining);
            prepared.executeUpdate();
            return "OK";
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public static Integer createAmortisation(Amortisation am1){
        Connection conn = null;
        try{
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:testdb.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            if (conn == null){
                return null;
            }
            Statement stmt = conn.createStatement();
            PreparedStatement prepared = conn.prepareStatement("INSERT INTO amortisations(userId,principal,debt,interest) VALUES(?,?,?,?);");
            prepared.setString(1, am1.userId);
            prepared.setBigDecimal(2, am1.principal);
            prepared.setBigDecimal(3, am1.debt);
            prepared.setBigDecimal(4, am1.interest);
            prepared.executeUpdate();

            Statement stmt2 = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()");
            Integer rowId = 0;
            while ( rs.next() ) {
                rowId = rs.getInt("last_insert_rowid()");
            }

            Statement stmt3 = conn.createStatement();
            PreparedStatement prepared2 = conn.prepareStatement("INSERT INTO amortisationLines(amortisationId,payment,principal,interest, balance) VALUES(?,?,?,?,?);");
            for( int i = 0; i < am1.details.size(); i++){
                prepared2.setString(1, Integer.toString(rowId));
                prepared2.setBigDecimal(2, am1.details.get(i).payment);
                prepared2.setBigDecimal(3, am1.details.get(i).principal);
                prepared2.setBigDecimal(4, am1.details.get(i).interest);
                prepared2.setBigDecimal(5, am1.details.get(i).balance);
                prepared2.addBatch();
            }

            prepared2.executeBatch();


            return rowId;
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }



    public static JSONArray getLoan(String loanId){
        Connection conn = null;
        if(Objects.equals(loanId, "")){
            return null;
        }
        try{
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:testdb.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            if (conn == null){
                return null;
            }
            Statement stmt = conn.createStatement();
            PreparedStatement prepared = conn.prepareStatement("SELECT * FROM loans where loanId = (?);");
            prepared.setString(1, loanId);
            ResultSet rs = prepared.executeQuery();
            //Creating a JSONObject object
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            while ( rs.next() ) {

                //String  name = rs.getString("fname") + " " + rs.getString("sname");
                JSONObject record = new JSONObject();
                //Inserting key-value pairs into the json object
                record.put("loanId", rs.getInt("loanId"));
                record.put("userId", rs.getString("userId"));
                record.put("principle", rs.getString("principle"));
                record.put("durration", rs.getString("durration"));
                record.put("interest", rs.getString("interest"));
                record.put("remaining", rs.getString("remaining"));
                array.add(record);

            }
            rs.close();
            return array;
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Amortisation getAmortisation(String amortisationId){
        Connection conn = null;
        if(Objects.equals(amortisationId, "")){
            return null;
        }
        try{
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:testdb.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            if (conn == null){
                return null;
            }
            Statement stmt = conn.createStatement();
            PreparedStatement prepared = conn.prepareStatement("SELECT * FROM amortisations where amortisationId = (?) LIMIT 1;");
            prepared.setString(1, amortisationId);
            ResultSet rs = prepared.executeQuery();
            while ( rs.next() ) {

                Amortisation record = new Amortisation();
                //Inserting key-value pairs into the json object
                record.setAmortisationId(String.valueOf(rs.getInt("amortisationId")));
                record.setUserId(String.valueOf(rs.getInt("userId")));
                record.setPrincipal(rs.getBigDecimal("principal"));
                record.setDebt(rs.getBigDecimal("debt"));

                Statement stmt2 = conn.createStatement();
                PreparedStatement prepared2 = conn.prepareStatement("SELECT * FROM amortisationLines where amortisationId = (?);");
                prepared2.setString(1, amortisationId);
                ResultSet rs2 = prepared2.executeQuery();
                //Creating a JSONObject object
                ArrayList<AmortisationDetail> array = new ArrayList<AmortisationDetail>();
                while ( rs2.next() ) {

                    AmortisationDetail detail = new AmortisationDetail();
                    //Inserting key-value pairs into the json object
                    detail.setAmLineId(String.valueOf(rs2.getInt("amLineId")));
                    detail.setAmortisationId(String.valueOf(rs2.getInt("amortisationId")));
                    detail.setPayment(rs2.getBigDecimal("payment"));
                    detail.setPrincipal(rs2.getBigDecimal("principal"));
                    detail.setInterest(rs2.getBigDecimal("interest"));
                    detail.setBalance(rs2.getBigDecimal("balance"));
                    array.add(detail);

                }
                record.setDetails(array);
                rs2.close();
                return record;


            }

            rs.close();

        }catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Connection quickConect(){
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:testdb.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return conn;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        testCanConnect();
    }



    public static BigDecimal calcMonthly(BigDecimal principal, BigDecimal interest, BigDecimal durration){
        BigDecimal cvtInterest = interest.divide(BigDecimal.valueOf(12), mc);
        BigDecimal repayment = BigDecimal.valueOf(0.00);
        BigDecimal constOne = BigDecimal.valueOf(1.00);
        BigDecimal stage1 = cvtInterest.multiply((constOne.add(cvtInterest)).pow(durration.intValue()),mc);
        BigDecimal stage2 = (constOne.add(cvtInterest).pow(durration.intValue())).subtract(constOne);
        BigDecimal stage3 = stage1.divide(stage2, mc);
        repayment = principal.multiply(stage3, mc);
        //repayment = principal * ((cvtInterest * ((constOne.add(cvtInterest)).pow(durration.intValue())) / (Math.pow((1 + cvtInterest), durration) -1.0)));

        return repayment;
    }

    public static BigDecimal calcMonthlyBalloon(BigDecimal principal, BigDecimal interest, BigDecimal durration, BigDecimal balloon){
        BigDecimal cvtInterest = interest.divide(BigDecimal.valueOf(12), mc);
        BigDecimal repayment = BigDecimal.valueOf(0.00);
        BigDecimal constOne = BigDecimal.valueOf(1.00);
        BigDecimal stage1 = balloon.divide((constOne.add(cvtInterest)).pow(durration.intValue()),mc);    //((constOne.add(cvtInterest)).pow(durration.intValue()));
        BigDecimal stage2 = principal.subtract(stage1);
        BigDecimal stage3 =  cvtInterest.divide(constOne.subtract(((constOne.add(cvtInterest)).pow(durration.negate().intValue(), mc))),mc);    //(constOne.add(cvtInterest).pow(durration.intValue())).subtract(constOne);
        BigDecimal stage4 = stage2.multiply(stage3, mc);
        repayment = principal.subtract(stage4, mc);
        //repayment = (principal - (balloon / (Math.pow((1 + cvtInterest),durration))) * ( cvtInterest / (1 - Math.pow((1 + cvtInterest),-durration))));

        return repayment;
    }
//(10,000 / ((1 + 0.00625) ^ 60)))  * (0.00625 / (1 - (1 + 0.00625) ^ -60))
//(20,000 - (10,000 / ((1 + 0.00625) ^ 60)))  * (0.00625 / (1 - (1 + 0.00625) ^ -60))


    public static BigDecimal calcInterest(BigDecimal balance, BigDecimal interest){
        BigDecimal cvtInterest = interest.divide(BigDecimal.valueOf(12), mc);
        BigDecimal monthInterest = BigDecimal.valueOf(0.00);
        monthInterest = balance.multiply(cvtInterest,mc);

        return monthInterest;
    }

    public static BigDecimal calcPrincipal(BigDecimal payment, BigDecimal interestPaid){
        BigDecimal principalPaid = BigDecimal.valueOf(0.00);
        principalPaid = payment.subtract(interestPaid);
        return principalPaid;
    }

    public static Amortisation createSchedule(BigDecimal totalBal, BigDecimal interest, BigDecimal durration,String userId, BigDecimal balloon){
        Amortisation am1 = new Amortisation();
        am1 = schedule(totalBal, interest, durration, balloon);
        am1.setUserId(userId);
        Integer status = createAmortisation(am1);
        if (status != null){
            am1.setAmortisationId(Integer.toString(status));
            return am1;
        }else{
            return null;
        }
    }


    public static Amortisation schedule(BigDecimal totalBal, BigDecimal interest, BigDecimal durration,BigDecimal balloon){
        BigDecimal monthly = BigDecimal.valueOf(0.00);
        if (balloon == null || balloon.equals(BigDecimal.ZERO)){
            monthly = calcMonthly(totalBal,interest,durration).setScale(2,RoundingMode.HALF_EVEN);
        }else{
            monthly = calcMonthlyBalloon(totalBal,interest,durration, balloon).setScale(2,RoundingMode.HALF_EVEN);
        }

        BigDecimal monthlyCount = BigDecimal.valueOf(0.00);
        BigDecimal balance = totalBal;
        Integer i = 0;
        BigDecimal interestVal = BigDecimal.valueOf(0.00);
        BigDecimal principalPaid = BigDecimal.valueOf(0.00);
        BigDecimal newBalance = BigDecimal.valueOf(0.00);
        Amortisation plan = new Amortisation();
        ArrayList<AmortisationDetail> array = new ArrayList<AmortisationDetail>();
        while (i <= durration.intValue()){
            AmortisationDetail detail = new AmortisationDetail();
            interestVal = calcInterest(balance, interest);
            principalPaid = calcPrincipal(monthly, interestVal);
            newBalance = balance.subtract(principalPaid);
            monthlyCount = monthlyCount.add(monthly,mc);

            detail.setPayment(monthly.setScale(2,RoundingMode.HALF_EVEN));
            detail.setPrincipal(principalPaid.setScale(2,RoundingMode.HALF_EVEN));
            detail.setInterest(interestVal.setScale(2,RoundingMode.HALF_EVEN));
            detail.setBalance(newBalance.setScale(2,RoundingMode.HALF_EVEN));
            balance = newBalance;

            array.add(detail);
            i++;
        }
        plan.setInterest(interest);
        plan.setPrincipal(totalBal.setScale(2,RoundingMode.HALF_EVEN));
        plan.setDebt(monthlyCount.setScale(2,RoundingMode.HALF_EVEN));
        plan.setDetails(array);
        return plan;
    }

}