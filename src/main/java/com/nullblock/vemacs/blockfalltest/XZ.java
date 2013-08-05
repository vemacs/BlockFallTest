package com.nullblock.vemacs.blockfalltest;

public class XZ {

    private int x;
    private int z;

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

    public boolean includes(int nx, int nz) {
        if (x >= nx && nx < x + 16 && z >= nz && nz < z + 16) {
            return true;
        }
        return false;
    }
}
