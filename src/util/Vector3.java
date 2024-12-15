package util;

import java.util.Objects;

public class Vector3 {

    private long x;
    private long y;
    private long z;

    public Vector3(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public Vector3 distance(Vector3 v) {
        return new Vector3(v.x - x, v.y - y, v.z - z);
    }

    public Vector3 scale(long scale) {
        return new Vector3(x * scale, y * scale, z * scale);
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public long getZ() {
        return z;
    }

    public void setZ(long z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vector3 vector3 = (Vector3) o;
        return x == vector3.x && y == vector3.y && z == vector3.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y + ", " + z + '}';
    }
}
