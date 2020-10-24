import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 *
 * @author Kamilla Kisová
 */
public class RushHour_B {
    static final int ROW = 6;
    static final int COLUMN = 6;
    static final int LEFT = 0;
    static final int RIGHT = 1;
    static final int UP = 2;
    static final int DOWN = 3;
    static int car_count;
    static final int MAX_DEPTH = 93;
    static final int MAX_CARS = 17;
    static int processedCount;
    static Stack<Node> stack;
    static HashSet<String> hashSet;

    /**
     * Function to check if a position of (x,y) is free to move to.
     * The name outOfBound remained from the time when it was only checking if the position was in bound or not.
     * @param cars Arraylist of objects Car
     * @param posX position X (column)
     * @param posY position Y (row)
     * @return returns
     */
    static boolean outOfBound(ArrayList<Car> cars, int posX, int posY){
        if(posX <= COLUMN && posX > 0 && posY <= ROW && posY > 0){
            for(int i = 0; i < car_count; i++){
                for(int j = 0; j < cars.get(i).getSize(); j++){
                    if(cars.get(i).getOrientation() == 'h'){
                        if(cars.get(i).getX() + j == posX && cars.get(i).getY() == posY)
                            return true;
                    }
                    else if(cars.get(i).getOrientation() == 'v'){
                        if(cars.get(i).getX() == posX && cars.get(i).getY() + j == posY)
                            return true;
                    }
                }
            }
        }
        else return true;
        return false;
    }

    /**
     * Generates a single new node
     * @param node node to generate from
     * @param carNum which car to modify in the node
     * @param operation which operation to do on the selected car
     * @return returns 0 if a new node was not generated, 1 if it was
     */
    static int newNode(Node node, int carNum, int operation){
        Node newNode = new Node();
        ArrayList<Car> newCars = node.getCars();
        Car car = newCars.get(carNum);
        if(operation == LEFT){
            if(outOfBound(newCars, car.getX() - 1, car.getY()))
                return 0;
            newCars.get(carNum).setX(newCars.get(carNum).getX() - 1);
            newNode.setLastOperation("LEFT(" + node.getCars().get(carNum).getColor() + ")");
        }
        else if(operation == RIGHT){
            if(outOfBound(newCars, car.getX() + car.getSize(), car.getY()))
                return 0;
            newCars.get(carNum).setX(newCars.get(carNum).getX() + 1);
            newNode.setLastOperation("RIGHT(" + node.getCars().get(carNum).getColor() + ")");
        }
        else if(operation ==  UP){
            if(outOfBound(newCars, car.getX(), car.getY() - 1))
                return 0;
            newCars.get(carNum).setY(newCars.get(carNum).getY() - 1);
            newNode.setLastOperation("UP(" + node.getCars().get(carNum).getColor() + ")");
        }
        else if(operation == DOWN){
            if(outOfBound(newCars, car.getX(), car.getY() + car.getSize()))
                return 0;
            newCars.get(carNum).setY(newCars.get(carNum).getY() + 1);
            newNode.setLastOperation("DOWN(" + node.getCars().get(carNum).getColor() + ")");
        }
        newNode.setPrevNode(node);
        newNode.setDepth(node.getDepth() + 1);
        newNode.setCars(newCars);
        if(hashSet.contains(newNode.representNode()))
            return 0;
        hashSet.add(newNode.representNode());
        stack.push(newNode);
        return 1;
    }

    /**
     * Generates nodes by a selected car
     * @param start node to start from
     * @param carNum car to generate from
     * @return returns number of new nodes generated
     */
    static int generateNodes(Node start, int carNum){
        int count = 0;
        if(start.getCars().get(carNum).getOrientation() == 'v'){
            count += newNode(start, carNum, UP);
            count += newNode(start, carNum, DOWN);
        }
        else if(start.getCars().get(carNum).getOrientation() == 'h'){
            count += newNode(start, carNum, LEFT);
            count += newNode(start, carNum, RIGHT);
        }
        return count;
    }

    /**
     * DLS util function for IDS. Checks if current (start) node is a solution, if yes, returns it,
     * else generates new nodes and iterates through them.
     * @param start starter node
     * @param limit depth limitation
     * @return returns a solution if found, else null
     */
    static Node DLS(Node start, int limit){
        if(start.getDepth() == 16){

            printMap(start.getCars());
            System.out.println(start.getDepth() + " depth");
        }
        //System.out.println(start.representNode());
        processedCount++;
        Car important = start.getCars().get(0);
        if(important.getOrientation() == 'v'){
            if(important.getY() == 0)
                return start;
        }
        else if(important.getOrientation() == 'h'){
            if(important.getX() == COLUMN - important.getSize() + 1)
                return start;
        }

        if(limit <= 0)
            return null;

        Node found;
        int count = 0;
        for(int i = 0; i < car_count; i++){
            count += generateNodes(start, i);
        }
        for(int i = 0; i < count; i++){
            if(stack.isEmpty())
                return null;
            found = DLS(stack.pop(), limit - 1);
            if(found != null){
                return found;
            }
        }

        return null;
    }

