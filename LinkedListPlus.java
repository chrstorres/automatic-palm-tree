// CS 0445 Spring 2020
// LinkedListPlus<T> class partial implementation

// See the commented methods below.  You must complete this class by
// filling in the method bodies for the remaining methods.  Note that you
// may NOT add any new instance variables, but you may use method variables
// as needed.

public class LinkedListPlus<T> extends A2LList<T> {
    // Default constructor simply calls super()
    public LinkedListPlus() {
        super();
    }

    public LinkedListPlus(LinkedListPlus<T> oldList) {
        super();
        if (oldList.getLength() > 0) {

            Node temp = oldList.firstNode;
            Node newNode = new Node(temp.data);
            firstNode = newNode;


            Node currNode = firstNode;
            temp = temp.next;
            int count = 1;
            while (count < oldList.getLength()) {
                newNode = new Node(temp.data);
                currNode.next = newNode;
                newNode.prev = currNode;
                temp = temp.next;
                currNode = currNode.next;
                count++;
            }
            currNode.next = firstNode;  // currNode is now at the end of the list.
            firstNode.prev = currNode;    // link to make the list circular
            numberOfEntries = oldList.numberOfEntries;
        }
    }


    public String toString() {
        StringBuilder b = new StringBuilder();
        Node curr = firstNode;
        int i = 0;
        while (i < this.getLength()) {
            b.append(curr.data.toString());
            b.append(" ");
            curr = curr.next;
            i++;
        }
        return b.toString();
    }

    public void leftShift(int num) {

        if (num > numberOfEntries) {
            clear();
        } else {
            Node temp = firstNode;

            int count = 0;

            for (int i = 0; i < num; i++) {

                temp = temp.next;

                remove(i);

                count = i;

            }

            remove(count);

        }
    }

    public void rightShift(int num) {

        if (num > numberOfEntries) {
            clear();
        } else {

            int count = numberOfEntries;

            for (int i = 0; i < num; i++) {

                remove(count);

                count -= 1;
            }

            remove(numberOfEntries - num);

        }
    }

    // Rotate to the left num locations in the list.  No Nodes
    // should be created or destroyed.
    public void leftRotate(int num) {

        if (num < 0 ) {
            num = numberOfEntries + num;
        }

        if (num > numberOfEntries) {
            num = num % numberOfEntries;
        }

        Node temp = firstNode;

        for (int i = 0; i < num; i++) {

            temp = temp.next;

        }

        firstNode = temp;
    }


    public void rightRotate(int num) {

        if (num < 0 ) {
            num = numberOfEntries + num;
        }


        if (num > numberOfEntries) {
            num = num % numberOfEntries;
        }

        Node temp = firstNode;

        for (int i = 0; i < num; i++) {

            temp = temp.prev;

        }

        firstNode = temp;


    }

    // Reverse the nodes in the list.  No Nodes should be created
    // or destroyed.
    public void reverse() {

        Node curr = firstNode.next;
        Node prev = curr.prev;
        Node next;

        for (int tCount = 1; tCount < numberOfEntries; tCount++) {
            next = curr.next;
            curr.next = prev;

            prev = curr;
            curr = next;
        }

        firstNode = prev;
    }
}
