package org.example;

public class ConversionRates {
    public float EURtoUSDrate = 1.15f;
    public float EURtoUSD(float Euro){
        return Euro * EURtoUSDrate;
    }

    public float USDtoEURrate = 0.87f;
    public float USDtoEUR(float USD){
        return USD * USDtoEURrate;
    }
    public float convertFunds(Currency from, Currency to, float amount){
        if (from == Currency.Euro & to == Currency.Dollar){
            return EURtoUSD(amount);
        } else if (from == Currency.Dollar & to == Currency.Euro) {
            return USDtoEUR(amount);
        }
        return amount;
    }
}

