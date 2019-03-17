package com.example.examplemod.gui;

import de.johni0702.minecraft.gui.container.GuiPanel;
import de.johni0702.minecraft.gui.container.GuiVerticalList;
import de.johni0702.minecraft.gui.element.GuiLabel;
import de.johni0702.minecraft.gui.element.GuiNumberField;
import de.johni0702.minecraft.gui.element.GuiSlider;
import de.johni0702.minecraft.gui.layout.GridLayout;
import de.johni0702.minecraft.gui.layout.VerticalLayout;
import org.lwjgl.util.ReadableColor;


public class GuiSimplePanel2 extends GuiPanel
{
    private static GuiNumberField newGuiNumberField() {
        return new GuiNumberField().setMaxLength(2).setSize(20, 20).setValidateOnFocusChange(true);
    }
    private final GuiSimple gui;

    public final GuiNumberField scrollValue = newGuiNumberField().setMaxLength(5).setWidth(40);
    private final GuiSlider slider = new GuiSlider(this).setSize(100, 20).setSteps(3)
            .setI18nText("Steps").onValueChanged(this::sliderUpdated);

    private final GuiVerticalList scrolls = new GuiVerticalList()
            .setSize(40, 48)
            .setBackgroundColor(ReadableColor.BLACK)
            .setLayout(new VerticalLayout().setSpacing(2))
            .addElements(new VerticalLayout.Data(0.5),
                         new GuiLabel().setI18nText("H1").setSize(20, 10),
                         new GuiLabel().setI18nText("H2").setSize(20, 10),
                         new GuiLabel().setI18nText("H3").setSize(20, 10),
                         new GuiLabel().setI18nText("H4").setSize(20, 10),
                         new GuiLabel().setI18nText("H5").setSize(20, 10),
                         new GuiLabel().setI18nText("H6").setSize(20, 10),
                         new GuiLabel().setI18nText("H7").setSize(20, 10),
                         new GuiLabel().setI18nText("H8").setSize(20, 10));

    private final GuiPanel testPanel = new GuiPanel(this)
            .setLayout(new GridLayout().setCellsEqualSize(false).setSpacingX(20).setSpacingY(5).setColumns(2))
            .addElements(new GridLayout.Data(0, 0.5), scrolls, scrollValue);

    GuiSimplePanel2(GuiSimple gui)
    {
        this.gui = gui;

        setLayout(new VerticalLayout().setSpacing(5));


    }

    private void sliderUpdated()
    {
        int value = slider.getValue() * 12;
        scrolls.setOffsetY(value);
        scrollValue.setValue(value);
    }
}
