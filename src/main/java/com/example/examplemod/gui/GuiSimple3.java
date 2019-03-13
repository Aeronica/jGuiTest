package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import de.johni0702.minecraft.gui.container.AbstractGuiScreen;
import de.johni0702.minecraft.gui.container.GuiPanel;
import de.johni0702.minecraft.gui.element.GuiButton;
import de.johni0702.minecraft.gui.element.GuiLabel;
import de.johni0702.minecraft.gui.element.advanced.GuiProgressBar;
import de.johni0702.minecraft.gui.function.Closeable;
import de.johni0702.minecraft.gui.layout.CustomLayout;
import de.johni0702.minecraft.gui.layout.HorizontalLayout;
import de.johni0702.minecraft.gui.layout.VerticalLayout;
import net.minecraft.client.Minecraft;

public class GuiSimple3 extends AbstractGuiScreen<GuiSimple3> implements Closeable
{
    private final net.minecraft.client.gui.GuiScreen parent;

    private final GuiProgressBar progressBar = new GuiProgressBar().setSize(400, 20);

    private final GuiPanel textPanel = new GuiPanel().setLayout(new VerticalLayout().setSpacing(3));
    private final GuiPanel buttonPanel = new GuiPanel().setLayout(new HorizontalLayout(HorizontalLayout.Alignment.CENTER).setSpacing(5));
    private final GuiPanel contentPanel = new GuiPanel(this).addElements(new VerticalLayout.Data(0.5),
                                                                        textPanel, buttonPanel, progressBar).setLayout(new VerticalLayout().setSpacing(20));
    private final GuiButton yesButton = new GuiButton(buttonPanel).setSize(75, 20).setI18nLabel("gui.yes");
    private final GuiButton noButton = new GuiButton(buttonPanel).setSize(75, 20).setI18nLabel("gui.no");
    private final GuiButton startButton = new GuiButton(buttonPanel).setSize(75, 20).setI18nLabel("gui.examplemod.simple3.start_button");
    private final GuiButton stopButton = new GuiButton(buttonPanel).setSize(75, 20).setI18nLabel("gui.examplemod.simple3.stop_button");

    private volatile boolean killThread = false;

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

        stopButton.onClick(this::stopProgress).setDisabled();

        startButton.onClick(() -> {
            // Do stuff

            new Thread(() -> {
                float progress = 0f;
                startButton.setDisabled();
                stopButton.setEnabled();
                killThread = false;
                while (progress <= 1.01f && !killThread)
                {
                    progressBar.setProgress(progress);
                    try
                    {
                        Thread.sleep(100);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    progress += 0.01;
                }
                startButton.setEnabled();
                stopButton.setDisabled();
            }, "SimpleGui3").start();
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
        stopProgress();
        ExampleMod.logger.info("Close GuiSimple2");
    }

    private void stopProgress() {
        stopButton.setDisabled();
        killThread = true;
    }
}
