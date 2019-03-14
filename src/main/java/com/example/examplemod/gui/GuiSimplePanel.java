package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import de.johni0702.minecraft.gui.container.GuiPanel;
import de.johni0702.minecraft.gui.element.advanced.GuiDropdownMenu;
import de.johni0702.minecraft.gui.layout.VerticalLayout;
import de.johni0702.minecraft.gui.utils.lwjgl.Dimension;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GuiSimplePanel extends GuiPanel
{
    private final GuiSimple gui;
    private static final Path NO_PATHS = Paths.get(".");

    private GuiDropdownMenu<Path> filePaths = new GuiDropdownMenu<Path>(this)
            .setMinSize(new Dimension(200, 20)).onSelection(i -> updatePaths())
            .setToString(p -> p == NO_PATHS ? "" : p.getFileName().toString());

    GuiSimplePanel(GuiSimple gui)
    {
        this.gui = gui;

        setLayout(new VerticalLayout().setSpacing(10));

        List<Path> pathList = new ArrayList<>();
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("."));
            for (Path entry : stream)
            {
                pathList.add(entry.getFileName());
            }
        }
        catch (IOException | NullPointerException e)
        {
            e.printStackTrace();
        }

        Path[] paths = pathList.toArray(new Path[0]);
        filePaths.setValues(paths);
        updatePaths();
    }

    private void updatePaths()
    {
        if (filePaths.getValues().length > 0)
        {
            Path path = filePaths.getSelectedValue();
            ExampleMod.logger.info("Selected: {}", path.toString());
        }
    }
}
