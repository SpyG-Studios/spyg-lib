package com.spygstudios.spyglib.hologram;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class HologramRow {
    public abstract void show(Player player);
    public abstract void hide(Player player);
    public abstract void teleport(Location location);
    public abstract void remove();
}
