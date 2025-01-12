package com.spygstudios.spyglib.hologram;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;

public class Hologram {
    public static final double LINE_DISTANCE = 0.25d;

    private final HologramManager manager;
    private Location location;
    private final List<HologramRow> rows = new ArrayList<>();
    private final List<Player> viewers = new ArrayList<>();
    private final int viewDistance;

    public Hologram(HologramManager manager, Location location, int viewDistance) {
        this.manager = manager;
        this.location = location;
        this.viewDistance = viewDistance;
        for(Player player : location.getWorld().getPlayers()) {
            update(player);
        }
    }

    public Location getLocation() {
        return location.clone();
    }

    public void teleport(Location location) {
        if(location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        if(!location.getWorld().equals(this.location.getWorld())) {
            throw new IllegalArgumentException("Cannot teleport hologram to a different world");
        }
        this.location = location;
        update();
    }

    public HologramRow addRow(String text) {
        return addRow(Component.text(text));
    }

    public HologramRow addRow(Component text) {
        HologramRow row = new HologramTextRow(this, location.clone().add(0, -rows.size() * LINE_DISTANCE, 0), text);
        rows.add(row);
        return row;
    }

    public HologramRow addRow(ItemStack item) {
        HologramRow row = new HologramItemRow(this, location.clone().add(0, -rows.size() * LINE_DISTANCE, 0), item);
        rows.add(row);
        return row;
    }

    public void removeRow(int index) {
        if(index < 0 || index >= rows.size()) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        rows.remove(index);
        update();
    }

    public void removeRow(HologramRow row) {
        if(row == null) {
            throw new IllegalArgumentException("Row cannot be null");
        }
        if(rows.contains(row)) {
            rows.remove(row);
            update();
        }
    }

    public List<HologramRow> getRows() {
        return List.copyOf(rows);
    }

    public List<Player> getViewers() {
        return List.copyOf(viewers);
    }

    public void update(Player player) {
        if(player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if(!player.getWorld().equals(location.getWorld()) && viewers.contains(player) || !player.isOnline()) {
            viewers.remove(player);
            for(HologramRow row : rows) {
                row.hide(player);
            }
            return;
        }
        double distSqrt = player.getLocation().distanceSquared(location);
        if(distSqrt <= viewDistance * viewDistance) {
            if(!viewers.contains(player)) {
                viewers.add(player);
                for(HologramRow row : rows) {
                    row.show(player);
                }
            }
        } else if(viewers.contains(player)) {
            viewers.remove(player);
            for(HologramRow row : rows) {
                row.hide(player);
            }
        }
    }

    private void update() {
        for(int i = 0; i < rows.size(); i++) {
            HologramRow r = rows.get(i);
            r.teleport(location.clone().add(0, -i * LINE_DISTANCE, 0));
        }
}

    public void remove() {
        for(HologramRow row : rows) {
            row.remove();
        }
        manager.removeHologram(this);
    }

}
