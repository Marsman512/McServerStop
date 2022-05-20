package me.marsman512.mc_server_stop;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.Util;

public class PlayerConnectionHandler implements ServerPlayConnectionEvents.Init, ServerPlayConnectionEvents.Disconnect{
    public void register() {
        ServerPlayConnectionEvents.INIT.register(this);
        ServerPlayConnectionEvents.DISCONNECT.register(this);
    }


    @Override
    public void onPlayDisconnect(ServerPlayNetworkHandler handler, MinecraftServer server) {
        if(server.getCurrentPlayerCount() <= 1) {
            McServerStop.stopTime =
                    (long)McServerStop.modConfig.stopDelaySeconds * 1_000 + Util.getMeasuringTimeMs();

            McServerStop.LOG.info("Last player left. Stopping soon.");
        }
    }

    @Override
    public void onPlayInit(ServerPlayNetworkHandler handler, MinecraftServer server) {
        if(McServerStop.stopTime != -1) {
            McServerStop.LOG.info("Stop canceled. {} joined.", handler.getPlayer().getDisplayName().getString());
            McServerStop.stopTime = -1;
        }
    }
}
