package download.preactions;

class PreActionNothing implements PreAction {

    public PreActionNothing() {
    }

    public PreActionNothing(Object... args) {
    }

    public byte[] compute(byte[] data) {
        return data;
    }
}
