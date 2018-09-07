package com.hfad.fullversionfood;

public class Ingredient {
    private String name;
    private int id;
    private String parent;
    static final String[] parents  = {"Алкоголь","Крупы","Мясо","Соусы","Приправы"};
    Ingredient(String name, int parentId, int id){
        this.name = name;
        this.parent = parents[parentId];
        this.id = id;
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
    public int getId(){
        return id;
    }

    static final Ingredient ingredients []= {
            new Ingredient("Вино белое",0,0),
            new Ingredient("Вино красное",0,1),
            new Ingredient("Водка",0,2),
            new Ingredient("Виски",0,3),
            new Ingredient("Гречка",1,4),
            new Ingredient("Рис",1,5),
            new Ingredient("Пшенка",1,6),
            new Ingredient("Горох",1,7),
            new Ingredient("Куринное филе",2,8),
            new Ingredient("Куринные ноги",2,9),
            new Ingredient("Свинина",2,10),
            new Ingredient("Говядина",2,11),
            new Ingredient("Томатный соус",3,12),
            new Ingredient("Соевый соус",3,13),
            new Ingredient("Черный перец",4,14),
            new Ingredient("Красный перец",4,15),
    };
}
