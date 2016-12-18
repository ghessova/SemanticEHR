package cz.zcu.kiv.sehr.archetypes;

import java.io.PrintWriter;


/**
 * inserts nodes to tree and prints the tree to file
 * @author Markéta Wolfová
 */
public class ArchetypeTree {
    
    ArchetypeNode root = null;
    ArchetypeNode node, nodeParent;
    boolean split;
    int rank;
  
    /**
     * inserts node to tree
     * @param ID ID of object
     * @param required  restriction (if object is required when inputting data)
     * @param parent path to object parent
     * @return true if push was successful, false if not
     */
    public boolean push( String ID, boolean required, String occurrences, String parent ) {
        
        this.node = new ArchetypeNode( ID, required, occurrences );
        if ( this.root == null ) {
            
            this.root = this.node;
        }
        else if ( parent.equals( "" )) {
            if ( !doesNodeExist( ID )) {
                root.addChild(node);
                node.setRank(root.children.size() - 1);
            }
        }
        else if ( !doesNodeExist( ID )) {
            
            findNode( getNodeParent( parent ), -1 );        // -1 is start of search, node is set to root
            if ( this.nodeParent != null ) {
                nodeParent.addChild(node);
                node.setRank(nodeParent.children.size() - 1 );
            }
            else {
                
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * returns true if node of the same ID already exists
     * @param ID nodes ID
     * @return true if node with given ID exists, false if not
     */
    boolean doesNodeExist( String ID ) {
        
        findNode( ID, -1 );
        return this.nodeParent != null;
    }
  
    /**
     * adds data types to nodes of tree
     * @param name name of object and data type of its parent
     * @param parent path to objects parent
     */
    void push( String name, String parent ) {
        
        this.split = false;
        
        findNode( getNodeParent( parent ), -1 );
        if ( this.nodeParent != null ) {
            nodeParent.setType(name);
            if ( this.split ) { // if the data type is an interval, node will get two children - min and max
            
                splitNode();    
            }
        }
    }
  


  
    /**
     * splits node with interval data type into two objects (min and max) and set them as its children
     */
    void splitNode() {
        this.node = new ArchetypeNode( this.nodeParent.ID + ".min", this.nodeParent.required, this.nodeParent.occurrences );
        node.setType(nodeParent.type);
        nodeParent.addChild(node);

        this.node = new ArchetypeNode( this.nodeParent.ID + ".max", this.nodeParent.required, this.nodeParent.occurrences );
        node.setType(nodeParent.type);
        nodeParent.addChild(node);
    }
  
    /**
     * finds objects parent using path
     * @param parent path to parent
     * @return parent's ID
     */
    String getNodeParent( String parent ) {
        
        int start = 0, end = 0;
        for ( int i = parent.length() - 1; i >= 0; i-- ) {
            
            if ( parent.charAt( i ) == ']' ) {
                
                end = i;
            }
            if ( parent.charAt( i ) == '[' ) {
            
                start = i + 1;
                break;
            }
        }
        
        if (( start == 0 ) && ( end == 0 )) {
            
            return this.root.ID;
        }
        
        return parent.substring( start, end );
    }
  
    /**
     * finds node using its ID
     * @param ID ID of object
     * @param i index, rank of child which is next
     *          when method is called -1 signifies root, rest is done automaticaly with recursion 
     */
    void findNode( String ID, int i ) {
        
        if ( i == -1 ) {
            
            this.nodeParent = this.root;
            i = 0;
        }
        
        if ( this.nodeParent.ID.equals( ID )) {
            
            return;
        }
        
        if (( this.nodeParent.children.isEmpty()) || ( i >= this.nodeParent.children.size())) {
            
            this.rank = this.nodeParent.rank;
            this.nodeParent = this.nodeParent.parent;
            if ( this.nodeParent != null ) {
                findNode( ID, this.rank + 1 );
            }
        }
        else {
            
            this.nodeParent = ((ArchetypeNode)this.nodeParent.children.get( i ));
            findNode( ID, 0 );
        }
    }
    
    /**
     * listing of the whole tree
     * @param pw instance PrintWriteru for writing to file
     * @param i index, rank of child which is next
     *          when method is called -1 signifies root, rest is done automaticaly with recursion 
     */
    void print( PrintWriter pw, int i ) {
        
        if ( i == -1 ) {
            
            this.node = this.root;
            i = 0;
        }
        if ( this.node.children.isEmpty()) {
            
            pw.println( "       \"" + this.node.ID + "\":{" );
            if ( this.node.type != null ) pw.println( "           \"type\":\"" + this.node.type + "\"," );
            else pw.println( "           \"type\":\"String\"," );
            pw.println( "           \"isRequired\":\"" + this.node.required + "\"," );
            pw.println( "           \"occurrences\":\"" + this.node.occurrences + "\"" );
      
            this.rank = this.node.rank;
            this.node = this.node.parent;
            if ( this.node == null ) pw.println( "       }" );
            else print( pw, this.rank + 1 );
        }
        else if ( i >= this.node.children.size()) {
            
            pw.println( "       }" );
      
            this.rank = this.node.rank;
            this.node = this.node.parent;
            if ( this.node == null ) pw.println( "       }" );
            else print( pw, this.rank + 1 );   
        }
        else {
            
            if ( i == 0 ) {
                
                pw.println( "       \"" + this.node.ID + "\":{" );
                pw.println( "       \"type\":\"object\"," );
            } 
            else pw.println( "       }," );
            
            this.node = ((ArchetypeNode)this.node.children.get( i ));
            print( pw, 0 );
        }
    }
  
    /**
     * deletes tree and prepares for next archetype
     */
    void clear() {
        
        this.root.children = null;
        this.root = null;
    }
    
}
