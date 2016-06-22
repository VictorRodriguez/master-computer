package comm.model;

import java.util.ArrayList;

/**
 * Created by Julio on 17/11/13.
 * @author Julio Cesar Roa Gil
 * @version 1.0
 */
public class Meal {

    private int id;
    private String name;
    private int calorieValue;
    private ArrayList<Ingredient> ingredients;

    /**
     *
     * @return meal ID
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return meal NAME
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return meal CALORIE VALUE
     */
    public int getCalorieValue() {
        return calorieValue;
    }

    /**
     *
     * @param calorieValue
     */
    public void setCalorieValue(int calorieValue) {
        this.calorieValue = calorieValue;
    }

    /**
     *
     * @return ingredients list of a meal
     */
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     *
     * @param ingredients
     */
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Calculate the total calories of a meal including calories ingredients
     * @return meal CALORIE VALUE include ingredients
     */
    public int calculateCalories() {
        int caloriesSum = 0;
        if (this.getIngredients() != null){
            for(int i=0; i < this.getIngredients().size(); i++){
                caloriesSum = caloriesSum + this.getIngredients().get(i).getCalorieValue();
            }
            if(this.getCalorieValue() > 0){
                caloriesSum = caloriesSum + this.getCalorieValue();
            }

            return caloriesSum;
        }
        return this.calorieValue;
    }
}
