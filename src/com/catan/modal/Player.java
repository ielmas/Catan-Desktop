package com.catan.modal;

import com.catan.Util.Constants;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Player {

    // properties
    protected ArrayList<Settlement> settlements;
    protected HashMap<String, ArrayList<SourceCard>> sourceCards;
    protected ArrayList<Road> roads;
    private String color;
    private String name;
    protected PriceCard priceCard;

    // constructor
    public Player(String color, String name) {
        settlements = new ArrayList<>();
        roads = new ArrayList<>();
        priceCard = new PriceCard("price_card");
        this.color = color;
        this.name = name;
        sourceCards = new HashMap<>();
        sourceCards.put(Constants.CARD_BRICK,  new ArrayList<>());
        sourceCards.put(Constants.CARD_GRAIN,  new ArrayList<>());
        sourceCards.put(Constants.CARD_LUMBER, new ArrayList<>());
        sourceCards.put(Constants.CARD_ORE,    new ArrayList<>());
        sourceCards.put(Constants.CARD_WOOL,   new ArrayList<>());
    }

    // methods
    public ArrayList<Settlement> getSettlements() {
        return settlements;
    }

    public void setSettlements(ArrayList<Settlement> settlements) {
        this.settlements = settlements;
    }

    public HashMap<String, ArrayList<SourceCard>> getSourceCards() {
        return sourceCards;
    }

    public void setSourceCards(HashMap<String, ArrayList<SourceCard>> sourceCards) {
        this.sourceCards = sourceCards;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSettlementImagePath(String construction) {
        if (construction.equals(Constants.VILLAGE)) {
            switch (color) {
                case Constants.COLOR_RED:
                    return Constants.PATH_VILLAGE_RED;
                case Constants.COLOR_PURPLE:
                    return Constants.PATH_VILLAGE_PURPLE;
                case Constants.COLOR_GREEN:
                    return Constants.PATH_VILLAGE_GREEN;
                case Constants.COLOR_BLUE:
                    return Constants.PATH_VILLAGE_BLUE;
            }
        }
        else if (construction.equals(Constants.CITY)) {
            switch (color) {
                case Constants.COLOR_RED:
                    return Constants.PATH_CITY_RED;
                case Constants.COLOR_PURPLE:
                    return Constants.PATH_CITY_PURPLE;
                case Constants.COLOR_GREEN:
                    return Constants.PATH_CITY_GREEN;
                case Constants.COLOR_BLUE:
                    return Constants.PATH_CITY_BLUE;
            }
        }
        else if (construction.equals(Constants.CIVILISATION)) {
            switch (color) {
                case Constants.COLOR_RED:
                    return Constants.PATH_CIVILISATION_RED;
                case Constants.COLOR_PURPLE:
                    return Constants.PATH_CIVILISATION_PURPLE;
                case Constants.COLOR_GREEN:
                    return Constants.PATH_CIVILISATION_GREEN;
                case Constants.COLOR_BLUE:
                    return Constants.PATH_CIVILISATION_BLUE;
            }
        }

        return Constants.PATH_CIVILISATION_BLUE;
    }

    public Color getRoadColor() {
        switch (color) {
            case Constants.COLOR_RED:
                return Constants.COLOR_RGB_RED;
            case Constants.COLOR_PURPLE:
                return Constants.COLOR_RGB_PURPLE;
            case Constants.COLOR_GREEN:
                return Constants.COLOR_RGB_GREEN;
            default:
                return Constants.COLOR_RGB_BLUE;
        }
    }

    public void getTurnProfit(int dieNumber) {
        for (Settlement settlement: settlements) {
            HashMap<String, Integer> profit = settlement.getTurnProfit(dieNumber);
            Set<String> keys = profit.keySet();
            for (String key: keys) {
//                sourceCards.get(key).add(new SourceCard(key, key));
                for (int i = 0; i < profit.get(key); i++) {
                    sourceCards.get(key).add(new SourceCard(key, key));
                }
            }
        }
    }

    public void showSourceCards() {
        System.out.println(">>>>>>>" + name + "<<<<<<<");
        Set<String> keys = sourceCards.keySet();
        for (String key: keys) {
            System.out.println(key + ": " + sourceCards.get(key).size());
        }
    }

    public boolean hasEnoughResources(String selectedConstruction) {
        Map<String, Integer> price = getPrice(selectedConstruction);;
        Set<String> keys = price.keySet();
        for (String key: keys) {
            if (sourceCards.get(key).size() < price.get(key)) {
                return false;
            }
        }
        return true;
    }

    public void subtractPriceOfConstruction(String selectedConstruction) {
        Map<String, Integer> price = getPrice(selectedConstruction);
        Set<String> keys = price.keySet();
        System.out.println(keys);
        for (String key: keys) {
            int priceOfCard = price.get(key);
            ArrayList<SourceCard> cards = sourceCards.get(key);
            if (priceOfCard > 0) {
                cards.subList(0, priceOfCard).clear();
            }
        }
    }

    private Map<String, Integer> getPrice(String selectedConstruction) {
        Map<String, Integer> price;
        switch (selectedConstruction) {
            case Constants.CITY:
                price = priceCard.getCityPrice();
                break;
            case Constants.VILLAGE:
                price = priceCard.getVillagePrice();
                break;
            case Constants.CIVILISATION:
                price = priceCard.getCivilizationPrice();
                break;
            default:
                price = priceCard.getRoadPrice();
                break;
        }
        return price;
    }

    @Override
    public String toString() {
        return "Player{" +
                "settlements=" + settlements +
                ", sourceCards=" + sourceCards +
                ", roads=" + roads +
                ", color='" + color + '\'' +
                '}';
    }
}