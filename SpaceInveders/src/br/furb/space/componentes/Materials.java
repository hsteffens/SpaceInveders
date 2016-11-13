package br.furb.space.componentes;

//------------------ inner class Materials -----------------------------------
import com.sun.opengl.util.texture.Texture;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.media.opengl.GL;

/* This class does two main tasks:
 * it loads the material details from the MTL file, storing
them as Material objects in the materials ArrayList.
 * it sets up a specified material's colours or textures
to be used when rendering -- see renderWithMaterial()
 */
public class Materials {

    private static final String MODEL_DIR = "";
    private ArrayList<Material> materials;
    // stores the Material objects built from the MTL file data
    // for storing the material currently being used for rendering
    private String renderMatName = null;
    private boolean usingTexture = false;

    public Materials(String mtlFnm) {
        materials = new ArrayList<Material>();

        String mfnm = "data/" + MODEL_DIR + mtlFnm;
        try {
            System.out.println("Loading material from " + mfnm);
            BufferedReader br = new BufferedReader(new FileReader(mfnm));
            readMaterials(br);
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    } // end of Materials()

    private void readMaterials(BufferedReader br) {
        try {
            String line;
            Material currMaterial = null; // current material
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) {
                    continue;
                }
                if (line.startsWith("newmtl ")) {
                    // new material
                    if (currMaterial != null) {
                        // save previous material
                        materials.add(currMaterial);
                    }
                    // start collecting info for new material
                    currMaterial = new Material(line.substring(7));
                } else if (line.startsWith("map_Kd ")) {
                    // texture filename
                    String fileName = MODEL_DIR + line.substring(7);
                    currMaterial.loadTexture(fileName);
                } else if (line.startsWith("Ka ")) {
                    // ambient colour
                    currMaterial.setKa(readTuple3(line));
                } else if (line.startsWith("Kd ")) {
                    // diffuse colour
                    currMaterial.setKd(readTuple3(line));
                } else if (line.startsWith("Ks ")) {
                    // specular colour
                    currMaterial.setKs(readTuple3(line));
                } else if (line.startsWith("Ns ")) {
                    // shininess
                    float val = Float.valueOf(line.substring(3)).floatValue();
                    currMaterial.setNs(val);
                } else if (line.charAt(0) == 'd') {
                    // alpha
                    float val = Float.valueOf(line.substring(2)).floatValue();
                    currMaterial.setD(val);
                } else if (line.startsWith("illum ")) { // illumination model
                    // not implemented
                } else if (line.charAt(0) == '#') {
                    // comment line
                    continue;
                } else {
                    System.out.println("Ignoring MTL line: " + line);
                }
            }
            materials.add(currMaterial);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    } // end of readMaterials()

    private Tuple3 readTuple3(String line) {
        StringTokenizer tokens = new StringTokenizer(line, " ");
        tokens.nextToken(); // skip MTL word
        try {
            float x = Float.parseFloat(tokens.nextToken());
            float y = Float.parseFloat(tokens.nextToken());
            float z = Float.parseFloat(tokens.nextToken());

            return new Tuple3(x, y, z);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        return null; // means an error occurred
    } // end of readTuple3()

    public void showMaterials() {
        System.out.println("No. of materials: " + materials.size());
        Material m;
        for (int i = 0; i < materials.size(); i++) {
            m = materials.get(i);
            m.showMaterial();
            // System.out.println();
        }
    } // end of showMaterials()

    // ----------------- using a material at render time -----------------
    public void renderWithMaterial(String faceMat, GL gl) {
        if (!faceMat.equals(renderMatName)) {
            // is faceMat is a new material?
            renderMatName = faceMat;
            switchOffTex(gl); // switch off any previous texturing
            // set up new rendering material
            Texture tex = getTexture(renderMatName);
            if (tex != null) {
                // use the material's texture
                // System.out.println("Using texture with " + renderMatName);
                switchOnTex(tex, gl);
            } else {
                // use the material's colours
                setMaterialColors(renderMatName, gl);
            }
        }
    } // end of renderWithMaterial()

    public void switchOffTex(GL gl) {
        if (usingTexture) {
            gl.glDisable(GL.GL_TEXTURE_2D);
            usingTexture = false;
            gl.glEnable(GL.GL_LIGHTING);
        }
    } // end of resetMaterials()

    private void switchOnTex(Texture tex, GL gl) {
        gl.glDisable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_TEXTURE_2D);
        usingTexture = true;
        tex.bind();
    } // end of resetMaterials()

    private Texture getTexture(String matName) {
        Material m;
        for (int i = 0; i < materials.size(); i++) {
            m = materials.get(i);
            if (m.hasName(matName)) {
                return m.getTexture();
            }
        }
        return null;
    } // end of getTexture()

    private void setMaterialColors(String matName, GL gl) {
        Material m;
        for (int i = 0; i < materials.size(); i++) {
            m = materials.get(i);
            if (m.hasName(matName)) {
                m.setMaterialColors(gl);
            }
        }
    } // end of setMaterialColors()
}