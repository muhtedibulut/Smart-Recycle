package smart;

public class user {
	
	private int id;
	private String ad;
	private String soyad;
	private String tc;
	private String kart_id;
	
	public user (int id,String ad,String soyad,String tc,String kart_id)
	{
		this.id=id;
		this.ad = ad;
		this.soyad = soyad;
		this.tc = tc;
		this.kart_id = kart_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getSoyad() {
		return soyad;
	}

	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}

	public String getTc() {
		return tc;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	public String getKart_id() {
		return kart_id;
	}

	public void setKart_id(String kart_id) {
		this.kart_id = kart_id;
	}
}
