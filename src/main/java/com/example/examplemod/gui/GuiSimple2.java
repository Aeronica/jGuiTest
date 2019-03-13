package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import de.johni0702.minecraft.gui.container.AbstractGuiScreen;
import de.johni0702.minecraft.gui.container.GuiPanel;
import de.johni0702.minecraft.gui.element.GuiButton;
import de.johni0702.minecraft.gui.element.GuiLabel;
import de.johni0702.minecraft.gui.element.GuiTooltip;
import de.johni0702.minecraft.gui.function.Closeable;
import de.johni0702.minecraft.gui.layout.CustomLayout;
import de.johni0702.minecraft.gui.layout.GridLayout;
import de.johni0702.minecraft.gui.layout.HorizontalLayout;
import de.johni0702.minecraft.gui.utils.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class GuiSimple2 extends AbstractGuiScreen<GuiSimple2> implements Closeable
{
    private final net.minecraft.client.gui.GuiScreen parent;
    public GuiButton currentTabButton;
    public GuiPanel currentTabPanel;

    public final List<GuiPanel> tabPanels = new ArrayList<>();

    public final GuiLabel warningLabel = new GuiLabel(this).setColor(Colors.RED)
            .setI18nText("gui.examplemod.editor.disclaimer");

    public final GuiPanel tabButtons = new GuiPanel(this).setLayout(new GridLayout().setSpacingX(5));

    public final GuiButton backButton = new GuiButton().setI18nLabel("gui.examplemod.back").setSize(100, 20);

    public final GuiPanel buttonPanel = new GuiPanel(this).setLayout(new HorizontalLayout().setSpacing(5))
            .addElements(null, backButton);

    public GuiSimple2(final net.minecraft.client.gui.GuiScreen parent)
    {
        this.parent = parent;
        backButton.onClick(new Runnable() {
            @Override
            public void run() {
                getMinecraft().displayGuiScreen(null);
            }
        });

        setTitle(new GuiLabel().setI18nText("gui.examplemod.title"));
        setBackground(Background.DIRT);

        makeTab("import", GuiPanel::new).setDisabled(); // Not yet implemented
        makeTab("upload", GuiPanel::new).setDisabled(); // Not yet implemented
        makeTab("edit", GuiPanel::new).setDisabled(); // Not yet implemented


        setLayout(new CustomLayout<GuiSimple2>()
        {
            @Override
            protected void layout(GuiSimple2 container, int width, int height)
            {
                // Move all inactive panels aside
                tabPanels.forEach(e -> pos(e, Integer.MIN_VALUE, Integer.MIN_VALUE));

                pos(warningLabel, 10, 22);
                size(warningLabel, width - 20, 10);

                pos(tabButtons, 10, y(warningLabel) + height(warningLabel) + 2);
                size(tabButtons, width - 20, 20);

                pos(buttonPanel, width - 10 - width(buttonPanel), height - 10 - height(buttonPanel));

                if (currentTabPanel != null)
                {
                    pos(currentTabPanel, 10, y(tabButtons) + height(tabButtons) + 10);
                    size(currentTabPanel, width - 20, y(buttonPanel) - 10 - y(currentTabPanel));
                }
            }
        });
    }

    public GuiButton makeTab(String name, Supplier<GuiPanel> panel)
    {
        GuiButton button = new GuiButton().setI18nLabel("gui.examplemod.simple." + name + ".title")
                .setTooltip(new GuiTooltip().setI18nText("gui.examplemod.simple." + name + ".description"))
                .setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        button.onClick(() ->
                       {
                           // Remove previous tab
                           if (currentTabButton != null)
                           {
                               currentTabButton.setEnabled();
                               removeElement(currentTabPanel);
                           }

                           // Activate new tab
                           currentTabButton = button.setDisabled();
                           addElements(null, currentTabPanel = panel.get());
                       });
        // Add new tab button to tabs panel
        tabButtons.addElements(null, button);
        ((GridLayout) tabButtons.getLayout()).setColumns(tabButtons.getChildren().size());
        return button;
    }

    @Override
    protected GuiSimple2 getThis()
    {
        return this;
    }

    @Override
    public void close()
    {
        ExampleMod.logger.info("Close GuiSimple2");
    }
}
