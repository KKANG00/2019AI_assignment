package aipackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//node that contains state information
class Node{
	String state = "";
	
	//constructor
	Node(String answer) {
		this.state = answer;
	}
	
	//get state information from node
	String getstate() {
		return this.state;
	}
	
	//get the length of the state
	int ith() {
		return this.state.length();
	}
}

public class nqueens {

	//n value
	static int input;
	
	//goal test
	static boolean isvalid(int input, String answer) {
		//if queens are not filled all yet it is invalid
		if(answer.length()<input) return false;
		
		//check if queens are attackable or not for any of 3 side
		for(int i=0;i<input;i++) {
			for(int j=i+1;j<input;j++) {
				//row check, diagonal check
				if(answer.charAt(i)==answer.charAt(j) || Math.abs(answer.charAt(i)-answer.charAt(j))==Math.abs(i-j))
					return false;
			}
		}
		
		//if nothing is problem then it is valid
		return true;
	}
	
	//convert to string form
	static String convert(String answer) {
		String result="";
		
		for(int i=0;i<answer.length();i++)
			result += answer.charAt(i)+" ";
		
		return result;
	}
	
	//breadth-first search
	static String bfs(int input, Node node) {
		
		Queue<Node> queue = new LinkedList<Node>();
		
		queue.offer(node);
		
		//ith column
		int ith = 0;
				
		//searching until queue is empty
		while(!queue.isEmpty()) {
			
			//poll the first element in the queue
			node = queue.remove();
			
			//current state
			String state = node.getstate();
			ith = node.ith();
					
			//if polled element is valid, return the answer
			if(isvalid(input, state)) {
				return "Location : "+convert(state);
			}
					
			//if popped element is not valid, expanding and add to queue
			else if(ith<input) {
				for(int i=0;i<input;i++) {
					Node newnode = new Node(state+Integer.toString(i));
					queue.offer(newnode);					
				}
			}
		}
		
		
		return "No Solution";
	}
	
	//depth-first search
	static String dfs(int depth, int input, Node node) {
		
		Stack<Node> stack = new Stack<Node>();
		
		stack.push(node);
		
		//ith column
		int ith = 0;
		
		//searching until stack is empty
		while(!stack.isEmpty()) {
			
			//pop the first element in the stack
			node = stack.peek();
			
			//current state
			String state = node.getstate();
			stack.pop();
			
			ith = node.ith();
			//System.out.println("queue size== "+stack.size());
			
			//if popped element is valid, return the answer
			if(isvalid(input, state)) {
				return "Location : "+convert(state);
				
			}
			
			//if popped element is not valid, expanding and push to stack
			else if(ith<depth) {
				
				for(int i=0;i<input;i++) {					
					Node newnode = new Node(state+Integer.toString(i));
					stack.push(newnode);					
				}
			}
		}
		
		return "No Solution";
		
	}
	
	public static void main(String[] args) throws IOException {
		
		input = Integer.parseInt(args[0]); String dfid ="";
		
		//root node
		Node node = new Node("");
		
		//doing dfs and time check
		double bt1 = System.currentTimeMillis();
		String dfs = dfs(input, input, node);
		double at1 = System.currentTimeMillis();
		double t1 = (at1 - bt1)/(double)1000.0;
		String dfstime = "Time : "+t1;
		
		//doing bfs and time check
		double bt2 = System.currentTimeMillis();
		String bfs = bfs(input, node);
		double at2 = System.currentTimeMillis();
		double t2 = (at2 - bt2)/(double)1000.0;
		String bfstime = "Time : "+t2;
	
		//doing dfid and time check
		//dfid is implemented by doing iterative dfs until the depth from 0 to input
		double bt3 = System.currentTimeMillis();
		for(int i=0;i<=input;i++) {
			dfid = dfs(i, input, node);
			if(!dfid.equals("No Solution")) break;
		}
		double at3 = System.currentTimeMillis();
		double t3 = (at3 - bt3)/(double)1000.0;
		String dfidtime = "Time : "+t3;
		
		//set path and filename and write to the text file
		String path = args[1];
		String filename = "result"+input+".txt";

		BufferedWriter file;
		file = new BufferedWriter(new FileWriter(new File(path, filename)));
		
		try {
			
			file.write(">DFS");
			file.newLine();
			file.write(dfs);
			file.newLine();
			file.write(dfstime);
			file.newLine();
			file.newLine();

			file.write(">BFS");
			file.newLine();
			file.write(bfs);
			file.newLine();
			file.write(bfstime);
			file.newLine();
			file.newLine();

			file.write(">DFID");
			file.newLine();
			file.write(dfid);
			file.newLine();
			file.write(dfidtime);
			
			file.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}