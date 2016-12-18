package cz.zcu.kiv.sehr.archetypes;

import java.util.ArrayList;


/**
 * defines a node for building a tree
 * @author Markéta Wolfová
 */
public class ArchetypeNode {
    
    String ID, type, occurrences;
    int rank;   // rank of child
    boolean required;
    ArchetypeNode parent;
    ArrayList<ArchetypeNode> children;
  
    /**
     * constructor creates instance of node
     */
    ArchetypeNode() { }
  
    /**
     * constructor with parameters
     * @param ID ID of object
     * @param required restriction (if object is required when inputting data)
     */
    ArchetypeNode( String ID, boolean required, String occurrences ) {
        this.ID = ID;
        this.required = required;
        this.occurrences = occurrences;
        this.parent = null;
        this.children = new ArrayList();
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(String occurrences) {
        this.occurrences = occurrences;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public ArchetypeNode getParent() {
        return parent;
    }

    public void setParent(ArchetypeNode parent) {
        this.parent = parent;
    }

    public void addChild(ArchetypeNode child) {
        this.children.add(child);
        child.setParent(this);
        child.setRank(this.children.size() - 1);
    }
}