import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
public class customerService<T> {
	public static int[] q; //q stands for queue
	public static void main(String args[]){
		Queue<?> customerList = readCustomer("customersfile.txt");
		q = new int[customerList.size];
		while(!customerList.isEmpty()){
			customerList.dequeue();
		}

		readQuery(customerList, "queriesfile.txt");
	}

	private static Queue<?> readCustomer(String arg){
		Queue<?> customerList = new Queue<Object>();
		try {
			String input;
			BufferedReader br = new BufferedReader(new FileReader(arg));
			input = br.readLine();
			Node.serviceTime = Integer.parseInt(input);
			while((input=br.readLine()) != null){
				int id;
				String arrivalTime;
				input = br.readLine();
				String strArray[] = input.split("\\s+");//split based on whitespace
				id = Integer.parseInt(strArray[1]);//split into array, and read the second one
				
				input = br.readLine();
				String strArray2[] = input.split("\\s+");//split based on whitespace
				arrivalTime = strArray2[1];//split into array, and read the second one
				
				customerList.enqueue(id, arrivalTime);
			}
			br.close();
		}
		catch(Exception e){
			System.out.println("Failed to read the file" + arg);		
		}
		return customerList;
	}
	private static void readQuery(Queue<?> customerList, String arg) {
		try {
			String input;
			BufferedReader br = new BufferedReader(new FileReader(arg));
			FileWriter fw = new FileWriter("output.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			while( (input = br.readLine()) != null){
				if (input.equalsIgnoreCase("NUMBER-OF-CUSTOMERS-SERVED")){
					bw.write("NUMBER-OF-CUSTOMERS-SERVED:"+customerList.served);
					bw.newLine();
				}else if (input.equalsIgnoreCase("LONGEST-BREAK-LENGTH")) {
					bw.write("LONGEST-BREAK-LENGTH:"+customerList.maxBreak);
					bw.newLine();
				}else if (input.equalsIgnoreCase("TOTAL-IDLE-TIME")) {
					bw.write("TOTAL-IDLE-TIME:"+customerList.idleTime);
					bw.newLine();
				}else if (input.equalsIgnoreCase("MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME")) {
					bw.write("MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME:"+customerList.maxPeople);
					bw.newLine();
				}else{
					if(input.matches("WAITING-TIME-OF \\d*")){ //regular expression to match id
						int id = Integer.parseInt(input.replaceAll("\\D+",""));//extract the id from string
						bw.write(input+":"+q[id-1]);//track the customer
						bw.newLine();
					}
				}
			}
			bw.close();
			br.close();
		}
		catch(Exception e){
			System.out.println("Failed to read the file" + arg);	
		}
	}
}
