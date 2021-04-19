package download.preactions;

class PreActionsRemoveFirstLines extends PreActionRemoveLines {
    PreActionsRemoveFirstLines(int numLines) {
        super();

        int[] linesToRemove = new int[numLines];
        for(int i = 0; i < numLines; i++) {
            linesToRemove[i] = i;
        }

        setLinesToRemove(linesToRemove);
    }
}

