package com.domain;

public class Note_and_extra_fileWithBLOBs extends Note_and_extra_file {
    private String overview;

    private String note;

    private byte[] extraFile;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public byte[] getExtraFile() {
        return extraFile;
    }

    public void setExtraFile(byte[] extraFile) {
        this.extraFile = extraFile;
    }
}