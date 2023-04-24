package Blocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Blocks {

    public static int[] pos;
    public static Stack<Integer>[] blocks;
    public static Stack<Integer> temp;
    
    public static void main(String[] args) throws FileNotFoundException {

        /*Scanner stdin = new Scanner(System.in);
		int numOfBlocks = stdin.nextInt();
		stdin.nextLine();
		String input = " ";
		input = stdin.nextLine();
		System.out.println(input);
    */

	    // Scanner stdin = new Scanner(System.in);
        Scanner stdin = new Scanner(new File("blocks3.txt"));
	    int numOfBlocks = stdin.nextInt();
	    stdin.nextLine();
	    
        //Stack<Integer>[] blocks = new Stack[numOfBlocks];
        blocks = new Stack[numOfBlocks];
       	temp = new Stack<>();
        pos = new int[numOfBlocks];
    	

        String command = null, keyword = null;
    	int num1 = 0, num2 = 0;

    	// Initializing initial positions of position array and block location
	    for(int i =0; i<blocks.length; i++){
		    blocks[i] = new Stack<>();
		    blocks[i].push(i);
		    pos[i] = i;
	    }
        
	
	    while(!stdin.hasNext("quit")){
		    String s = stdin.nextLine();
		    String[] splinter = s.split(" ", 0);
		    command = splinter[0].trim();
		    keyword = splinter[2].trim();
		    num1 = Integer.parseInt(splinter[1]);
		    num2 = Integer.parseInt(splinter[3]);

		    if(command.equals("move")){
                    if(keyword.equals("onto"))
			    moveOnto(num1, num2);
                    else
			    moveOver(num1, num2);
		    }
		    else if(command.equals("pile")){
                    if(keyword.equals("onto"))
			    pileOnto(num1, num2);
                    else
			    pileOver(num1, num2);
		    }
		    else 
                throw new Error("You messed up");
            }
            
            printBlocks();
        
            System.exit(0);
            
	} // End of Main

	public static void moveOnto(int num1, int num2){
        // First return all the blocks ontop of a and b
        // Cycling through array after index of num1
        if(pos[num1] != pos[num2]){
            while(blocks[pos[num1]].peek() != num1){
                moveBack(blocks[pos[num1]].pop());
            }
        }
        // Cycling through array after index of num2
        if(pos[num1] != pos[num2]){
        while(blocks[pos[num2]].peek() != num2){
            moveBack(blocks[pos[num2]].pop());
        }
        }
        // Pushing num1 onto num2 stack, removing it from the original stack 
        // and setting position to block 2 stack
        blocks[pos[num2]].push(blocks[pos[num1]].pop());
        pos[num1] = pos[num2];
    
}

// Look at line 3 of the data file for this is moving block 7 back
// when it should not
public static void moveOver(int num1, int num2){
    // First return all block ontop of a;
    // Cycling through array after index of num1
    if(pos[num1] != pos[num2]){
        while(blocks[pos[num1]].peek() != num1){
            moveBack(blocks[pos[num1]].pop());
        }
    }
    // Pushing block a onto stack b and updating position of element a
    blocks[pos[num2]].push(blocks[pos[num1]].pop());
    pos[num1] = pos[num2];
    
}

public static void pileOnto(int num1, int num2){
    
    // Pushing every element ontop of the stack until we reach a
    //if(pos[num1] != pos[num2]){
        while(blocks[pos[num1]].peek() != num1){
            temp.push(blocks[pos[num1]].pop());
        }
    //}
    // Pushing element a
    temp.push(blocks[pos[num1]].pop());
    
    // Returning all blocks on top of B
    if(pos[num1] != pos[num2])
        while(blocks[pos[num2]].peek() != num2){
            moveBack(blocks[pos[num2]].pop());
    }
    
    // Adding temp stack to b
    while(!temp.isEmpty()){
        pos[temp.peek()] = pos[num2];
        blocks[pos[num2]].push(temp.pop());
    }
    
}

public static void pileOver(int num1, int num2){
    // Pushing every element ontop of the stack until we reach a
    if(pos[num1] != pos[num2]){
    while(blocks[pos[num1]].peek() != num1){
        temp.push(blocks[pos[num1]].pop());
        }
    }
    // Pushing element a on the temp stack
    temp.push(blocks[pos[num1]].pop());
    
    // Adding temp stack to b
    while(temp.size() > 0){
        pos[temp.peek()] = pos[num2];
        blocks[pos[num2]].push(temp.pop());
    }
    
}


// Method to call to move numbers back to original block location
public static void moveBack(int num){
    //pos[num] = num;
    // System.out.println("blocks size " + blocks[num].size());
    blocks[num].push(num);
    pos[num] = num;
}

public static void printBlocks(){
    // Pushing them onto temp then printing
    for(int i=0; i<blocks.length; i++){
        System.out.print(i + ":");
        
        while(!blocks[i].isEmpty()){
            temp.push(blocks[i].pop());
        }

        while(!temp.isEmpty()){
            System.out.print(" " + temp.pop());
        }

        System.out.println();
    }



}

} // End of class Blocks