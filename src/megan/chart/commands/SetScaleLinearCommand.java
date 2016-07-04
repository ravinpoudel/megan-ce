/*
 *  Copyright (C) 2016 Daniel H. Huson
 *
 *  (Some files contain contributions from other authors, who are then mentioned separately.)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package megan.chart.commands;

import jloda.gui.commands.CommandBase;
import jloda.gui.commands.ICheckBoxCommand;
import jloda.util.Basic;
import jloda.util.ResourceManager;
import jloda.util.parse.NexusStreamParser;
import megan.chart.gui.ChartViewer;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SetScaleLinearCommand extends CommandBase implements ICheckBoxCommand {
    public boolean isSelected() {
        ChartViewer chartViewer = (ChartViewer) getViewer();
        return chartViewer.getChartDrawer() != null && chartViewer.getScalingType() == ChartViewer.ScalingType.LINEAR;
    }

    public String getSyntax() {
        return "set chartScale={" + Basic.toString(ChartViewer.ScalingType.values(), "|") + "};";
    }

    public void apply(NexusStreamParser np) throws Exception {
        np.matchIgnoreCase("set chartScale=");
        String scale = np.getWordMatchesIgnoringCase(Basic.toString(ChartViewer.ScalingType.values(), " "));
        np.matchIgnoreCase(";");

        final ChartViewer chartViewer = (ChartViewer) getViewer();
        chartViewer.setScalingType(ChartViewer.ScalingType.valueOf(scale.toUpperCase()));
        chartViewer.repaint();
    }

    public void actionPerformed(ActionEvent event) {
        execute("set chartScale=linear;");
    }

    public boolean isApplicable() {
        ChartViewer chartViewer = (ChartViewer) getViewer();
        return chartViewer.getChartData() != null && chartViewer.getChartDrawer() != null && chartViewer.getChartDrawer().isSupportedScalingType(ChartViewer.ScalingType.LINEAR);
    }

    public String getName() {
        return "Linear Scale";
    }

    public String getDescription() {
        return "Show values on a linear scale";
    }

    public ImageIcon getIcon() {
        return ResourceManager.getIcon("LinScale16.gif");
    }

    public boolean isCritical() {
        return true;
    }

    /**
     * gets the accelerator key  to be used in menu
     *
     * @return accelerator key
     */
    public KeyStroke getAcceleratorKey() {
        return null;
    }
}

