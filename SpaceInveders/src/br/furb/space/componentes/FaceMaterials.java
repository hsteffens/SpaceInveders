package br.furb.space.componentes;

//-------------------------  Inner Class  --------------------------------------
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/* FaceMaterials stores the face indicies where a material
is first used. At render time, this information is utilized
to change the rendering material when a given face needs
to be drawn.
 */
public class FaceMaterials {

    private HashMap<Integer, String> faceMats;
    // the face index (integer) where a material is first used
    // for reporting
    private HashMap<String, Integer> matCount;
    // how many times a material (string) is used

    public FaceMaterials() {
        faceMats = new HashMap<Integer, String>();
        matCount = new HashMap<String, Integer>();
    } // end of FaceMaterials()

    public void addUse(int faceIdx, String matName) {
        // store the face index and the material it uses
        if (faceMats.containsKey(faceIdx)) {
            // face index already present
            System.out.println("Face index " + faceIdx + " changed to use material " + matName);
        }
        faceMats.put(faceIdx, matName);

        // store how many times matName has been used by faces
        if (matCount.containsKey(matName)) {
            int i = matCount.get(matName) + 1;
            matCount.put(matName, i);
        } else {
            matCount.put(matName, 1);
        }
    } // end of addUse()

    public String findMaterial(int faceIdx) {
        return faceMats.get(faceIdx);
    }

    public void showUsedMaterials() {
        System.out.println("No. of materials used: " + matCount.size());

        // build an iterator of material names
        Set<String> keys = matCount.keySet();
        Iterator<String> iter = keys.iterator();

        // cycle through the hashmap showing the count for each material
        String matName;
        int count;
        while (iter.hasNext()) {
            matName = iter.next();
            count = matCount.get(matName);

            System.out.print(matName + ": " + count);
            System.out.println();
        }
    } // end of showUsedMaterials()
} // end of FaceMaterials class