package br.furb.space.componentes;

//-------------------- inner Class ---------------------------
/* Faces stores the information for each face of a model.
A face is represented by three arrays of indicies for
the vertices, normals, and tex coords used in that face.
facesVertIdxs, facesTexIdxs, and facesNormIdxs are ArrayLists of
those arrays; one entry for each face.
renderFace() is supplied with a face index, looks up the
associated vertices, normals, and tex coords indicies arrays,
and uses those arrays to access the actual vertices, normals,
and tex coords data for rendering the face.
 */
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.media.opengl.GL;

public class Faces {

    private static final float DUMMY_Z_TC = -5.0F;
    /* indicies for vertices, tex coords, and normals used
    by each face */
    private ArrayList<int[]> facesVertIdxs;
    private ArrayList<int[]> facesTexIdxs;
    private ArrayList<int[]> facesNormIdxs;
    // references to the model's vertices, normals, and tex coords
    private ArrayList<Tuple3> verts;
    private ArrayList<Tuple3> normals;
    private ArrayList<Tuple3> texCoords;

    public Faces(ArrayList<Tuple3> vs, ArrayList<Tuple3> ns, ArrayList<Tuple3> ts) {
        verts = vs;
        normals = ns;
        texCoords = ts;

        facesVertIdxs = new ArrayList<int[]>();
        facesTexIdxs = new ArrayList<int[]>();
        facesNormIdxs = new ArrayList<int[]>();
    } // end of Faces()

    public boolean addFace(String line) {
        try {
            line = line.substring(2); // skip the "f "
            StringTokenizer st = new StringTokenizer(line, " ");
            int numTokens = st.countTokens(); // number of v/vt/vn tokens
            // create arrays to hold the v, vt, vn indicies
            int[] v = new int[numTokens];
            int[] vt = new int[numTokens];
            int[] vn = new int[numTokens];

            for (int i = 0; i < numTokens; i++) {
                String faceToken = addFaceVals(st.nextToken()); // get a v/vt/vn token
                // System.out.println(faceToken);
                StringTokenizer st2 = new StringTokenizer(faceToken, "/");
                int numSeps = st2.countTokens(); // how many '/'s are there in the token
                v[i] = Integer.parseInt(st2.nextToken());
                vt[i] = (numSeps > 1) ? Integer.parseInt(st2.nextToken()) : 0;
                vn[i] = (numSeps > 2) ? Integer.parseInt(st2.nextToken()) : 0;
                // add 0's if the vt or vn index values are missing;
                // 0 is a good choice since real indicies start at 1
            }
            // store the indicies for this face
            facesVertIdxs.add(v);
            facesTexIdxs.add(vt);
            facesNormIdxs.add(vn);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect face index");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    } // end of addFace()

    private String addFaceVals(String faceStr) {
        char[] chars = faceStr.toCharArray();
        StringBuffer sb = new StringBuffer();
        char prevCh = 'x'; // dummy value
        for (int k = 0; k < chars.length; k++) {
            if (chars[k] == '/' && prevCh == '/') {
                // if no char between /'s
                sb.append('0'); // add a '0'
            }
            prevCh = chars[k];
            sb.append(prevCh);
        }
        return sb.toString();
    } // end of addFaceVals()

    public void renderFace(int i, GL gl) {
        if (i >= facesVertIdxs.size()) {
            // i out of bounds?
            return;
        }
        int[] vertIdxs = facesVertIdxs.get(i);
        // get the vertex indicies for face i
        int polytype;
        if (vertIdxs.length == 3) {
            polytype = gl.GL_TRIANGLES;
        } else if (vertIdxs.length == 4) {
            polytype = gl.GL_QUADS;
        } else {
            polytype = gl.GL_POLYGON;
        }
        gl.glBegin(polytype);

        // get the normal and tex coords indicies for face i
        int[] normIdxs = facesNormIdxs.get(i);
        int[] texIdxs = facesTexIdxs.get(i);

        /* render the normals, tex coords, and vertices for face i
        by accessing them using their indicies */
        Tuple3 vert;
        Tuple3 norm;
        Tuple3 texCoord;
        for (int f = 0; f < vertIdxs.length; f++) {

            if (normIdxs[f] != 0) {
                // if there are normals, render them
                norm = normals.get(normIdxs[f] - 1);
                gl.glNormal3f(norm.getX(), norm.getY(), norm.getZ());
            }

            if (texIdxs[f] != 0) {
                // if there are tex coords, render them
                texCoord = texCoords.get(texIdxs[f] - 1);
                if (texCoord.getZ() == DUMMY_Z_TC) {
                    // using 2D tex coords
                    gl.glTexCoord2f(texCoord.getX(), texCoord.getY());
                } else {
                    // 3D tex coords
                    gl.glTexCoord3f(texCoord.getX(), texCoord.getY(), texCoord.getZ());
                }
            }

            vert = verts.get(vertIdxs[f] - 1); // render the vertices
            gl.glVertex3f(vert.getX(), vert.getY(), vert.getZ());
        }

        gl.glEnd();
    } // end of renderFace()

    public int getNumFaces() {
        return facesVertIdxs.size();
    }
} // end of inner Class Faces