    /**
     * Iterative Deepening Depth First Search
     * @param start node we start from AKA starter position we gave in the txt file
     * @return returns the final node (solution) if it exists
     */
    static Node IDDFS(Node start){
        Node finalNode;
        for(int i = 0; i < MAX_DEPTH; i++){
            System.out.println("--------------------------");
            hashSet = new HashSet<>();
            stack = new Stack<>();
            finalNode = DLS(start, i);
            if(finalNode != null)
                return finalNode;
        }
        return null;
    }

    /**
     * Creates an array from a string
     * @param carString string of cars
     */
    static ArrayList<Car> createArray(String[] carString){
        int i, size, x, y;
        String[] singleCar;
        ArrayList<Car> cars = new ArrayList<>();
        Car car;
        for(i = 0; i < car_count; i++){
            singleCar = carString[i].split(" ");
            size = Integer.parseInt(String.valueOf(singleCar[1]));
            x = Integer.parseInt(String.valueOf(singleCar[2]));
            y = Integer.parseInt(String.valueOf(singleCar[3]));
            car = new Car(singleCar[0], size, x, y, singleCar[4].charAt(0));
            cars.add(i,car);
        }
        return cars;
    }

    static void printPath(Node node){
        System.out.println("Path taken to solution:");
        Stack<String> path = new Stack<>();
        while(node != null){
            path.add(node.getLastOperation());
            node = node.getPrevNode();
        }
        path.pop();
        int size = path.size();
        for(int i = 0; i < size; i++){
            System.out.print(path.pop());
            if(i < size - 1){
                System.out.print(", ");
            }
        }
    }

    /**
     *
     * @param cars
     */
    public static void printMap(ArrayList<Car> cars){
        int i, j;
        char[][] map = new char[ROW][COLUMN];
        System.out.println("This is your map:");

        for(i = 0; i < ROW; i++){
            for(j = 0; j < COLUMN; j++){
                map[i][j] = '.';
            }
        }

        for(i = 0; i < car_count; i++){
            if(cars.get(i).getOrientation() == 'v'){
                for(j = 0; j < cars.get(i).getSize(); j++){
                    map[cars.get(i).getY() - 1 + j][cars.get(i).getX() - 1] = cars.get(i).getColor().charAt(0);
                }
            }
            else{
                for(j = 0; j < cars.get(i).getSize(); j++){
                    map[cars.get(i).getY() - 1][cars.get(i).getX() - 1 + j] = cars.get(i).getColor().charAt(0);
                }
            }
        }

        for(i = 0; i < ROW; i++){
            for(j = 0; j < COLUMN; j++){
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
        long start;
        long end;
        Node starterNode;
        Node finalNode;
        car_count = 0;
        ArrayList<Car> cars;

        System.out.println("Enter the name of the input file:");

        BufferedReader reader = null;
        Scanner input = new Scanner(System.in);
        String filename = input.next();
        String path = "input/" + filename + ".txt";

        File file = new File(path);
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        } catch (FileNotFoundException f){
            System.out.println("File is nonexistent.");
            System.exit(0);
        }

        String inputLine;
        String[] inputWhole = new String[MAX_CARS];
        try {
            while((inputLine = reader.readLine()) != null){
                inputWhole[car_count] = inputLine;
                car_count++;
            }
        } catch(IOException io){
            System.out.println("IOException");
            System.exit(0);
        }

        cars = createArray(inputWhole);
        printMap(cars);

        if((cars.get(0).getOrientation() == 'h' && cars.get(0).getX() == COLUMN - cars.get(0).getSize() - 1) ||
                (cars.get(0).getOrientation() == 'v' && cars.get(0).getY() == 0)){
            System.out.println("Given input is already a solution");
            System.exit(0);
        }

        System.out.println("Iterative Deepening Depth First Search:");

        starterNode = new Node(0, null, null, cars);

        start = System.currentTimeMillis();
        finalNode = IDDFS(starterNode);
        end = System.currentTimeMillis() - start;

        if(finalNode != null){
            System.out.println("Number of steps to final position: " + finalNode.getDepth());
            System.out.println("Number of nodes processed / visited: " + processedCount);
            System.out.println("Time spent searching: " + end + " msec = " + (float)end/1000 + " sec");
            printPath(finalNode);
        }
        else{
            System.out.println("Solution was not found or does not exist");
            System.out.println("Number of nodes processed (visited): " + processedCount);
            System.out.println("Time spent searching: " + end + " msec = " + (float)end/1000 + " sec");
        }
    }
}
