package smart;

public class urun {
	
	private int id;
	private String urunAd;
	private String urunOzellik;
	private int urunPuan;
	
	public urun (int id,String urunAd,String urunOzellik,int urunPuan)
	{
		this.id=id;
		this.urunAd = urunAd;
		this.urunOzellik = urunOzellik;
		this.urunPuan = urunPuan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrunAd() {
		return urunAd;
	}

	public void setUrunAd(String urunAd) {
		this.urunAd = urunAd;
	}
	
	public String getUrunOzellik() {
		return urunOzellik;
	}

	public void setUrunOzellik(String urunOzellik) {
		this.urunOzellik = urunOzellik;
	}
	
	public int getUrunPuan() {
		return urunPuan;
	}

	public void setUrunPuan(int urunPuan) {
		this.urunPuan = urunPuan;
	}

}
