package cz.zcu.kiv.sehr.archetypes;

import org.bson.Document;
import org.openehr.am.archetype.Archetype;
import org.openehr.am.archetype.constraintmodel.CObject;
import se.acode.openehr.parser.ADLParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by ghessova on 18.12.16.
 */
public class ArchetypeParser {

    public Archetype parseFile(InputStream stream) {

        try {

            return  new ADLParser(stream).parse();
        }
        catch ( Exception e ) {

            System.out.println( "Couldn't parse an archetype to objects." );
            return null;
        }
    }

    /**
     * saves values from PathNodeMap to ArrayListu and builds tree
     * @param archetype archetype objects (AOM file)
     * @param tree instance of tree
     */
    public void buildTree( Archetype archetype, ArchetypeTree tree ) {
        Object[] mapKeys = archetype.getPathNodeMap().keySet().toArray();       // array of PathNodeMap keys to access their values
        ArrayList<CObject> mapValues = new ArrayList();                         // list of values from PathNodeMap

        // saves object to arraylist
        for ( int i = 0; i < archetype.getPathNodeMap().size(); i++ ) {
            String key =  mapKeys[i].toString();
            mapValues.add( (CObject)archetype.getPathNodeMap().get(key));
        }

        pushRoot( tree, mapValues );
        pushRootsChildren( tree, mapValues );
        pushChildren( tree, mapValues );
        pushTypes( tree, mapValues );
    }

    /**
     * deletes unimportant objects and adds root to tree
     * @param tree instance of tree
     * @param mapValues ArrayList of values from PathNodeMap
     */
    public  void pushRoot( ArchetypeTree tree, ArrayList<CObject> mapValues ) {

        for ( int i = 0; i < mapValues.size(); i++ ) {
            CObject archetypeObject = mapValues.get(i);
            String nodeId = archetypeObject.getNodeId();
            String parentNodePath =  archetypeObject.getParent() == null ? "" : archetypeObject.getParent().parentNodePath();
            if ( nodeId == null  && ( !archetypeObject.getRmTypeName().contains( "DV_" )))
            {
                mapValues.remove(i ); //remove unimportant object
                i--;
            }
            else if ( archetypeObject.isRoot())
            {
                tree.push( nodeId, archetypeObject.isRequired(), getOccurencesInterval(archetypeObject),parentNodePath );
                mapValues.remove(i );
                i--;
            }
        }
    }

    /**
     * inserts root's children to tree
     * @param tree instance of tree
     * @param mapValues ArrayList of values from PathNodeMap
     */
    public  void pushRootsChildren( ArchetypeTree tree, ArrayList<CObject> mapValues ) {

        for ( int i = 0; i < mapValues.size(); i++ ) {
            CObject archetypeObject = mapValues.get(i);
            String nodeId = archetypeObject.getNodeId();
            String parentNodePath =  archetypeObject.getParent() == null ? "" : archetypeObject.getParent().parentNodePath();

            if ("".equals(parentNodePath) && !archetypeObject.getRmTypeName().contains( "DV_" )) {
                tree.push( nodeId, archetypeObject.isRequired(), getOccurencesInterval(archetypeObject), parentNodePath);
                mapValues.remove( i );
                i--;
            }
        }
    }

    /**
     * inserts rest of the children
     * @param tree instance of tree
     * @param mapValues ArrayList of values from PathNodeMap
     */
    public  void pushChildren( ArchetypeTree tree, ArrayList<CObject> mapValues ) {

        boolean pushingDone,
                repeat = true;  // indicates if pushing object was successful
        while ( repeat ) {  // indicates if while should repeat (when at least one pushing failed)
            repeat = false;
            for ( int i = 0; i < mapValues.size(); i++ ) {
                CObject archetypeObject = mapValues.get(i);
                String nodeId = archetypeObject.getNodeId();
                String parentNodePath =  archetypeObject.getParent() == null ? "" : archetypeObject.getParent().parentNodePath();

                if ( !archetypeObject.getRmTypeName().contains( "DV_" )) { //field with a type

                    pushingDone = tree.push(nodeId, archetypeObject.isRequired(), getOccurencesInterval(archetypeObject), parentNodePath);
                    if ( pushingDone ) {
                        mapValues.remove(i);
                        i--;
                    }
                    else {

                        repeat = true;
                    }
                }
            }
        }
    }

    /**
     * adds data type to tree's node
     * @param tree instance of tree
     * @param mapValues ArrayList of values from PathNodeMap
     */
    public  void pushTypes( ArchetypeTree tree, ArrayList<CObject> mapValues ) {

        for ( int i = 0; i < mapValues.size(); i++ ) {
            CObject archetypeObject = mapValues.get(i);
            String nodeId = archetypeObject.getNodeId();
            String parentNodePath =  archetypeObject.getParent() == null ? "" : archetypeObject.getParent().parentNodePath();
            tree.push(archetypeObject.getRmTypeName(), parentNodePath);
            mapValues.remove(i);
            i--;
        }
    }

    public String getOccurencesInterval(CObject object ) {

        String occurrences;

        if ( object.getOccurrences().getLower() != null ) {

            if ( object.getOccurrences().isLowerIncluded()) {

                occurrences = "<";
            }
            else {

                occurrences = "(";
            }

            occurrences = occurrences + Integer.toString(( object.getOccurrences().getLower())) + ";";
        }
        else {

            occurrences = "(-inf;";
        }

        if ( object.getOccurrences().getUpper() != null ) {

            occurrences = occurrences + Integer.toString(( object.getOccurrences().getUpper()));
            if ( object.getOccurrences().isUpperIncluded()) {

                occurrences = occurrences + ">";
            }
            else {

                occurrences = occurrences + ")";
            }
        }
        else {

            occurrences = occurrences + "inf)";
        }

        return occurrences;
    }

    public Document processArchetypeInputStream(InputStream stream) {

        Archetype archetype = parseFile(stream);
        /*PrintWriter pw = null;
        try {
            pw = new PrintWriter( new File("archetype.json") );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }*/
        if (archetype.getOntology() == null) {
            System.out.println("dffsdfsdfsdfsdfs");
        }
        else {
            System.out.println("aaaaaaaaaaaaaaaaaa");
        }
        ArchetypeTree tree = new ArchetypeTree(archetype.getOntology());
        buildTree( archetype, tree );
        Document doc = new Document();
        doc.append("description", archetype.getArchetypeId().rmEntity().toLowerCase());
        doc.append("name", archetype.getArchetypeId().localID());
        doc.append("properties", tree.root.getDocument(archetype.getOntology()));
        /*pw.println( "{" );
        pw.println( "   \"description\":\"" + archetype.getArchetypeId().rmEntity().toLowerCase() + "\"," );
        pw.println( "   \"name\":\"" + archetype.getArchetypeId().localID() + "\"," );
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
        //pw.println( "   \"postDate\":\"" + sdf.format(((File)archetypes.get( i )).lastModified()) + "\"," );
        pw.println( "   \"properties\":{" );
        buildTree( archetype, tree );
        tree.print( pw, -1 );                                                 // -1 beginning of listing, node is set to root
        pw.println( "   }" );
        pw.println( "}" );
        pw.close();*/
        return doc;
    }


}
