# Flight Booking System – OOP Project

## Overview
This project is a Flight Booking System developed using Object-Oriented Programming (OOP) principles.  
It simulates an airline reservation system that allows users to search flights, book tickets, manage reservations, and view available flights.

The system demonstrates key OOP concepts such as:
- Classes and Objects
- Encapsulation
- Inheritance
- Polymorphism
- Abstraction

---

## Project Objectives
- Manage flight schedules efficiently
- Allow passengers to book and cancel flights
- Maintain seat availability for each flight
- Store and retrieve booking information
- Provide a simple user interface (CLI or GUI depending on implementation)

---

## System Features

### Flight Management
- Add new flights
- Update flight details
- View all available flights

### Passenger Management
- Register new passengers
- Store passenger information

### Booking System
- Book a flight ticket
- Cancel a booking
- Check seat availability
- View booking history

---

## OOP Design

### Classes Used
- Flight
- Passenger
- Booking
- AirportSystem (main controller class)

---

### Encapsulation
All class attributes are private and accessed through getters and setters.

---

### Inheritance
If implemented:
- A base class such as User
- Derived classes such as Passenger and Admin

---

### Polymorphism
- Method overriding for different user actions
- Flexible implementations for booking and display functions

---

### Abstraction
- Internal system logic is hidden from the user
- Users interact with high-level functions such as booking and cancellation

---

## Main Functionalities

### Add Flight
Creates a new flight with ID, destination, time, and available seats.

### Search Flights
Searches flights by destination or flight ID.

### Book Flight
Assigns a seat to a passenger and updates seat availability.

### Cancel Booking
Removes an existing booking and restores seat availability.

### View Flights
Displays all available flights in the system.

---

## System Flow
1. User starts the system
2. Views available flights
3. Selects a flight
4. Enters passenger details
5. Booking is confirmed
6. System updates seat availability

---

## Technologies Used
- Object-Oriented Programming language (Java / C++ / Python)
- Console-based or GUI-based interface

---

## How to Run

### Java
```bash
javac Main.java
java Main
