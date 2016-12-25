package cz.zcu.kiv.sehr.utils;

/**
 * Created by ghessova on 20.12.16.
 */
public class PagingParams {

    public int skip;
    public int limit;

    public PagingParams(int skip, int limit) {
        this.skip = skip;
        this.limit = limit;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
