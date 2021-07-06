package br.com.elotech.project.service.utils;

public class Pageable {

	private Object content;
	private int numberOfElements;
	private int totalPages;
	private int pageNumber;
	private long totalElements;

	public Pageable() {
		// TODO Auto-generated constructor stub
	}

	public Pageable(Object content, int numberOfElements, int totalPages, int pageNumber, long totalElements) {
		this.content = content;
		this.numberOfElements = numberOfElements;
		this.totalPages = totalPages;
		this.pageNumber = pageNumber;
		this.totalElements = totalElements;
	}

	public Pageable(Object content, int numberOfElements, long totalElements) {
		this.content = content;
		this.numberOfElements = numberOfElements;
		this.totalElements = totalElements;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public Pageable build(int limit, int offset) {
		int totalPages = this.totalElements % limit == 0 ? (int) this.totalElements / limit : (int) (this.totalElements / limit) + 1;
		int pageNumber = offset / limit;

		this.setTotalPages((int) totalPages);
		this.setPageNumber(pageNumber);

		return this;
	}

}
