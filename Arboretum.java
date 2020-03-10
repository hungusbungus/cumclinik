import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;

/**
 * A testbed for an augmented implementation of an AVL tree
 * @author William Duncan, YOUR NAME
 * @see AVLTreeAPI, AVLTreeException
 * <pre>
 * Date: December 20, 2019
 * CSC 3102 Programming Project # 2
 * Instructor: Dr. Duncan
 * </pre>
 */
public class Arboretum {
    public static void main(String[] args) throws FileNotFoundException {
        String usage = "ArboretumAnalyzer <data-type> <order-code> <command-file>\n";
        usage += "<data-type>: -s or -S for strings\n";
        usage += "  <order-code>:\n";
        usage += "  -1 for reversed lexicographical order\n";
        usage += "  1 for lexicographical order\n";
        usage += "  -2 for decreasing string length and reversed lexicographical order\n";
        usage += "  2 for increasing string length and lexicographical order\n";
        usage += "<data-type>: -i or -I for integers\n";
        usage += "  <order-code>:\n";
        usage += "  -1 for decreasing numerical order\n";
        usage += "  1 for increasing numerical order\n";
        usage += "<command-file>: name of the command file name\n";
        //complete the implementation of this method

        AVLTree currAVLTree = new AVLTree();

        Scanner uInput = new Scanner(System.in);

        System.out.println(usage);

        Boolean validType = false;
        do {
            System.out.print("Please Enter Your Data Type: ");
            String temp = uInput.next();
            if (temp.equalsIgnoreCase("-i") || temp.equalsIgnoreCase("-s")) {
                currAVLTree.dataType = temp;
                validType = true;
            } else {
                System.out.println("Your Data Type Does Not Exist.");
                System.out.println("Please reference the provided chart.");
                System.out.println("Now Terminating...");
                throw new IllegalArgumentException();
            }
        }
        while (!validType);

        Boolean validCode = false;
        do {
            System.out.print("Please Enter Your Order Code For Your Selected Data Type: ");
            String temp = uInput.next();
            if (temp.equalsIgnoreCase("-2")
                    || temp.equalsIgnoreCase("-1")
                    || temp.equalsIgnoreCase("1")
                    || temp.equalsIgnoreCase("2")) {
                if (currAVLTree.dataType.equals("-i") && (temp.equals("-2") || temp.equals("2"))) {
                    System.out.println("Your Entered Sort Code Does Not Exist.");
                    System.out.println("Please reference the provided chart.");
                    System.out.println("Now Terminating...");
                    throw new IllegalArgumentException();
                } else {
                    currAVLTree.orderCode = temp;
                    validCode = true;
                }
            } else {
                System.out.println("Your Entered Sort Code Does Not Exist.");
                System.out.println("Please reference the provided chart.");
                System.out.println("Now Terminating...");
                throw new IllegalArgumentException();
            }
        }
        while (!validCode);

        String cmdFile;
        Boolean exit = false;

        int counter = 0;
        do {
            cmdFile = uInput.nextLine();
            File tempCMD = new File(cmdFile);
            if (!tempCMD.exists()) {
                if (counter > 0) {
                    System.out.println("Your Command File Does Not Exist.");
                    System.out.println("Now Terminating...");
                    System.exit(0);
                }
                System.out.println("Please enter your cmdFile location:");
                counter++;
                exit = false;
            }
            if (tempCMD.exists()) {
                exit = true;
            }
        }
        while (!exit);
        File mainCMD = new File(cmdFile);

        Scanner cmdScanner = new Scanner(mainCMD);

        ArrayList<String> cmdArray = new ArrayList<>();

        Boolean assignExit = false;

        do {
            while (cmdScanner.hasNextLine()) {
                if (!cmdScanner.hasNextLine()) {
                    assignExit = true;
                }
                cmdArray.add(cmdScanner.nextLine());
            }
        }
        while (assignExit);

        int i = 0;
        
        Boolean whitespaceRemover = false;
        do {
            if (cmdArray.get(i).equalsIgnoreCase("stats") ||
                    cmdArray.get(i).equalsIgnoreCase("sort") ||
                    cmdArray.get(i).equalsIgnoreCase("traverse")) {

                if (cmdArray.get(i).equalsIgnoreCase("stats"))
                    System.out.println("There are no attainable stats for this AVL.");

                if (cmdArray.get(i).equalsIgnoreCase("sort"))
                    System.out.println("There is nothing to sort in this AVL.");

                if (cmdArray.get(i).equalsIgnoreCase("traverse"))
                    System.out.println("There is nothing to traverse through in this AVL.");

                i++;
            }

            if (i == cmdArray.size()) {
                return;
            }

            if (!cmdArray.get(i).equalsIgnoreCase("peek") &&
                    !cmdArray.get(i).equalsIgnoreCase("delete") &&
                    !cmdArray.get(i).equalsIgnoreCase("stats")) {
                whitespaceRemover = true;
            }
        }
        while (!whitespaceRemover);
        
        while (i < cmdArray.size()) {
            String[] checkValues = cmdArray.get(i).split("\\s+");
            if (checkValues[0].equals("insert")) {

                if(currAVLTree.dataType.equalsIgnoreCase("-s")){
                    String length = checkValues[1];
                    System.out.println("Inserted: " + checkValues[1]);
                    currAVLTree.insert(length);
                    i++;
                }

                if (currAVLTree.dataType.equalsIgnoreCase("-i")) {
                    Integer number = Integer.valueOf(checkValues[1]);
                    System.out.println("Inserted: " + number);
                    currAVLTree.insert(number);
                    i++;
                }
            }
            if (checkValues[0].equals("sort")){
                currAVLTree.entries.clear();;
                ArrayList<AVLTree> sortedTree = currAVLTree.sort();

                if(sortedTree.size() == 0){
                    System.out.println("This AVL is empty.");
                }
                else{
                    System.out.println("Sorted List of Tree Items:");
                    System.out.print("[");
                    for (int j = 0; j < sortedTree.size() - 1; j++) {
                        System.out.print(sortedTree.get(j) + ", ");
                    }
                    System.out.print(sortedTree.get(sortedTree.size() - 1));
                    System.out.println("]");
                }
                i++;
            }
            if (checkValues[0].equals("delete")){
                if(currAVLTree.dataType.equalsIgnoreCase("-s")){
                    String length = checkValues[1];
                    System.out.println("Deleted: " + checkValues[1]);
                    currAVLTree.remove(length);
                    i++;
                }
                if (currAVLTree.dataType.equalsIgnoreCase("-i")) {
                    Integer number = Integer.valueOf(checkValues[1]);
                    System.out.println("Deleted: " + number);
                    currAVLTree.remove(number);
                    i++;
                }

            }
            if (checkValues[0].equals("traverse")){

                currAVLTree.entries.clear();

                if(currAVLTree.dataType.equalsIgnoreCase("-i")) {
                    Function<Object, Integer> func = (Object a) -> {currAVLTree.entries.add(a); return null;};
                    currAVLTree.traverse(func);
                    i++;
                }
                if(currAVLTree.dataType.equalsIgnoreCase("-s")){
                    Function<Object, String> func = (Object a) -> {currAVLTree.entries.add(a); return null;};
                    currAVLTree.traverse(func);
                    i++;
                }
            }
            if (checkValues[0].equals("stats")){
                System.out.println("Stats: size = " + currAVLTree.size() +
                        ", actual-height = " + currAVLTree.height() +
                        ", optimal-height = " + optimalHeightChecker(currAVLTree) +
                        ", #leaves = " + currAVLTree.countLeaves() +
                        ", full? = " + currAVLTree.isFull() +
                        ", perfect? = " + currAVLTree.isPerfect());


                i++;
            }
        }
    }

    public static int optimalHeightChecker (AVLTree tree){
        int b = tree.height();
        boolean exit = false;
        int x = 0;

        if(tree.size() == 0)
            return b = 0;

        if(tree.size() == 1)
            return b = 0;

        while(!exit){
            if(tree.size() <= Math.pow(2,x) + 1 && tree.size() >= Math.pow(2,x - 1)) {
                b = x;
                exit = true;
            }
            else{
                x++;
            }
        }
        return tree.height();
    }
    }
