package com.bawnorton.allthetrims.platform;

import java.nio.file.Path;

//? if fabric {
import net.fabricmc.loader.api.FabricLoader;

public final class Platform {
    public static Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static Path getGameDir() {
        return FabricLoader.getInstance().getGameDir();
    }

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
//?} elif neoforge {
/*import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;

public final class Platform {
    public static Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
}
*///?}

