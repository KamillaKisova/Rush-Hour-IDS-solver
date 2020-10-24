import java.util.ArrayList;

/**
 *
 */
public class Node {
    private int depth;
    private Node prevNode;
    private String lastOperation;
    private ArrayList<Car> cars;

    /**
     * Constructor, creating an empty node
     */
    public Node(){}

    /**
     * Constructor for creating a full node
     * @param depth depth of the node (distance from starter node)
     * @param prev previous node (what this was generated from)
     * @param op operation made to get here
     * @param cars ArrayList of objects Car
     */
    public Node(int depth, Node prev, String op, ArrayList<Car> cars){
        this.depth = depth;
        this.prevNode = prev;
        this.lastOperation = op;
        this.cars = cars;
    }

    /*
     Getters and setters follow here
     */

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Node getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(Node prevNode) {
        this.prevNode = prevNode;
    }

    public String getLastOperation() {
        return lastOperation;
    }

    public void setLastOperation(String lastOperation) {
        this.lastOperation = lastOperation;
    }

    /**
     * Getter for our ArrayList of Cars.
     * Iterates through the ArrayList, building up a new one.
     * @return returns new ArrayList of Cars
     */
    public ArrayList<Car> getCars() {
        ArrayList<Car> newCars = new ArrayList<>();
        Car newCar;
        for(int i = 0; i < cars.size(); i++){
            newCar = new Car(cars.get(i));
            newCars.add(newCar);
        }
        return newCars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    /**
     *
     * @return returns a string which represents
     */
    public String representNode(){
        StringBuilder node = new StringBuilder();
        for(int i = 0; i < cars.size(); i++){
            node.append(cars.get(i).representCar());
        }
        return node.toString();
    }
}
