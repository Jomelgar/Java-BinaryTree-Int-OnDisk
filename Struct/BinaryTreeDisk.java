package Struct;
import Struct.TreeNode;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Queue;


public class BinaryTreeDisk {
    RandomAccessFile raf;
    
    public BinaryTreeDisk(String Filename)
    {
        //Create File
        try
        {
            //Only verifies if it is created correctly
            raf = new RandomAccessFile(Filename,"rw");
            if(raf.length() > 0)return;
            raf.writeLong(-1);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    public boolean add(int value)
    {
        try
        {
            raf.seek(0);
            long RootOffset = raf.readLong();
            if(RootOffset == -1)
            {
                raf.seek(0);
                raf.writeLong(raf.length());
            }
            return RecursiveAdd(new TreeNode(value),RootOffset);
        }catch(Exception e)
        {
            return false;
        }
    }
    
    private TreeNode read(long offset)
    {
        try
        {
            raf.seek(offset);
            int i = raf.readInt();
            long left = raf.readLong();
            long right = raf.readLong();
            return new TreeNode(i,left,right);
        }catch(Exception e)
        {
            return null;
        } 
    } 
    
    private boolean RecursiveAdd(TreeNode node, long offset)
    {
        try
        {
            if(offset == -1)
            {
                raf.seek(raf.length());
                raf.writeInt(node.Value());
                raf.writeLong(node.LeftChild());
                raf.writeLong(node.RightChild());
                return true;
            }
            
            TreeNode parent = read(offset);
            if(parent.Value() > (node.Value()))
            {
                //Left
                if(parent.LeftChild() == -1)
               {
                   raf.seek(offset);
                    raf.skipBytes(4);
                   raf.writeLong(raf.length());
                   return RecursiveAdd(node,-1);
               }
                return RecursiveAdd(node,parent.LeftChild());
            }else if(parent.Value() < (node.Value()))
            {
               //Right
               if(parent.RightChild() == -1)
               {
                   raf.seek(offset);
                   raf.skipBytes(12);
                   raf.writeLong(raf.length());
                   return RecursiveAdd(node,-1);
               }
               return RecursiveAdd(node,parent.RightChild());
            }else
            {
                //Is already in the struct
                return true;
            }
        }catch(Exception e)
        {
            return false;
        }
    }
    
    public String HierarchyList()
    {
        try
        {
            String text = "";
            Queue<TreeNode> queue = new LinkedList<TreeNode>();
            raf.seek(0);
            long root = raf.readLong();
            queue.add(read(root));
            while(!queue.isEmpty())
            {
                int count = queue.size();
                for (int i = 0; i < count; i++) 
                {
                    TreeNode node = queue.poll();
                    if(node.LeftChild() != -1)
                    {
                        queue.add(read(node.LeftChild()));
                    }
                    if(node.RightChild() != -1)
                    {
                        queue.add(read(node.RightChild()));
                    }
                    
                    text+= node.Value() + " ";
                }
                if(!queue.isEmpty())text+= "\n";
            }
            return text;
        }catch(Exception e)
        {
            return null;
        }
    }
}
