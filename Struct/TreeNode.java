package Struct;

import java.io.Serializable;

public class TreeNode implements Serializable{
    //ATTRIBUTES
    private long LeftChild;
    private long RightChild;
    private int value;
    
    //CONSTRUCTORS
    public TreeNode(int value)
    {
        this.value = value;
        LeftChild = -1;
        RightChild = -1;
    }
    
    public TreeNode(int value, long LeftChild, long RightChild)
    {
        this.value = value;
        this.LeftChild = LeftChild;
        this.RightChild = RightChild;
    }

    
    public void setLeftChild(long LeftChild) {
        this.LeftChild = LeftChild;
    }

    public void setRightChild(long RightChild) {
        this.RightChild = RightChild;
    }
    
    public long LeftChild() {
        return LeftChild;
    }

    public long RightChild() {
        return RightChild;
    }

    public int Value() {
        return value;
    }
    
    
}
