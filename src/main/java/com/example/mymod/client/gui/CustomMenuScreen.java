package com.example.mymod.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CustomMenuScreen extends Screen {

    // Размеры окна меню
    private static final int MENU_WIDTH  = 200;
    private static final int MENU_HEIGHT = 180;

    // Координаты верхнего левого угла (вычисляются по центру экрана)
    private int menuX;
    private int menuY;

    public CustomMenuScreen() {
        super(Text.literal("§6§lМоё меню"));
    }

    @Override
    protected void init() {
        menuX = (this.width  - MENU_WIDTH)  / 2;
        menuY = (this.height - MENU_HEIGHT) / 2;

        int buttonX      = menuX + (MENU_WIDTH - 160) / 2;
        int buttonWidth  = 160;
        int buttonHeight = 20;

        // Кнопка 1 — Привет
        addDrawableChild(ButtonWidget.builder(
                Text.literal("👋  Сказать привет"),
                btn -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.client.player.sendMessage(
                            Text.literal("§aПривет, мир!"), false);
                    this.close();
                })
                .dimensions(buttonX, menuY + 40, buttonWidth, buttonHeight)
                .build());

        // Кнопка 2 — Время
        addDrawableChild(ButtonWidget.builder(
                Text.literal("⏰  Показать время"),
                btn -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    long time = this.client.player.getWorld().getTimeOfDay() % 24000;
                    this.client.player.sendMessage(
                            Text.literal("§eВремя в мире: §f" + time + " §7тиков"), false);
                    this.close();
                })
                .dimensions(buttonX, menuY + 68, buttonWidth, buttonHeight)
                .build());

        // Кнопка 3 — Координаты
        addDrawableChild(ButtonWidget.builder(
                Text.literal("📍  Мои координаты"),
                btn -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    double x = Math.round(this.client.player.getX() * 10.0) / 10.0;
                    double y = Math.round(this.client.player.getY() * 10.0) / 10.0;
                    double z = Math.round(this.client.player.getZ() * 10.0) / 10.0;
                    this.client.player.sendMessage(
                            Text.literal("§bКоординаты: §fX=" + x + " Y=" + y + " Z=" + z), false);
                    this.close();
                })
                .dimensions(buttonX, menuY + 96, buttonWidth, buttonHeight)
                .build());

        // Кнопка 4 — Здоровье
        addDrawableChild(ButtonWidget.builder(
                Text.literal("❤  Моё здоровье"),
                btn -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    float hp    = this.client.player.getHealth();
                    float maxHp = this.client.player.getMaxHealth();
                    this.client.player.sendMessage(
                            Text.literal("§cЗдоровье: §f" + hp + " / " + maxHp), false);
                    this.close();
                })
                .dimensions(buttonX, menuY + 124, buttonWidth, buttonHeight)
                .build());

        // Кнопка «Закрыть»
        addDrawableChild(ButtonWidget.builder(
                Text.literal("✖  Закрыть"),
                btn -> this.close())
                .dimensions(buttonX, menuY + 152, buttonWidth, buttonHeight)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Затемнение фона
        this.renderBackground(context, mouseX, mouseY, delta);

        // Фон меню — тёмный прямоугольник
        context.fill(menuX, menuY, menuX + MENU_WIDTH, menuY + MENU_HEIGHT, 0xCC1A1A2E);

        // Рамка
        context.drawBorder(menuX, menuY, MENU_WIDTH, MENU_HEIGHT, 0xFFFFAA00);

        // Заголовок
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                this.title,
                this.width / 2,
                menuY + 14,
                0xFFFFAA00);

        // Разделитель под заголовком
        context.fill(menuX + 10, menuY + 28, menuX + MENU_WIDTH - 10, menuY + 29, 0x88FFAA00);

        // Рисуем кнопки и прочее
        super.render(context, mouseX, mouseY, delta);
    }

    // Закрывается на Escape — поведение по умолчанию
    @Override
    public boolean shouldPause() {
        return false; // Игра не останавливается пока открыто меню
    }
}
