package it.vehicles.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetPageable implements Pageable {
	private int limit;
	private int offset;
	private String sortBy;
	private Sort sort;

	public OffsetPageable(int limit, int offset, String sortBy) {
		this.limit = limit;
		this.offset = offset;
		this.sortBy = sortBy;
		this.sort = new Sort(Sort.Direction.ASC, sortBy);
	}

	@Override
	public int getPageNumber() {
		return offset / limit;
	}

	@Override
	public int getPageSize() {
		return limit;
	}

	@Override
	public long getOffset() {
		return offset;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	@Override
	public Pageable next() {
		return new OffsetPageable(getPageSize(), (int) (getOffset() + getPageSize()), getSortBy());
	}

	public Pageable previous() {
		return hasPrevious() ? new OffsetPageable(getPageSize(), (int) (getOffset() - getPageSize()), getSortBy())
				: this;
	}

	@Override
	public Pageable previousOrFirst() {
		return hasPrevious() ? previous() : first();
	}

	@Override
	public Pageable first() {
		return new OffsetPageable(getPageSize(), 0, getSortBy());
	}

	@Override
	public boolean hasPrevious() {
		return offset > limit;
	}

	public String getSortBy() {
		return sortBy;
	}
}
