package me.marsman512.mc_server_stop;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;

public class TickHandler implements ServerTickEvents.EndTick {
    public void register() {
        ServerTickEvents.END_SERVER_TICK.register(this);
    }

    @Override
    public void onEndTick(MinecraftServer server) {
        if(McServerStop.stopTime != -1 && Util.getMeasuringTimeMs() >= McServerStop.stopTime) {
            McServerStop.LOG.info("Stopping the server!");
            server.stop(false);
        }
    }
}
