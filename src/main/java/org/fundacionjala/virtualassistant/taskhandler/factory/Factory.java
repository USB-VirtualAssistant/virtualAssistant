package org.fundacionjala.virtualassistant.taskhandler.factory;

import org.fundacionjala.virtualassistant.taskhandler.TaskAction;

public abstract class Factory {
    abstract TaskAction create();
}