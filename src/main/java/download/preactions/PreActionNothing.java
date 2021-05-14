package download.preactions;

class PreActionNothing implements PreAction {

    public PreActionNothing() {
    }

    public PreActionNothing(Object... args) {
    }

    public boolean compute(final byte[] data) {
        return true;
    }
}
