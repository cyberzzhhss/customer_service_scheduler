public class Node<T>{
	Node<T> next;
	int id,time,waitTime = 0;// time to be served
	static int serviceTime = 0; //time to wrap each gift
	Node(int id, String arrivalTime){
		this.id=id;
		String[] times = arrivalTime.split(":");
		int min = Integer.parseInt(times[1]);
		int sec = Integer.parseInt(times[2]);
		int hour = Integer.parseInt(times[0]);
		if(hour < 8)// assume no one shows up before 8 am
			hour += 12;// the afternoon time is converted to 24 hours
		time = 3600*hour + 60*min + sec;
		if(time < 9*3600){
			waitTime += (9*3600 - time);
			time = 9*3600;
		}
	}
}