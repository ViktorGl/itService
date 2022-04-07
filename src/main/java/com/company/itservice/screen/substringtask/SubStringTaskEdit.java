package com.company.itservice.screen.substringtask;

import io.jmix.ui.screen.*;
import com.company.itservice.entity.SubStringTask;

@UiController("SubStringTask.edit")
@UiDescriptor("sub-string-task-edit.xml")
@EditedEntityContainer("subStringTaskDc")
public class SubStringTaskEdit extends StandardEditor<SubStringTask> {
}