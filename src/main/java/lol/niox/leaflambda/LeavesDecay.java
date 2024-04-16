package lol.niox.leaflambda;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.HashSet;

public class LeavesDecay implements Listener {
    private final LeafLambda plugin;
    public static short radius = 6;
    public static ArrayList<Player> players = new ArrayList<>();

    public LeavesDecay(LeafLambda plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (players.contains(player)){
            return;
        }
        String blockType = block.getType().toString();
        if (blockType.contains("LOG") || blockType.contains("LEAVES")) {

            HashSet<Block> leaves = getNearbyLeaves(block);
            if (leaves.isEmpty()) {
                return;
            }
            new Decay(plugin, leaves, player).runTaskTimer(plugin, 0, 10);
            players.add(player);
        }
    }

    public static HashSet<Block> getNearbyLeaves(Block block) {
        HashSet<Block> leaves = new HashSet<>();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block b = block.getRelative(x, y, z);
                    if (block.getLocation().distance(b.getLocation()) <= radius) {
                        if (b.getBlockData() instanceof Leaves) {
                            Leaves leafData = (Leaves) b.getBlockData();
                            if (!leafData.isPersistent()) {
                                leaves.add(b);
                            }
                        }
                    }
                }
            }
        }
        return leaves;
    }
}
