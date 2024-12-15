package util;

import java.util.Objects;

public class Vector2  {

    private long x;
    private long y;

    public Vector2(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2 distance(Vector2 v) {
        return new Vector2(v.x - x, v.y - y);
    }

    public Vector2 scale(long scale) {
        return new Vector2(x * scale, y * scale);
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return x == vector2.x && y == vector2.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y +'}';
    }
}
