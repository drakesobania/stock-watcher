package models;

import java.io.*;
import java.util.*;

public class StockStore {
    private HashMap<Integer, Stock> stocks = new HashMap<>();

    public Optional<Stock> add(Stock stock) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("stocks.txt"));
            bufferedWriter.append(stock.getSymbol() + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(stock);
    }

    public boolean remove(Stock stock) {
        boolean deleted = false;
        try {
            File currentFile = new File("stocks.txt");
            File newFile = new File("stocks.tmp");

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(currentFile));
            String currentLine;
            while((currentLine = bufferedReader.readLine()) != null) {
                if (currentLine.equals(stock.getSymbol())) {
                    deleted = true;
                    continue;
                }
                bufferedWriter.append(currentLine + "\n");
            }
            bufferedWriter.close();
            bufferedReader.close();
            currentFile.delete();
            newFile.renameTo(currentFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deleted;
    }
}
