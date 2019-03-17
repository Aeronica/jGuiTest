package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import de.johni0702.minecraft.gui.container.GuiPanel;
import de.johni0702.minecraft.gui.element.GuiLabel;
import de.johni0702.minecraft.gui.element.GuiNumberField;
import de.johni0702.minecraft.gui.element.advanced.GuiDropdownMenu;
import de.johni0702.minecraft.gui.layout.GridLayout;
import de.johni0702.minecraft.gui.layout.HorizontalLayout;
import de.johni0702.minecraft.gui.layout.VerticalLayout;
import net.minecraft.client.resources.I18n;
import org.lwjgl.util.Dimension;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.OptionalInt.of;

public class GuiSimplePanel extends GuiPanel
{
    private static GuiNumberField newGuiNumberField() {
        return new GuiNumberField().setMaxLength(2).setSize(20, 20).setValidateOnFocusChange(true);
    }

    private final GuiSimple gui;
    private static final Path NO_PATHS = Paths.get(".");

    private GuiDropdownMenu<Path> filePaths = new GuiDropdownMenu<Path>(this)
            .setMinSize(new Dimension(200, 20)).onSelection(i -> updatePaths())
            .setToString(p -> p == NO_PATHS ? "" : p.getFileName().toString());

    public final GuiNumberField startHour = newGuiNumberField();
    public final GuiNumberField startMin = newGuiNumberField();
    public final GuiNumberField startSec = newGuiNumberField();
    public final GuiNumberField startMilli = newGuiNumberField().setSize(40, 20).setMaxLength(4);
    public final GuiDropdownMenu<Integer> startMarker = new GuiDropdownMenu<>();

    public final GuiNumberField endHour = newGuiNumberField();
    public final GuiNumberField endMin = newGuiNumberField();
    public final GuiNumberField endSec = newGuiNumberField();
    public final GuiNumberField endMilli = newGuiNumberField().setSize(40, 20).setMaxLength(4);
    public final GuiDropdownMenu<Integer> endMarker = new GuiDropdownMenu<>();

    private final GuiPanel testPanel = new GuiPanel(this)
            .setLayout(new GridLayout().setCellsEqualSize(false).setSpacingX(20).setSpacingY(5).setColumns(3))
            .addElements(new GridLayout.Data(0, 0.5),
                         // line 1, column 1, label
                         new GuiLabel().setI18nText("Start"),
                         // line 1, column 2, H:M:S.ms
                         new GuiPanel().setLayout(new HorizontalLayout().setSpacing(2)).addElements(
                                 new HorizontalLayout.Data(0.5),
                                 startHour, new GuiLabel().setI18nText("hours"),
                                 startMin, new GuiLabel().setI18nText("minutes"),
                                 startSec, new GuiLabel().setI18nText("seconds"),
                                 startMilli, new GuiLabel().setI18nText("ms")),
                         // line 1, column 3, drop down
                         startMarker,

                         // Line 2, column 1, label
                         new GuiLabel().setI18nText("End"),
                         // line 2, column 2, H:M:S.ms
                         new GuiPanel().setLayout(new HorizontalLayout().setSpacing(2)).addElements(
                                 new HorizontalLayout.Data(0.5),
                                 endHour, new GuiLabel().setI18nText("hours"),
                                 endMin, new GuiLabel().setI18nText("minutes"),
                                 endSec, new GuiLabel().setI18nText("seconds"),
                                 endMilli, new GuiLabel().setI18nText("ms")),
                         // line 2, column 3, drop down
                         endMarker);

    GuiSimplePanel(GuiSimple gui)
    {
        this.gui = gui;

        setLayout(new VerticalLayout().setSpacing(10));

        Function<Integer, String> toString = m -> {
            if (m == null) {
                return I18n.format("gui.examplemod.simple3.label01");
            } else {
                return of(m).orElse(-1) + " (" + (m / 1000) + ")";
            }
        };

        startMarker.setToString(toString).setMinSize(new Dimension(100, 20))
                .onSelection(i -> onSelectedMarkerChanged(startMarker, startHour, startMin, startSec, startMilli));
        endMarker.setToString(toString).setMinSize(new Dimension(100, 20))
                .onSelection(i -> onSelectedMarkerChanged(endMarker, endHour, endMin, endSec, endMilli));

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

    private void onSelectedMarkerChanged(GuiDropdownMenu<Integer> dropdown, GuiNumberField hour, GuiNumberField min,
                                         GuiNumberField sec, GuiNumberField milli) {
        Integer marker = dropdown.getSelectedValue();
        if (marker != null) {
            int time = marker;
            setTime(time, hour, min, sec, milli);
        }
    }

    private void setTime(int time, GuiNumberField hour, GuiNumberField min,
                         GuiNumberField sec, GuiNumberField milli) {
        milli.setValue(time % 1000); time /= 1000;
        sec.setValue(time % 60); time /= 60;
        min.setValue(time % 60); time /= 60;
        hour.setValue(time);
    }

    private void updatePaths()
    {
        if (filePaths.getValues().length > 0)
        {
            Path path = filePaths.getSelectedValue();
            ExampleMod.logger.info("Selected: {}", path.toString());
        }

        startMarker.setValues(new Integer[]{11111,22222,33333}).setSelected(0);
        endMarker.setValues(new Integer[]{44444,55555,66666}).setSelected(0);
    }
}
