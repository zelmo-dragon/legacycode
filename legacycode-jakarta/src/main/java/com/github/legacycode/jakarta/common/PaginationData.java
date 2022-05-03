package com.github.legacycode.jakarta.common;

import java.util.Collection;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.config.PropertyOrderStrategy;

@JsonbPropertyOrder(PropertyOrderStrategy.LEXICOGRAPHICAL)
public class PaginationData<E> {

    @JsonbProperty("data")
    private Collection<E> data;

    @JsonbProperty("size")
    private long size;

    @JsonbProperty("pageSize")
    private long pageSize;

    @JsonbProperty("pageNumber")
    private long pageNumber;

    @JsonbProperty("pageCount")
    private long pageCount;

    public PaginationData() {
    }

    // Accesseurs & Mutateurs


    public Collection<E> getData() {
        return data;
    }

    public void setData(Collection<E> data) {
        this.data = data;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }
}
