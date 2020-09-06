import java.util.Scanner;

public class TemperatureConverters {

    public static float convertToFahrenheit(float temperatureCelsius) {
        return 9f / 5f * temperatureCelsius + 32f;
    }

    public static float convertToKelvin(float temperatureCelsius) {
        return temperatureCelsius + 273.15f;
    }

    public static float convertToRankin(float temperatureCelsius) {
        return convertToFahrenheit(temperatureCelsius) + 459.67f;
    }

    public static float convertToReomure(float temperatureCelsius) {
        return 0.8f * temperatureCelsius;
    }

    public static void main(String[] args) {
        System.out.println("Введите температуру в градусах целсия:");
        Scanner in = new Scanner(System.in);
        float temp = in.nextFloat();
        System.out.println(temp + " C = " + convertToFahrenheit(temp) + " F = " + convertToKelvin(temp) + " K = " + convertToRankin(temp) + " Ra = " + convertToReomure(temp) + " Re");
    }
}

