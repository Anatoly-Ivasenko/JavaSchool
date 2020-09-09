import java.util.*;
import java.util.stream.Collectors;

public class CarTypezator {

    public static class Car {
        private String model;
        private String type;

        public Car(String model, String type) {
            this.model = model;
            this.type = type;
        }

        String getModel() {
            return model;
        }

        String getType() {
            return type;
        }

        public String toString() {
            return model + " " + type;
        }
    }

    public static void main(String[] args) {
        ArrayList<Car> carList = new ArrayList<>();
        carList.add(new Car("Lada","Sedan"));
        carList.add(new Car("Toyota","Hatchback"));
        carList.add(new Car("Renault","Crossover"));
        carList.add(new Car("Lada","Hatchback"));
        carList.add(new Car("Mercedes","Cabrio"));
        carList.add(new Car("Toyota","Crossover"));
        carList.add(new Car("Lada","Universal"));
        carList.add(new Car("Mazda","Sedan"));
        carList.add(new Car("Moskvicth","Combi"));
        carList.add(new Car("Skoda","Sedan"));
        carList.add(new Car("Honda","Sedan"));
        carList.add(new Car("Datsun","Hatchback"));
        carList.add(new Car("Skoda","Liftback"));

        for (Car car: carList) {
            System.out.println(car);
        }

        System.out.println("------------------------------------------------");
        HashMap<String, ArrayList<String>> carTypes = new HashMap<>();
        for (Car car: carList) {
            if (!carTypes.containsKey(car.getType())) {
                carTypes.put(car.getType(), new ArrayList<>());
            }

            ArrayList<String> value = carTypes.get(car.getType());
            value.add(car.getModel());
        }

        for (Map.Entry<String, ArrayList<String>> entry : carTypes.entrySet()) {
            System.out.print(entry.getKey()+": ");
            System.out.println(entry.getValue().toString());
        }

        System.out.println("---------------using Stream API-----------------");
        Map<String,List<Car>> carstype = carList.stream().collect(Collectors.groupingBy(Car::getType));
        for (Map.Entry<String, List<Car>> entry : carstype.entrySet()) {
            System.out.print(entry.getKey()+": ");
            for (Car car : entry.getValue()) {
                System.out.print(car.getModel() + " ");
            }
            System.out.println();
        }
    }
}
