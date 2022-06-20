package com.github.legacycode.infrastructure.search;

import java.util.ArrayList;
import java.util.List;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.config.PropertyOrderStrategy;

@JsonbPropertyOrder(PropertyOrderStrategy.LEXICOGRAPHICAL)
public class PaginationData<T> {

    @JsonbProperty("data")
    private List<T> data;

    @JsonbProperty("size")
    private long size;

    @JsonbProperty("pageSize")
    private long pageSize;

    @JsonbProperty("pageNumber")
    private long pageNumber;

    @JsonbProperty("pageCount")
    private long pageCount;

    public PaginationData() {
        this.data = new ArrayList<>();
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    // Accesseurs & Mutateurs

    public List<T> getData() {
        return List.copyOf(data);
    }

    public void setData(List<T> data) {
        this.data.clear();
        this.data.addAll(data);
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
