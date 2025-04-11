// Klasa abstrakcyjna i polimorfizm
abstract class Vehicle {
    // Metoda abstrakcyjna
    public abstract void drive();
}
// Klasa Car dziedziczy po klasie Vehicle
class Car extends Vehicle {
    @Override
    public void drive() {
        System.out.println("Samochód jedzie po drodze.");
    }
}
// Klasa Bike dziedziczy po klasie Vehicle
class Bike extends Vehicle {
    @Override
    public void drive() {
        System.out.println("Rower jedzie po ścieżce rowerowej.");
    }
}
// Klasa Boat dziedziczy po klasie Vehicle
class Boat extends Vehicle {
    @Override
    public void drive() {
        System.out.println("Łódź płynie po wodzie.");
    }
}
// Interfejs i polimorfizm
interface Refuelable {
    void refuel();
}
// Klasa implementująca interfejs Refuelable
class GasStation implements Refuelable {
    @Override
    public void refuel() {
        System.out.println("Pojazd tankuje paliwo na stacji benzynowej.");
    }
}
// Klasa implementująca interfejs Refuelable
class ChargingStation implements Refuelable {
    @Override
    public void refuel() {
        System.out.println("Pojazd elektryczny ładuje baterię na stacji ładowania.");
    }
}
// Klasa implementująca interfejs Refuelable
class Marina implements Refuelable {
    @Override
    public void refuel() {
        System.out.println("Łódź tankuje paliwo w marinie.");
    }
}
public class Main {
    public static void main(String[] args) {
        // Sekcja A: Klasa abstrakcyjna i polimorfizm
        System.out.println("=== Klasa abstrakcyjna ===");
        Vehicle[] vehicles = {new Car(), new Bike(), new Boat()}; // Rzutowanie w górę do klasy bazowej

        // Wywołania polimorficzne metody drive
        for (Vehicle vehicle : vehicles) {
            vehicle.drive(); // Każda klasa potomna wywołuje własną implementację
        }
        // Sekcja B: Interfejs i polimorfizm
        System.out.println("\n--- Interfejs ---");
        Refuelable[] stations = {new GasStation(), new ChargingStation(), new Marina()}; // Rzutowanie do interfejsu

        // Wywołania polimorficzne metody refuel
        for (Refuelable station : stations) {
            station.refuel(); // Każda klasa wywołuje własną implementację
        }
    }
}
