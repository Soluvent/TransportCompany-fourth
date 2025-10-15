// Абстрактний клас Vehicle
abstract class Vehicle {
    protected String brand;
    protected int capacity;

    public Vehicle(String brand, int capacity) {
        this.brand = brand;
        this.capacity = capacity;
    }

    // Абстрактний метод для руху
    public abstract void move();

    // Гетери
    public String getBrand() {
        return brand;
    }

    public int getCapacity() {
        return capacity;
    }

    public void displayInfo() {
        System.out.println("Транспортний засіб: " + brand + ", місткість: " + capacity);
    }
}

// Інтерфейс для завантаження вантажу
interface Loadable {
    void loadCargo(double weight);
    void unloadCargo();
    double getCurrentLoad();
    double getMaxLoad();
}

// Клас Bus
class Bus extends Vehicle {
    private int passengerCount;

    public Bus(String brand, int capacity) {
        super(brand, capacity);
        this.passengerCount = 0;
    }

    @Override
    public void move() {
        System.out.println("Автобус " + brand + " їде по міському маршруту");
    }

    public void boardPassengers(int count) {
        if (passengerCount + count <= capacity) {
            passengerCount += count;
            System.out.println(count + " пасажирів сіли в автобус. Всього: " + passengerCount);
        } else {
            System.out.println("Недостатньо місць! Максимум: " + capacity);
        }
    }

    public void dropPassengers(int count) {
        if (count <= passengerCount) {
            passengerCount -= count;
            System.out.println(count + " пасажирів вийшли з автобусу. Залишилось: " + passengerCount);
        }
    }
}

// Клас Truck з реалізацією Loadable
class Truck extends Vehicle implements Loadable {
    private double maxLoad;
    private double currentLoad;

    public Truck(String brand, int capacity, double maxLoad) {
        super(brand, capacity);
        this.maxLoad = maxLoad;
        this.currentLoad = 0;
    }

    @Override
    public void move() {
        System.out.println("Вантажівка " + brand + " їде по шосе з вантажем " + currentLoad + " кг");
    }

    @Override
    public void loadCargo(double weight) {
        if (currentLoad + weight <= maxLoad) {
            currentLoad += weight;
            System.out.println("Завантажено " + weight + " кг. Поточний вантаж: " + currentLoad + " кг");
        } else {
            System.out.println("Перевантаження! Максимум: " + maxLoad + " кг");
        }
    }

    @Override
    public void unloadCargo() {
        System.out.println("Розвантажено " + currentLoad + " кг");
        currentLoad = 0;
    }

    @Override
    public double getCurrentLoad() {
        return currentLoad;
    }

    @Override
    public double getMaxLoad() {
        return maxLoad;
    }
}

// Клас Bicycle з реалізацією Loadable
class Bicycle extends Vehicle implements Loadable {
    private double maxLoad;
    private double currentLoad;
    private boolean hasBasket;

    public Bicycle(String brand, boolean hasBasket) {
        super(brand, 1);
        this.hasBasket = hasBasket;
        this.maxLoad = hasBasket ? 10.0 : 5.0;
        this.currentLoad = 0;
    }

    @Override
    public void move() {
        System.out.println("Велосипед " + brand + " їде по велодоріжці");
    }

    @Override
    public void loadCargo(double weight) {
        if (!hasBasket && weight > 0) {
            System.out.println("Велосипед не має кошика для вантажу!");
            return;
        }
        if (currentLoad + weight <= maxLoad) {
            currentLoad += weight;
            System.out.println("Завантажено " + weight + " кг у кошик. Поточний вантаж: " + currentLoad + " кг");
        } else {
            System.out.println("Кошик переповнений! Максимум: " + maxLoad + " кг");
        }
    }

    @Override
    public void unloadCargo() {
        if (currentLoad > 0) {
            System.out.println("Розвантажено " + currentLoad + " кг з кошика");
            currentLoad = 0;
        }
    }

    @Override
    public double getCurrentLoad() {
        return currentLoad;
    }

    @Override
    public double getMaxLoad() {
        return maxLoad;
    }
}

// Головний клас для демонстрації
class TransportCompanyDemo {
    public static void main(String[] args) {
        System.out.println("=== ТРАНСПОРТНА КОМПАНІЯ ===\n");

        // Створення транспортних засобів
        Bus bus = new Bus("Mercedes", 50);
        Truck truck = new Truck("Volvo", 2, 15000);
        Bicycle bicycle = new Bicycle("Giant", true);

        // Демонстрація поліморфізму через базовий клас
        System.out.println("--- Поліморфізм: всі транспортні засоби рухаються ---");
        Vehicle[] vehicles = {bus, truck, bicycle};
        for (Vehicle v : vehicles) {
            v.displayInfo();
            v.move();
            System.out.println();
        }

        // Робота з автобусом
        System.out.println("--- Робота з автобусом ---");
        bus.boardPassengers(30);
        bus.move();
        bus.boardPassengers(25);
        bus.dropPassengers(15);
        System.out.println();

        // Робота з вантажівкою
        System.out.println("--- Робота з вантажівкою ---");
        truck.loadCargo(5000);
        truck.loadCargo(8000);
        truck.move();
        truck.loadCargo(3000); // Спроба перевантаження
        truck.unloadCargo();
        System.out.println();

        // Робота з велосипедом
        System.out.println("--- Робота з велосипедом ---");
        bicycle.loadCargo(7);
        bicycle.move();
        bicycle.loadCargo(5); // Спроба перевантаження
        bicycle.unloadCargo();
        System.out.println();

        // Поліморфізм через інтерфейс Loadable
        System.out.println("--- Поліморфізм: завантаження вантажу ---");
        Loadable[] loadableVehicles = {truck, bicycle};
        for (Loadable lv : loadableVehicles) {
            System.out.println("Максимальне навантаження: " + lv.getMaxLoad() + " кг");
            lv.loadCargo(5);
            System.out.println("Поточне навантаження: " + lv.getCurrentLoad() + " кг");
            System.out.println();
        }

        // Демонстрація інкапсуляції
        System.out.println("--- Демонстрація інкапсуляції ---");
        System.out.println("Бренд автобусу: " + bus.getBrand());
        System.out.println("Місткість: " + bus.getCapacity());
        // Прямий доступ до полів заборонений через модифікатор private
    }
}