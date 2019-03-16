package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import de.johni0702.minecraft.gui.container.GuiPanel;
import de.johni0702.minecraft.gui.element.GuiButton;
import de.johni0702.minecraft.gui.element.GuiLabel;
import de.johni0702.minecraft.gui.layout.HorizontalLayout;
import de.johni0702.minecraft.gui.layout.VerticalLayout;
import de.johni0702.minecraft.gui.popup.GuiFileChooserPopup;

import javax.annotation.Nullable;
import java.io.File;

public class GuiSimplePanel3 extends GuiPanel
{
    private final GuiSimple gui;
    private File file;


    private final GuiButton dialog = new GuiButton()
            .onClick(new Runnable() {
                         @Override
                         public void run()
                         {
                             Futures.addCallback(
                                     GuiFileChooserPopup.openLoadGui(GuiSimplePanel3.this, "Load", ".txt", ".mml", ".zip", ".ms2mml")
                                             .getFuture(),
                                     new FutureCallback<File>()
                                     {
                                         @Override
                                         public void onSuccess(@Nullable File result)
                                         {
                                             file = result;
                                         }

                                         @Override
                                         public void onFailure(Throwable t)
                                         {
                                             t.printStackTrace();
                                             ExampleMod.logger.error(t);
                                         }
                                     });
                         }
                     }).setSize(75, 20).setI18nLabel("What?");

    GuiSimplePanel3(GuiSimple gui, File file)
    {
        this.gui = gui;
        this.file = file;

        final GuiPanel leftPanel = new GuiPanel()
                .addElements(null, new GuiLabel().setSize(75, 20).setI18nText("Something"))
                .setLayout(new VerticalLayout(VerticalLayout.Alignment.TOP).setSpacing(10));

        final GuiPanel rightPanel = new GuiPanel()
                .addElements(null, dialog)
                .setLayout(new VerticalLayout(VerticalLayout.Alignment.TOP).setSpacing(10));

        addElements(new HorizontalLayout.Data(), leftPanel, rightPanel);
    }
}
