package com.nullblock.vemacs.blockfalltest;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BlockFallTest extends JavaPlugin implements Listener {

    private static List<XZ> newChunks = new ArrayList<XZ>();

    public void onDisable() {
    }

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChunkLoad(ChunkLoadEvent event) {
        if (event.isNewChunk()) {
            Chunk c = event.getChunk();
            final XZ v = new XZ(c.getX(), c.getZ());
            newChunks.add(v);
            BukkitRunnable r = new BukkitRunnable() {
                public void run() {
                    newChunks.remove(v);
                }
            };
            getServer().getScheduler().runTaskLater(this, r, 20);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block b = event.getBlockPlaced();
        BlockState bs = event.getBlockReplacedState();
        this.getLogger().info(String.format("Block %d:%d placed at %d, %d, %d replaces %d:%d by %s",
                b.getTypeId(), b.getData(), b.getX(), b.getY(), b.getZ(),
                bs.getTypeId(), bs.getData().getData(), event.getPlayer().getName()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        Block b = event.getBlock();
        this.getLogger().info(String.format("Block %d:%d broken at %d, %d, %d by %s",
                b.getTypeId(), b.getData(), b.getX(), b.getY(), b.getZ(), event.getPlayer().getName()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        Block b = event.getBlock();
        this.getLogger().info(String.format("Block %d:%d changed at %d, %d, %d to %d:%d by %s",
                b.getTypeId(), b.getData(), b.getX(), b.getY(), b.getZ(),
                event.getTo().getId(), event.getData(), event.getEntityType().getName()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBurn(BlockBurnEvent event) {
        Block b = event.getBlock();
        this.getLogger().info(String.format("Block %d:%d burnt at %d, %d, %d",
                b.getTypeId(), b.getData(), b.getX(), b.getY(), b.getZ()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockSpread(BlockSpreadEvent event) {
        Block b = event.getBlock();
        Block s = event.getSource();
        this.getLogger().info(String.format("Block %d:%d spread at %d, %d, %d by %d:%d at %d, %d, %d",
                b.getTypeId(), b.getData(), b.getX(), b.getY(), b.getZ(),
                s.getTypeId(), s.getData(), s.getX(), s.getY(), s.getZ()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockForm(BlockFormEvent event) {
        Block b = event.getNewState().getBlock();
        this.getLogger().info(String.format("Block %d:%d formed at %d, %d, %d",
                b.getTypeId(), b.getData(), b.getX(), b.getY(), b.getZ()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
         public void onBlockFromTo(BlockFromToEvent event) {
        Block b = event.getBlock();
        for (XZ v : newChunks)
            if (v.includes(b.getX(), b.getY())) {
                Block t = event.getToBlock();
                this.getLogger().info(String.format("Block %d:%d at %d, %d, %d turned into %d:%d",
                        b.getTypeId(), b.getData(), b.getX(), b.getY(), b.getZ(),
                        t.getTypeId(), t.getData()));
            } else {
                getLogger().info(String.format("Spammed at chunk at %d, %d, block at %d, %d" , v.getX() * 16, v.getZ() * 16, b.getX(), b.getZ()));
            }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityInteract(EntityInteractEvent event) {
        Block b = event.getBlock();
        this.getLogger().info(String.format("Block %d:%d (%s) at %d, %d, %d was interacted with by %s",
                b.getTypeId(), b.getData(), b.getType().toString(), b.getX(), b.getY(), b.getZ(),
                event.getEntityType().getName()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStructureGrow(StructureGrowEvent event) {
        List<BlockState> bl = event.getBlocks();
        for (BlockState bs : bl) {
            Block b = bs.getBlock();
            this.getLogger().info(String.format("Block %d:%d grew at %d, %d, %d",
                    b.getTypeId(), b.getData(), b.getX(), b.getY(), b.getZ()));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityExplode(EntityExplodeEvent event) {
        List<Block> bl = event.blockList();
        for (Block b : bl) {
            this.getLogger().info(String.format("Block %d:%d exploded at %d, %d, %d",
                    b.getTypeId(), b.getData(), b.getX(), b.getY(), b.getZ()));
        }
    }

}

