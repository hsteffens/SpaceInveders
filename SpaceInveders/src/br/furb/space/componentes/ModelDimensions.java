package br.furb.space.componentes;

//----------------  inner Class  --------------------
import java.text.DecimalFormat;

/* This class calculates the 'edge' coordinates for the model
along its three dimensions.
The edge coords are used to calculate the model's:
 * width, height, depth
 * its largest dimension (width, height, or depth)
 * (x, y, z) center point
 */
//inner class ModelDimensions
public class ModelDimensions {

    // edge coordinates
    private float leftPt;
    private float rightPt; // on x-axis
    private float topPt;
    private float bottomPt; // on y-axis
    private float farPt;
    private float nearPt; // on z-axis
    // for reporting
    private DecimalFormat df = new DecimalFormat("0.##"); // 2 dp

    public ModelDimensions() {
        leftPt = 0.0f;
        rightPt = 0.0f;
        topPt = 0.0f;
        bottomPt = 0.0f;
        farPt = 0.0f;
        nearPt = 0.0f;
    } // end of ModelDimensions()

    public void set(Tuple3 vert) {
        rightPt = vert.getX();
        leftPt = vert.getX();

        topPt = vert.getY();
        bottomPt = vert.getY();

        nearPt = vert.getZ();
        farPt = vert.getZ();
    } // end of set()

    public void update(Tuple3 vert) {
        if (vert.getX() > rightPt) {
            rightPt = vert.getX();
        }
        if (vert.getX() < leftPt) {
            leftPt = vert.getX();
        }
        if (vert.getY() > topPt) {
            topPt = vert.getY();
        }
        if (vert.getY() < bottomPt) {
            bottomPt = vert.getY();
        }
        if (vert.getZ() > nearPt) {
            nearPt = vert.getZ();
        }
        if (vert.getZ() < farPt) {
            farPt = vert.getZ();
        }
    } // end of update()

    // ------------- use the edge coordinates ----------------------------
    public float getWidth() {
        return rightPt - leftPt;
    }

    public float getHeight() {
        return topPt - bottomPt;
    }

    public float getDepth() {
        return nearPt - farPt;
    }

    public float getLargest() {
        float height = getHeight();
        float depth = getDepth();

        float largest = getWidth();
        if (height > largest) {
            largest = height;
        }
        if (depth > largest) {
            largest = depth;
        }
        return largest;
    } // end of getLargest()

    public Tuple3 getCenter() {
        float xc = (rightPt + leftPt) / 2.0f;
        float yc = (topPt + bottomPt) / 2.0f;
        float zc = (nearPt + farPt) / 2.0f;
        return new Tuple3(xc, yc, zc);
    } // end of getCenter()

    public void reportDimensions() {
        Tuple3 center = getCenter();

        System.out.println("x Coords: " + df.format(leftPt) + " to " + df.format(rightPt));
        System.out.println("  Mid: " + df.format(center.getX()) + "; Width: " + df.format(getWidth()));

        System.out.println("y Coords: " + df.format(bottomPt) + " to " + df.format(topPt));
        System.out.println("  Mid: " + df.format(center.getY()) + "; Height: " + df.format(getHeight()));

        System.out.println("z Coords: " + df.format(nearPt) + " to " + df.format(farPt));
        System.out.println("  Mid: " + df.format(center.getZ()) + "; Depth: " + df.format(getDepth()));
    } // end of reportDimensions()
} // end of inner class ModelDimensions