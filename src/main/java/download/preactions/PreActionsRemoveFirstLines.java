package download.preactions;

import control.wrappers.PreActionWrapper;

/**
 * Removes the specified number of trailing lines in a document. When no number is
 * specified {@value STD_NUM_LINES} is used
 */
class PreActionsRemoveFirstLines extends PreActionRemoveLines {
    static final String STD_NUM_LINES = "1";

    PreActionsRemoveFirstLines(PreActionWrapper pre) {
        super();

        int numLines = Integer.valueOf(pre.get("numLines").orElse(STD_NUM_LINES));

        int[] linesToRemove = new int[numLines];
        for(int i = 0; i < numLines; i++) {
            linesToRemove[i] = i;
        }

        setLinesToRemove(linesToRemove);
    }
}

