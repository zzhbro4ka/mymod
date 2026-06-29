package com.example.mymod;

import com.example.mymod.client.gui.CustomMenuScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class MyModClient implements ClientModInitializer {

    public static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        // Регистрируем клавишу Right Shift
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mymod.open_menu",       // translation key
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,   // Right Shift
                "category.mymod.general"     // категория в настройках
        ));

        // Слушаем нажатие каждый тик
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new CustomMenuScreen());
                }
            }
        });
    }
}
