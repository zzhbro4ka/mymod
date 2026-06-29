package com.example.mymod.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CustomMenuScreen extends Screen {

    private static final int MENU_WIDTH  = 200;
    private static final int MENU_HEIGHT = 180;

    private int menuX;
    private int menuY;

    public CustomMenuScreen() {
        super(Text.literal("§6§lМоё меню"));
    }

    @Override
    protected void init() {
        menuX = (this.width  - MENU_WIDTH)  / 2;
        menuY = (this.height - MENU_HEIGHT) / 2;

        int buttonX     = menuX + (MENU_WIDTH - 160) / 2;
        int buttonWidth = 160;
        int buttonHeight = 20;

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Сказать привет"),
                btn -> {
                    MinecraftClient mc = MinecraftClient.getInstance();
                    if (mc.player != null)
                        mc.player.sendMessage(Text.literal("§aПривет, мир!"), false);
                    this.client.setScreen(null);
                })
                .dimensions(buttonX, menuY + 40, buttonWidth, buttonHeight)
                .build());

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Показать время"),
                btn -> {
                    MinecraftClient mc = MinecraftClient.getInstance();
                    if (mc.player != null) {
                        long time = mc.player.getWorld().getTimeOfDay() % 24000;
                        mc.player.sendMessage(Text.literal("§eВремя: §f" + time + " §7тиков"), false);
                    }
                    this.client.setScreen(null);
                })
                .dimensions(buttonX, menuY + 68, buttonWidth, buttonHeight)
                .build());

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Мои координаты"),
                btn -> {
                    MinecraftClient mc = MinecraftClient.getInstance();
                    if (mc.player != null) {
                        double x = Math.round(mc.player.getX() * 10.0) / 10.0;
                        double y = Math.round(mc.player.getY() * 10.0) / 10.0;
                        double z = Math.round(mc.player.getZ() * 10.0) / 10.0;
                        mc.player.sendMessage(Text.literal("§bX=" + x + " Y=" + y + " Z=" + z), false);
                    }
                    this.client.setScreen(null);
                })
                .dimensions(buttonX, menuY + 96, buttonWidth, buttonHeight)
                .build());

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Моё здоровье"),
                btn -> {
                    MinecraftClient mc = MinecraftClient.getInstance();
                    if (mc.player != null) {
                        float hp    = mc.player.getHealth();
                        float maxHp = mc.player.getMaxHealth();
                        mc.player.sendMessage(Text.literal("§cЗдоровье: §f" + hp + "/" + maxHp), false);
                    }
                    this.client.setScreen(null);
                })
                .dimensions(buttonX, menuY + 124, buttonWidth, buttonHeight)
                .build());

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Закрыть"),
                btn -> this.client.setScreen(null))
                .dimensions(buttonX, menuY + 152, buttonWidth, buttonHeight)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.fill(menuX, menuY, menuX + MENU_WIDTH, menuY + MENU_HEIGHT, 0xCC1A1A2E);
        context.drawBorder(menuX, menuY, MENU_WIDTH, MENU_HEIGHT, 0xFFFFAA00);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, menuY + 14, 0xFFFFAA00);
        context.fill(menuX + 10, menuY + 28, menuX + MENU_WIDTH - 10, menuY + 29, 0x88FFAA00);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
