public class Queue <T> {
    Node <T> first,last;
    int size = 0,served = 0,maxPeople = 0,idleTime = 0,maxBreak = 0;
    public Queue() {
        this.first = this.last = null;
    }
    boolean isEmpty() {
        return first == null;
    }
    void enqueue(int id, String arrivalTime) {
        size++;
        Node <T> temp = new Node <T> (id, arrivalTime);
        if (this.last == null) {
            this.first = this.last = temp;
            return;
        }
        this.last.next = temp;
        this.last = temp;
    }
    Node <T> dequeue() {
        // If queue is empty, return Null.
        if (first == null) {return null;}
        served++;
        size--;
        Node <T> temp = first;
        customerService.q[temp.id - 1] = temp.waitTime;
        first = first.next;
        if (first == null) { // Node.serviceTime is the constant T in the first line 
            if (temp.time - Node.serviceTime < 61200) {
                if (61200 - temp.time - Node.serviceTime > maxBreak){//compare 
                    maxBreak = 61200 - temp.time - Node.serviceTime;// store the largest
                }
                idleTime += 61200 - temp.time - Node.serviceTime;
            }
        } else {
            if (first.time < temp.time + Node.serviceTime) {
                first.waitTime += temp.time + Node.serviceTime - first.time;
                first.time      = temp.time + Node.serviceTime;
            } else {
                if (first.time - temp.time - Node.serviceTime > maxBreak){ //compare 
                    maxBreak = first.time - temp.time - Node.serviceTime;// store the largest
                }
                idleTime += first.time - temp.time - Node.serviceTime;
            }
        }
        if (first.time > 61200) { // 17 in 24hours is 5pm 
           first.waitTime = 61200 - first.time;//17 * 3600 = 61200
           if (first.waitTime < 0) {first.waitTime = 0;}
           customerService.q[first.id - 1] = first.waitTime;
           while (first.next != null) {
               first.waitTime = 61200 - first.time;
               customerService.q[first.id - 1] = first.waitTime;
               first = first.next;
           }
           if(first.next == null){
               first = null;
               last = null;
               size = 0;
               return null;
           }
        }

        int peopleInLine = 0;
        Node<T> line = first.next; // move to the next line
        while (line != null && line.time < 61200 && (line.time - first.time) < Node.serviceTime) {
            peopleInLine++;
            line = line.next;
            if (peopleInLine > maxPeople) {//update the number if more than one person shows up before 9 am
                maxPeople = peopleInLine;
            }
        }
        return temp;
    }
}
