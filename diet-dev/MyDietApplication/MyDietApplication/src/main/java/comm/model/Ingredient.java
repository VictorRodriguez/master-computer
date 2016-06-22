package comm.model;

/**
 * Created by Julio on 17/11/13.
 * @author Julio Cesar Roa Gil
 * @version 1.0
 */
public class Ingredient{

    private int id;
    private String name;
    private int calorieValue;

    /*
    *
    * @return ingredient ID
    * */
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
    * @return ingredient NAME
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
    * @return ingredient CALORIE VALUE
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
}
