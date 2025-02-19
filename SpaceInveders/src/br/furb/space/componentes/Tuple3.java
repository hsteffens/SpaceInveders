package br.furb.space.componentes;
// Tuple3.java

/* A tuple of 3 elements, which can be used to
   store a vertex, normal, or texture coord.
*/




public class Tuple3
{
  private float x, y, z;


  public Tuple3(float xc, float yc, float zc)
  {
    x = xc;
    y = yc;
    z = zc;
  }


  public String toString()
  {  return "( " + x + ", " + y + ", " + z + " )";  }


  public void setX(float xc)
  {  x = xc;  }

  public float getX()
  { return x; }


  public void setY(float yc)
  {  y = yc;  }

  public float getY()
  { return y; }


  public void setZ(float zc)
  {  z = zc;  }

  public float getZ()
  { return z; }

}  // end of Tuple3 class