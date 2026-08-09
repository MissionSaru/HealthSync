package service;

public class BMIService {

    // BMI = weight(kg) / height(m)^2, rounded to 2 decimals
    public static double calculateBMI(double weightKg, double heightM) {
        if (weightKg <= 0 || heightM <= 0) {
            return 0;
        }
        double bmi = weightKg / (heightM * heightM);
        return Math.round(bmi * 100.0) / 100.0;
    }

    public static String getCategory(double bmi) {
        if (bmi <= 0) {
            return "Unknown";
        }
        if (bmi < 18.5) {
            return "Underweight";
        }
        if (bmi < 25) {
            return "Normal";
        }
        if (bmi < 30) {
            return "Overweight";
        }
        return "Obese";
    }
}
