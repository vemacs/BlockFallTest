package com.nullblock.vemacs.blockfalltest;

public class XZ {

    private int x;
    private int z;

    @Override
    public int hashCode() {
        return x + z;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof XZ) {
            return ((XZ) obj).getX() == x && ((XZ) obj).getZ() == z;
        }
        return false;
    }

    public XZ (int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

}
