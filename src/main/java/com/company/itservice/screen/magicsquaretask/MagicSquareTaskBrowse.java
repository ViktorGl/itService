package com.company.itservice.screen.magicsquaretask;

import io.jmix.ui.screen.*;
import com.company.itservice.entity.MagicSquareTask;

@UiController("MagicSquareTask.browse")
@UiDescriptor("magic-square-task-browse.xml")
@LookupComponent("magicSquareTasksTable")
public class MagicSquareTaskBrowse extends StandardLookup<MagicSquareTask> {
}