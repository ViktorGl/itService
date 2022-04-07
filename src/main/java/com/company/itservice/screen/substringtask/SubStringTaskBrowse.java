package com.company.itservice.screen.substringtask;

import io.jmix.ui.screen.*;
import com.company.itservice.entity.SubStringTask;

@UiController("SubStringTask.browse")
@UiDescriptor("sub-string-task-browse.xml")
@LookupComponent("subStringTasksTable")
public class SubStringTaskBrowse extends StandardLookup<SubStringTask> {
}