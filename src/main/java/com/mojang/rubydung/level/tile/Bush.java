package com.mojang.rubydung.level.tile;

import com.mojang.rubydung.level.Level;
import com.mojang.rubydung.level.Tessellator;
import com.mojang.rubydung.phys.AABB;

import java.util.Random;

public class Bush extends Tile {

    /**
     * Create a bush tile with given id
     *
     * @param id Id of the tile
     */
    protected Bush(int id) {
        super(id);

        // Set texture slot id
        this.textureId = 15;
    }

    @Override
    public void onTick(Level level, int x, int y, int z, Random random) {
        int tileIdBelow = level.getTile(x, y - 1, z);

        // Destroy bush if there is no light or no dirt/grass below it
        if (!level.isLit(x, y, z) || (tileIdBelow != Tile.dirt.id && tileIdBelow != Tile.grass.id)) {
            level.setTile(x, y, z, 0);
        }
    }

    @Override
    public void render(Tessellator tessellator, Level level, int layer, int x, int y, int z) {
        // Render in correct layer
        if (level.isLit(x, y, z) ^ layer != 1) {
            return;
        }

        int textureId = this.getTexture(this.textureId);

        float minU = textureId % 16 / 16.0F;
        float minV = minU + 999 / 16000.0F;
        float maxU = (float) (textureId / 16) / 16.0F;
        float maxV = maxU + 999 / 16000.0F;

        int rots = 2;

        tessellator.color(1.0f, 1.0f, 1.0f);

        for (int r = 0; r < rots; r++) {

            float xa = (float) (Math.sin(r * Math.PI / rots + 0.7853981633974483) / 2.0D);
            float za = (float) (Math.cos(r * Math.PI / rots + 0.7853981633974483) / 2.0D);

            float minX = x + 0.5F - xa;
            float maxX = x + 0.5F + xa;
            float minY = y + 0.0F;
            float maxY = y + 1.0F;
            float minZ = z + 0.5F - za;
            float maxZ = z + 0.5F + za;

            // Render bush side
            tessellator.vertexUV(minX, maxY, minZ, minV, maxU);
            tessellator.vertexUV(maxX, maxY, maxZ, minU, maxU);
            tessellator.vertexUV(maxX, minY, maxZ, minU, maxV);
            tessellator.vertexUV(minX, minY, minZ, minV, maxV);
            tessellator.vertexUV(maxX, maxY, maxZ, minU, maxU);
            tessellator.vertexUV(minX, maxY, minZ, minV, maxU);
            tessellator.vertexUV(minX, minY, minZ, minV, maxV);
            tessellator.vertexUV(maxX, minY, maxZ, minU, maxV);
        }
    }

    @Override
    public AABB getAABB(int x, int y, int z) {
        return null;
    }

    @Override
    public boolean blocksLight() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
