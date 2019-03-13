package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import de.johni0702.minecraft.gui.container.AbstractGuiScreen;
import de.johni0702.minecraft.gui.container.GuiPanel;
import de.johni0702.minecraft.gui.element.GuiButton;
import de.johni0702.minecraft.gui.element.GuiLabel;
import de.johni0702.minecraft.gui.function.Closeable;
import de.johni0702.minecraft.gui.layout.CustomLayout;
import de.johni0702.minecraft.gui.layout.HorizontalLayout;
import de.johni0702.minecraft.gui.layout.VerticalLayout;
import net.minecraft.client.Minecraft;

public class GuiSimple3 extends AbstractGuiScreen<GuiSimple3> implements Closeable
{
    private final net.minecraft.client.gui.GuiScreen parent;

    public final GuiPanel textPanel = new GuiPanel().setLayout(new VerticalLayout().setSpacing(3));
    public final GuiPanel buttonPanel = new GuiPanel().setLayout(new HorizontalLayout().setSpacing(5));
    public final GuiPanel contentPanel = new GuiPanel(this).addElements(new VerticalLayout.Data(0.5),
                                                                        textPanel, buttonPanel).setLayout(new VerticalLayout().setSpacing(20));
    public final GuiButton yesButton = new GuiButton(buttonPanel).setSize(150, 20).setI18nLabel("gui.yes");
    public final GuiButton noButton = new GuiButton(buttonPanel).setSize(150, 20).setI18nLabel("gui.no");

    public GuiSimple3(final net.minecraft.client.gui.GuiScreen parent)
    {
        this.parent = parent;

        textPanel.addElements(new VerticalLayout.Data(0.5),
                              new GuiLabel().setI18nText("gui.examplemod.simple3.label01"),
                              new GuiLabel().setI18nText("gui.examplemod.simple3.label02", "What's going on here?"),
                              new GuiLabel().setI18nText("gui.examplemod.simple3.label03"));


        yesButton.onClick(() -> {
            ExampleMod.logger.info("GuiSimple3 YES");
            Minecraft.getMinecraft().displayGuiScreen(null);
        });

        noButton.onClick(() -> {
            ExampleMod.logger.info("GuiSimple3 NO");
            Minecraft.getMinecraft().displayGuiScreen(null);
        });

        setLayout(new CustomLayout<GuiSimple3>() {
            @Override
            protected void layout(GuiSimple3 container, int width, int height) {
                pos(contentPanel, width / 2 - width(contentPanel) / 2, height / 2 - height(contentPanel) / 2);
            }
        });
    }

    @Override
    protected GuiSimple3 getThis()
    {
        return this;
    }

    @Override
    public void close()
    {
        ExampleMod.logger.info("Close GuiSimple2");
    }
}
