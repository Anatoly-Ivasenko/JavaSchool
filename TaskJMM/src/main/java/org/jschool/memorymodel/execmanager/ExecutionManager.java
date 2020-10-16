package org.jschool.memorymodel.execmanager;

import org.jschool.memorymodel.execmanager.Context;

public interface ExecutionManager {

    Context execute(Runnable callback, Runnable... tasks);
}
