package com.parrotanalytics.api.request.insightbuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class AssignedDashboards.
 */
@Deprecated
public class AssignedDashboards {

	/** The id. */
	private Integer id;

	/**
	 * Instantiates a new assigned dashboards.
	 */
	public AssignedDashboards() {
	}

	/**
	 * Instantiates a new assigned dashboards.
	 *
	 * @param id the id
	 */
	public AssignedDashboards(Integer id) {
		super();
		this.id = id;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssignedDashboards other = (AssignedDashboards) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
