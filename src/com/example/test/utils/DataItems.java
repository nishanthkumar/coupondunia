package com.example.test.utils;

public class DataItems {

	    private String title, thumbnailUrl, subtitle, locality, neighbouthoodName;
	    private String[] categoryName;
	    private int year, coupons;
	    private double distance;
	 
	    public DataItems() {
	    }
	 
	    public DataItems(String name, String thumbnailUrl, String locality,
	            String subtitle, int coupons, double distance ) {
	        this.title = name;
	        this.thumbnailUrl = thumbnailUrl;
	        this.setCoupons(coupons);
	        this.setLocality(locality);
	        this.setSubtitle(subtitle);
	        this.setDistance(distance);
	    }
	 
	    public String getTitle() {
	        return title;
	    }
	 
	    public void setTitle(String name) {
	        this.title = name;
	    }
	 
	    public String getThumbnailUrl() {
	        return thumbnailUrl;
	    }
	 
	    public void setThumbnailUrl(String thumbnailUrl) {
	        this.thumbnailUrl = thumbnailUrl;
	    }
	 
	    public int getYear() {
	        return year;
	    }
	 
	    public void setYear(int year) {
	        this.year = year;
	    }

		public String getLocality() {
			return locality;
		}

		public void setLocality(String locality) {
			this.locality = locality;
		}

		public String getSubtitle() {
			return subtitle;
		}

		public void setSubtitle(String subtitle) {
			this.subtitle = subtitle;
		}

		public int getCoupons() {
			return coupons;
		}

		public void setCoupons(int coupons) {
			this.coupons = coupons;
		}

		public double getDistance() {
			return distance;
		}

		public void setDistance(double distance) {
			this.distance = distance;
		}

		public String[] getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String[] categoryName) {
			this.categoryName = categoryName;
		}

		public String getNeighbouthoodName() {
			return neighbouthoodName;
		}

		public void setNeighbouthoodName(String neighbouthoodName) {
			this.neighbouthoodName = neighbouthoodName;
		}
		
}
