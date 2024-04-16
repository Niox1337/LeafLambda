package lol.niox.leaflambda;

import org.bukkit.block.Block;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class Decay extends BukkitRunnable {
    LeafLambda plugin;
    private HashSet<Block> leaves;
    private final Player player;
    private short times = 0;

    public Decay(LeafLambda plugin, HashSet<Block> leaves, Player player) {
        this.leaves = leaves;
        this.player = player;
    }

    @Override
    public void run() {
        Random random = new Random();
        if (leaves.isEmpty() || times > 10) {
            LeavesDecay.players.remove(player);
            this.cancel();
        }
        times++;
        Iterator<Block> iterator = leaves.iterator();
        while (iterator.hasNext()) {
            Block leaf = iterator.next();
            if (leaf.getBlockData() instanceof Leaves) {
                Leaves leafData = (Leaves) leaf.getBlockData();
                if (leafData.getDistance() > 6){
                    if (random.nextInt(100) < 50) {
                        player.stopSound(leaf.getBlockData().getSoundGroup().getBreakSound());
                        leaf.breakNaturally();
                        iterator.remove();
                    }
                }
            }
        }
    }
}
