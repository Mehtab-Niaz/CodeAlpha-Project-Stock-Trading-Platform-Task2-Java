import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Portfolio {
    private Map<String, Integer> stocks;
    private double cashBalance;

    public Portfolio(double initialCash) {
        stocks = new HashMap<>();
        this.cashBalance = initialCash;
    }

    public void buyStock(String symbol, int quantity, double price) {
        if (cashBalance >= quantity * price) {
            stocks.put(symbol, stocks.getOrDefault(symbol, 0) + quantity);
            cashBalance -= quantity * price;
            System.out.println("Bought " + quantity + " shares of " + symbol);
        } else {
            System.out.println("Insufficient funds to buy " + quantity + " shares of " + symbol);
        }
    }

    public void sellStock(String symbol, int quantity, double price) {
        if (stocks.getOrDefault(symbol, 0) >= quantity) {
            stocks.put(symbol, stocks.get(symbol) - quantity);
            cashBalance += quantity * price;
            System.out.println("Sold " + quantity + " shares of " + symbol);
        } else {
            System.out.println("Not enough shares to sell.");
        }
    }

    public void displayPortfolio(Map<String, Stock> marketData) {
        System.out.println("Portfolio:");
        for (Map.Entry<String, Integer> entry : stocks.entrySet()) {
            String symbol = entry.getKey();
            int quantity = entry.getValue();
            double currentPrice = marketData.get(symbol).getPrice();
            System.out.println(symbol + ": " + quantity + " shares, Current Value: $" + (quantity * currentPrice));
        }
        System.out.println("Cash Balance: $" + cashBalance);
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Stock> marketData = new HashMap<>();
        marketData.put("AAPL", new Stock("AAPL", 150.0));
        marketData.put("GOOGL", new Stock("GOOGL", 2800.0));
        marketData.put("AMZN", new Stock("AMZN", 3300.0));

        Portfolio portfolio = new Portfolio(10000.0);  // Initial cash balance

        while (true) {
            System.out.println("Market Data:");
            for (Stock stock : marketData.values()) {
                System.out.println(stock.getSymbol() + ": $" + stock.getPrice());
            }

            System.out.println("\nOptions:");
            System.out.println("1. Buy Stock");
            System.out.println("2. Sell Stock");
            System.out.println("3. View Portfolio");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter stock symbol: ");
                String symbol = scanner.next();
                System.out.print("Enter quantity to buy: ");
                int quantity = scanner.nextInt();
                if (marketData.containsKey(symbol)) {
                    portfolio.buyStock(symbol, quantity, marketData.get(symbol).getPrice());
                } else {
                    System.out.println("Invalid stock symbol.");
                }
            } else if (choice == 2) {
                System.out.print("Enter stock symbol: ");
                String symbol = scanner.next();
                System.out.print("Enter quantity to sell: ");
                int quantity = scanner.nextInt();
                if (marketData.containsKey(symbol)) {
                    portfolio.sellStock(symbol, quantity, marketData.get(symbol).getPrice());
                } else {
                    System.out.println("Invalid stock symbol.");
                }
            } else if (choice == 3) {
                portfolio.displayPortfolio(marketData);
            } else if (choice == 4) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }

            System.out.println();
        }

        scanner.close();
    }
}
