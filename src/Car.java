/**
 * Object representing a car in Rush Hour
 *
 * @author Kamilla Kisová
 */
public class Car {
    private String color;
    private int size;
    private int x;
    private int y;
    private char orientation;

    /**
     * Constructor for Car, fully initializing
     * @param color color of the car
     * @param size size (2 or 3)
     * @param x position x (column)
     * @param y position y (row)
     * @param orientation orientation (vertical = v, horizontal = h)
     */
    public Car(String color, int size, int x, int y, char orientation){
        this.color = color;
        this.size = size;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    /**
     * Constructor for Car, initializing from another Car
     * @param car already running instance of Car
     */
    public Car(Car car){
        this.color = car.color;
        this.size = car.size;
        this.x = car.x;
        this.y = car.y;
        this.orientation = car.orientation;
    }

    public String getColor(){
        return color;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getOrientation() {
        return orientation;
    }

    /**
     *
     * @return returns a string about the car
     */
    public String representCar(){
        return "" + color + size + x + y + orientation;
    }
}
