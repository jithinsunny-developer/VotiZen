package com.example.votizen;

class CandidatePoll {
    public String state_name, first_name, last_name, party_name, ward_number, promises_fulfilled, promises_made, criminal_records, symbol, photo;
    public long live_poll;

    public CandidatePoll() {
    }
    public String getState_name() {
        return state_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getParty_name() {
        return party_name;
    }

    public String getWard_number() {
        return ward_number;
    }

    public String getPromises_fulfilled() {
        return promises_fulfilled;
    }

    public String getPromises_made() {
        return promises_made;
    }

    public String getCriminal_records() {
        return criminal_records;
    }
    public long getLive_poll() {
        return live_poll;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setParty_name(String party_name) {
        this.party_name = party_name;
    }

    public void setWard_number(String ward_number) {
        this.ward_number = ward_number;
    }

    public void setPromises_fulfilled(String promises_fulfilled) {this.promises_fulfilled = promises_fulfilled;}

    public void setPromises_made(String promises_made) {
        this.promises_made = promises_made;
    }
    public void setCriminal_records(String criminal_records) {this.criminal_records = criminal_records;}
    public void setLive_poll(long live_poll){this.live_poll = live_poll;}
}
