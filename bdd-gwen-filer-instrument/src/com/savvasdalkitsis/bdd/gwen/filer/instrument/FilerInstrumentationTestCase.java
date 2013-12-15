package com.savvasdalkitsis.bdd.gwen.filer.instrument;

import android.app.Instrumentation;
import android.content.Context;
import android.test.InstrumentationTestCase;
import android.util.Log;
import com.jayway.android.robotium.solo.Solo;
import com.savvasdalkitsis.bdd.gwen.filer.instrument.model.agents.Folder;
import com.savvasdalkitsis.bdd.gwen.filer.instrument.model.agents.User;

import java.io.File;

public class FilerInstrumentationTestCase extends InstrumentationTestCase {
    protected Folder folder;
    protected User user;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Instrumentation instrumentation = getInstrumentation();
        Context targetContext = instrumentation.getTargetContext();
        File externalCacheDir = targetContext.getExternalCacheDir();
        clear(externalCacheDir);
        folder = new Folder(externalCacheDir);
        user = new User(targetContext, new Solo(instrumentation));
    }

    private void clear(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    Log.w(FilerInstrumentationTestCase.class.getName(), "Failed to delete file " + file.getAbsolutePath());
                }
            } else {
                clear(file);
            }
        }
    }
}