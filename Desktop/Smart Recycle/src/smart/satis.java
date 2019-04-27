package smart;

public class satis {
	
	private int id;
	private String kart_id;
	private String tarih;
	private String satilanUrunler;
	private int puan;
	
	public satis(int id,String kart_id,String tarih,String satilanUrunler,int puan)
	{
		this.id=id;
		this.kart_id = kart_id;
		this.tarih = tarih;
		this.satilanUrunler = satilanUrunler;
		this.puan=puan;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKart_id() {
		return kart_id;
	}
	public void setKart_id(String kart_id) {
		this.kart_id = kart_id;
	}
	public String getTarih() {
		return tarih;
	}
	public void setTarih(String tarih) {
		this.tarih = tarih;
	}
	public String getSatilanUrunler() {
		return satilanUrunler;
	}
	public void setSatilanUrunler(String satilanUrunler) {
		this.satilanUrunler = satilanUrunler;
	}
	public int getPuan() {
		return puan;
	}
	public void setPuan(int puan) {
		this.puan = puan;
	}

}
