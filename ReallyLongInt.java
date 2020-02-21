// CS 0445 Spring 2020
// This is a partial implementation of the ReallyLongInt class.  You need to
// complete the implementations of the remaining methods.  Also, for this class
// to work, you must complete the implementation of the LinkedListPlus class.
// See additional comments below.

import java.util.ArrayList;

public class ReallyLongInt 	extends LinkedListPlus<Integer>
        implements Comparable<ReallyLongInt> {
    private ReallyLongInt() {
        super();
    }

    // Data is stored with the LEAST significant digit first in the list.  This is
    // done by adding all digits at the front of the list, which reverses the order
    // of the original string.  Note that because the list is doubly-linked and
    // circular, we could have just as easily put the most significant digit first.
    // You will find that for some operations you will want to access the number
    // from least significant to most significant, while in others you will want it
    // the other way around.  A doubly-linked list makes this access fairly
    // straightforward in either direction.
    public ReallyLongInt(String s) {
        super();
        char c;
        int digit = -1;
        // Iterate through the String, getting each character and converting it into
        // an int.  Then make an Integer and add at the front of the list.  Note that
        // the add() method (from A2LList) does not need to traverse the list since
        // it is adding in position 1.  Note also the the author's linked list
        // uses index 1 for the front of the list.
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (('0' <= c) && (c <= '9')) {
                digit = c - '0';
                // Do not add leading 0s
                if (!(digit == 0 && this.getLength() == 0))
                    this.add(1, new Integer(digit));
            } else throw new NumberFormatException("Illegal digit " + c);
        }
        // If number is all 0s, add a single 0 to represent it
        if (digit == 0 && this.getLength() == 0)
            this.add(1, new Integer(digit));
    }

    public ReallyLongInt(ReallyLongInt rightOp) {
        super(rightOp);
    }

    // Method to put digits of number into a String.  Note that toString()
    // has already been written for LinkedListPlus, but you need to
    // override it to show the numbers in the way they should appear.
    public String toString() {

        StringBuilder sb = new StringBuilder();

        Node temp = firstNode.prev;

        for (int i = numberOfEntries; i > 0; i--) {

            sb.append(temp.getData());

            temp = temp.prev;

        }

        return sb.toString();

    }

    // See notes in the Assignment sheet for the methods below.  Be sure to
    // handle the (many) special cases.  Some of these are demonstrated in the
    // RLITest.java program.

    // Return new ReallyLongInt which is sum of current and argument
    public ReallyLongInt add(ReallyLongInt rightOp) {

        Node temp = firstNode.prev;
        Node tempnew = rightOp.firstNode.prev;
        ReallyLongInt comp = new ReallyLongInt();

        int remainder = 0;
        int numOps;

        if (rightOp.getLength() > numberOfEntries) {
            numOps = rightOp.getLength();
        } else {
            numOps = numberOfEntries;
        }

        int composite;

        for (int i = 1; i < numOps + 1; i++) {

            if (i > numberOfEntries) {
                composite = tempnew.getData() + remainder;
            } else if (i > rightOp.getLength()) {
                composite = temp.getData() + remainder;
            } else {
                composite = temp.getData() + tempnew.getData() + remainder;
            }

            if (composite > 10) {
                remainder = 1;
                composite = composite - 10;
            } else if (composite == 10) {
                remainder = 1;
                composite = 0;
            } else {
                remainder = 0;
            }

            comp.add(composite);

            temp = temp.prev;
            tempnew = tempnew.prev;

        }

        if (remainder > 0) {

            comp.add(remainder);
        }

        return new ReallyLongInt(comp);

    }

    // Return new ReallyLongInt which is difference of current and argument
    public ReallyLongInt subtract(ReallyLongInt rightOp) {

        Node temp = firstNode.prev;
        Node tempnew = rightOp.firstNode.prev;

        ReallyLongInt comp = new ReallyLongInt();

        if (firstNode.getData() - rightOp.firstNode.getData() == 1 && firstNode.prev.getData() - rightOp.firstNode.prev.getData() == 0) {

            temp = firstNode;
            tempnew = rightOp.firstNode;

        }


        int remainder = 0;
        int numOps;
        int tempNewValue;

        boolean less = false;

        if (numberOfEntries == rightOp.getLength()) {
            numOps = numberOfEntries + 1;
        } else {
        numOps = numberOfEntries;
        }


        int composite;

        for (int i = 1; i < numOps; i++) {

            if (temp.prev.getData().equals(0)) {
                less = true;
                if (i >rightOp.getLength()) {tempNewValue = 0;}
                else {tempNewValue = tempnew.getData();}
                composite = 10 - tempNewValue - remainder;
            } else {
                composite = temp.getData() - tempnew.getData() - remainder;
            }

            if (composite < 0) {
                remainder = 1;
                if (composite < -10) {
                    composite = composite + 10;
                }
                else {
                    composite = composite + 11;
                }
            } else if (composite == 0) {
                remainder = 0;
            } else if (composite == 10) {
                composite = 0;
                remainder = 1;
            } else if (composite == 9) {
                remainder = 1;
            } else {
                remainder = 0;
            }

            comp.add(composite);

            temp = temp.prev;
            tempnew = tempnew.prev;

        }

        if (remainder > 0) {

            comp.add(remainder);
        }

        if (less) {
            comp.remove(numberOfEntries);
        }

        return new ReallyLongInt(comp);


    }

    // Return -1 if current ReallyLongInt is less than rOp
    // Return 0 if current ReallyLongInt is equal to rOp
    // Return 1 if current ReallyLongInt is greater than rOp
    public int compareTo(ReallyLongInt rOp) {
        Node temp = rOp.firstNode;

        if (numberOfEntries < rOp.getLength()) {
            return -1;
        } else if (numberOfEntries > rOp.getLength()) {
            return 1;
        } else {
            if (firstNode.prev.getData() > temp.prev.getData()) {
                return 1;
            } else if (firstNode.prev.getData() < temp.prev.getData()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    // Is current ReallyLongInt equal to rightOp?
    public boolean equals(Object rightOp) {

        ReallyLongInt temp = (ReallyLongInt) rightOp;

        if (numberOfEntries < temp.getLength()) {
            return false;
        } else if (numberOfEntries > temp.getLength()) {
            return false;
        } else {
            if (firstNode.prev.getData().equals(temp.firstNode.prev.getData())) {
                return true;
            } else {
                return false;
            }
        }
    }

    // Mult. current ReallyLongInt by 10^num
    public void multTenToThe(int num)
    {
        for (int i = 0; i < num; i++) {
            add(1, 0);
        }
    }

    // Divide current ReallyLongInt by 10^num
    public void divTenToThe(int num)
    {
        if ( num < numberOfEntries ) {
            for (int i = 0; i < num; i++) {
                remove(1);
            }
        }
        else {
            for (int i = 0; i < numberOfEntries + 1; i++) {
                replace(i, 0);
            }
        }
    }
}