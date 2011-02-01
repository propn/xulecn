/**
 * 
 */
package org.leixu.iwap.dao;

import java.util.Date;

/**
 * @author Administrator
 * 
 */
public class BaseEntity {
	private Integer id;

	private String url;

	private Date inDate;

	private String site;

	private Date pubDate;

	public BaseEntity() {
	}

	public BaseEntity(String url, Date inDate, String site, Date pubDate) {
		this.url = url;
		this.inDate = inDate;
		this.site = site;
		this.pubDate = pubDate;
	}

	public BaseEntity(Integer id, String url, Date inDate, String site,
			Date pubDate) {
		this(url, inDate, site, pubDate);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

}
