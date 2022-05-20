package me.marsman512.mc_server_stop;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class McServerStop implements ModInitializer {
    public static final Logger LOG = LoggerFactory.getLogger(McServerStop.class);

    public static ModConfig modConfig;
    public static long stopTime = -1;

    private static TickHandler tickHandler;

    private static PlayerConnectionHandler playerConnectionHandler;

    @Override
    public void onInitialize() {
        modConfig = ModConfig.loadModConfig();
        LOG.info("Stop delay is {} seconds.", modConfig.stopDelaySeconds);

        tickHandler = new TickHandler();
        tickHandler.register();

        playerConnectionHandler = new PlayerConnectionHandler();
        playerConnectionHandler.register();
    }
}
