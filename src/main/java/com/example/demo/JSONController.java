package com.example.demo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController

//@RequestMapping("/flights")
@RequestMapping("/flights")
public class JSONController {

    @GetMapping("")
    public List<Flight> getFlights() {
        Flight flight1 = new Flight();
        flight1.departDate = "2017-04-21 14:34";
        Ticket ticket1 = new Ticket();
        Passenger passenger1 = new Passenger();
        passenger1.firstName = "Some name";
        passenger1.lastName = "Some other name";
        ticket1.passenger = passenger1;
        ticket1.price = 200;
        List<Ticket> tickets1 = new ArrayList<>();
        tickets1.add(ticket1);
        flight1.tickets = tickets1;


        Flight flight2 = new Flight();
        flight2.departDate = "2017-04-21 14:34";
        Ticket ticket = new Ticket();
        Passenger passenger = new Passenger();
        passenger.firstName = "Some other name";
        passenger.lastName = null;
        ticket.passenger = passenger;
        ticket.price = 400;
        List<Ticket> tickets2 = new ArrayList<>();
        tickets2.add(ticket);
        flight2.tickets = tickets2;


        return Arrays.asList(flight1, flight2);
    }

    @GetMapping("/flight")
    public Flight getFlight() {
        Flight flight = new Flight();
        flight.departDate = "2017-04-21 14:34";
        Ticket ticket = new Ticket();
        Passenger passenger = new Passenger();
        passenger.firstName = "Some name";
        passenger.lastName = "Some other name";
        ticket.passenger = passenger;
        ticket.price = 200;
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);
        flight.tickets = tickets;
        return flight;
    }

    public static class Person {
        private String name;
        private int age;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
    }

    public static class Flight {
        private String departDate;
        private List<Ticket> tickets;

        @JsonProperty("Departs")
        public String getDepartDate() {
            return departDate;
        }
        public void setDepartDate(String departDate) {
            this.departDate = departDate;
        }

        @JsonProperty("Tickets")
        public List<Ticket> getTickets() {
            return tickets;
        }
        public void setTickets(List<Ticket> tickets) {
            this.tickets = tickets;
        }
    }

    public static class Ticket {
        private Passenger passenger;
        private int price;

        @JsonProperty("Passenger")
        public Passenger getPassenger() {
            return passenger;
        }
        public void setPassenger(Passenger passenger) {
            this.passenger = passenger;
        }
        @JsonProperty("Price")
        public int getPrice() {
            return price;
        }
        public void setPrice(int price) {
            this.price = price;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Passenger {
        private String firstName;
        private String lastName;

        @JsonProperty("FirstName")
        public String getFirstName() {
            return firstName;
        }
        public void  setFirstName(String firstName) {
            this.firstName = firstName;
        }
        @JsonProperty("LastName")
        public String getLastName() {
            return lastName;
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }


    @PostMapping(value = "/tickets/total", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getTotal(@RequestBody Map<String, Ticket[]> body) {

        int price1 = body.get("Tickets")[0].getPrice();
        int price2 = body.get("Tickets")[1].getPrice();
        int total = price1 + price2;

        return "{\n"+"\"result\": "+total+"\n}";
    }
}
