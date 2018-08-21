package com.hfad.fullversionfood;

public class Ingredient {
    private String name;
    private String parent;
    static final String[] parents  = {"Алкоголь","Крупы","Мясо","Соусы","Приправы"};
    Ingredient(String name, int parentId){
        this.name = name;
        this.parent = parents[parentId];
    }
    public String getName(){
        return name;
    }
    public String getParent(){
        return parent;
    }
    public String toSting(){
        return name;
    }
    static final Ingredient ingredients []= {
            new Ingredient("Вино белое",0),
            new Ingredient("Вино красное",0),
            new Ingredient("Водка",0),
            new Ingredient("Виски",0),
            new Ingredient("Гречка",1),
            new Ingredient("Рис",1),
            new Ingredient("Пшенка",1),
            new Ingredient("Горох",1),
            new Ingredient("Куринное филе",2),
            new Ingredient("Куринные ноги",2),
            new Ingredient("Свинина",2),
            new Ingredient("Говядина",2),
            new Ingredient("Томатный соус",3),
            new Ingredient("Соевый соус",3),
            new Ingredient("Черный перец",4),
            new Ingredient("Красный перец",4),
    };
}
