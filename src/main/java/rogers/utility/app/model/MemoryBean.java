package rogers.utility.app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryBean {

    boolean osmDBCall=false;
    long osmDBCallMili=0;
    RequestBean callTrack;
    int size=0;
    Map<String,CallTrackBean> tracks=new HashMap<>();


    public boolean isOsmDBCall() {
        return osmDBCall;
    }

    public void setOsmDBCall(boolean osmDBCall) {
        this.osmDBCall = osmDBCall;
    }

    public long getOsmDBCallMili() {
        return osmDBCallMili;
    }

    public void setOsmDBCallMili(long osmDBCallMili) {
        this.osmDBCallMili = osmDBCallMili;
    }

    public Map<String, CallTrackBean> getTracks() {
        return tracks;
    }

    public void setTracks(Map<String, CallTrackBean> tracks) {
        this.tracks = tracks;
    }

    public RequestBean getCallTrack() {
        return callTrack;
    }

    public void setCallTrack(RequestBean callTrack) {
        this.callTrack = callTrack;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
