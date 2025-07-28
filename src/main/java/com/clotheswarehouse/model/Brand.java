package com.clotheswarehouse.model;

public enum Brand {
    NIKE("Nike"),
    ADIDAS("Adidas"),
    PUMA("Puma"),
    UNDER_ARMOUR("Under Armour"),
    REEBOK("Reebok"),
    NEW_BALANCE("New Balance"),
    CONVERSE("Converse"),
    VANS("Vans"),
    GUCCI("Gucci"),
    PRADA("Prada"),
    LOUIS_VUITTON("Louis Vuitton"),
    CHANEL("Chanel"),
    VERSACE("Versace"),
    ARMANI("Armani"),
    DOLCE_GABBANA("Dolce & Gabbana"),
    CALVIN_KLEIN("Calvin Klein"),
    TOMMY_HILFIGER("Tommy Hilfiger"),
    RALPH_LAUREN("Ralph Lauren"),
    HUGO_BOSS("Hugo Boss"),
    BALENCIAGA("Balenciaga");

    private final String displayName;

    Brand(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
