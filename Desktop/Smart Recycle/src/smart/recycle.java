package smart;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import com.fazecast.jSerialComm.SerialPort;


public class recycle extends JFrame {
	
	private Container container; //Eklenen componentlerin tutulduðu nesne
	private JPanel anaPanel,menuPanel,titlePanel,panel3; //Programda bulunan paneller
	private int height,width; //Ekranýn geniþlik ve yüksekliðinin tutulduðu deðiþkenler
	private int pagePositionX,pagePositionY; //Sayfalarýn baþlangýç pozisyonlarý
	private int pageWidth,pageHeigth; ////Sayfalarýn geniþlik ve yüksekliðinin tutulduðu deðiþkenler
	private Connection con; //Veri tabaný iþlmeleri
    private Statement st; //Veri tabaný iþlmeleri
    private ResultSet rs; //Veri tabaný iþlmeleri
    private JTable table; //Verilerin listelenmesi
    private DefaultTableModel model; //Verilerin listelenmesi
    private boolean menu_Satis,menu_Kisiler,menu_Urunler,menu_Istatistik; //Menulerin oluþturulup oluþturulup oluþturulmadýðýný kontrol eden deðiþkenler
	private String menu; //Programýn hangi menude olduðunu tutan deðiþken
    private String ID,ad,soyad,kartID,mevcutPuan,str_alinanUrunler,TC,urunAd,urunPuan,urunOzellik,portData,tableUrun,send,ilktarih,sontarih,tarihSatis,toplamSatis;//Programda kullanýlan deðiþkenler
	private JButton btnSatis,btnKisiler,btnUrunler,btnIstatistik; //Menudeki butonlar
	private JLabel title,projectName,logo; //Menude yer alan labeller ve logosu
	private JSeparator menuSeparetor; //Menude yer alan ayýrýcý
    private JComboBox<String> portList; //Satýþ ve kiþiler sayfasýndaki COMM listesi
    private JLabel labelCmm,labelAd,labelSoyad,labelKartID,labelMevcutPuan,labelAlinanUrunler,labelToplamPuan,labelTC; //Satýþ sayfasýndaki gerekli olan labeller
    private JTextField txtAd,txtSoyad,txtKartID,txtMevcutPuan,txtAlinanUrunler,txtToplamPuan,txtTC; //Satýþ sayfasýndaki gerekli olan textler
    private JTextArea alinanUrunler; //Satýþ sayfasýndaki alnýnan ürünler
    private JButton btnSatisSatis,btnSatisTemizle,btnSatisYenile,btnSatisAdet,btnSatisCikar;  //Satýþ sayfasýndaki gerekli olan butonlar
    private int tablePuan,toplamPuan; //Satýþ sayfasýndaki seçilen ürünlerin toplam puanýnýn saklandýðý deðiþken
    private boolean tableClick; //Tabloya týklanýp týklanmadýðýnýn kontrolünün yapýldýðý deðiþken
    private ArrayList<String> satisUrunList,satisPuanList; //Satýþ sayfasýnladkii seçilen ürünlerin tutulduðu array
    private SerialPort[] portNames; //Bilgisayara baðlý olan portlarýn tutulduðu deðiþken
    private SerialPort port; //Ardunio ile haberleþilecek olan port
    private boolean portControl; //Portun açýk olup olmadýðýnýn tutulduðunu deðiþken
    private Timer myTimer; //Portun hangi zaman aralýklarýnda dinleneceðinin tuttulduðu deðiþken
    private TimerTask gorev;  //Portun hangi zaman aralýklarýnda dinleneceðinin tuttulduðu deðiþken
    private JButton btnKisilerEkle,btnKisilerGuncelle,btnKisilerSil,btnKisilerArama,btnKisilerTemizle,btnKisilerYenile;//Kiþiler sayfasýndaki gerekli olan butonlar
    private JLabel labelUrunAd,labelUrunOzellik,labelUrunPuan; //Ürünler sayfasýndaki gerekli olan labeller
    private JTextField txtUrunAd,txtUrunOzellik,txtUrunPuan; //Ürünler sayfasýndaki gerekli olan textler
    private JButton btnUrunlerEkle,btnUrunlerGuncelle,btnUrunlerSil,btnUrunlerArama,btnUrunlerTemizle,btnUrunlerYenile;//Ürünler sayfasýndaki gerekli olan butonlar
    private JLabel labelilkTarih,labelsonTarih,labelTarihSatis,labelToplamSatis;//Ýstatistik sayfasýndaki gerekli olan labeller
    private JTextField txtilkTarih,txtsonTarih,txtTarihSatis,txtToplamSatis;//Ýstatistik sayfasýndaki gerekli olan textler
    private JButton btnIstatistikArama,btnIstatistikTemizle,btnIstatistikYenile;//Ýstatistik sayfasýndaki gerekli olan butonlar
    
	public recycle() throws SQLException
	{
		super("SMART RECYCLE"); //Pencerede çýkan isim
    	//Bilgisayarýn ekran yüksekilðini ve geniþliðini al
    	Toolkit tk = Toolkit.getDefaultToolkit();
    	
    	height = ((int) tk.getScreenSize().getHeight());
        width  = ((int) tk.getScreenSize().getWidth()) - 50;
        
        pagePositionX = (int)width / 8;
        pagePositionY = (int)width / 8;
        
        pageWidth = width - (width / 8);
        pageHeigth = height - (width / 8);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);//Ekrana boyut veriliyor
        this.setResizable(false); //Ekranýn küçültilmesini engelle sabit boyutlu
        
        container = getContentPane();
        container.removeAll();
        
        anaPanel = new JPanel(null); //Ana panel oluþturuluyor
        anaPanel.setBounds(0, 0, width, height);//Baþlangýç noktasý ve boyutlarý veriliyor
        anaPanel.setBackground(Color.WHITE);//Panele renk ver
        
        con = database.dbConnection();
        st = (Statement) con.createStatement();
        
        //Satýþ sayfasýndaki arraylerin oluþturulmasý
        satisUrunList = new ArrayList<String>();
        satisPuanList = new ArrayList<String>();
        
        myTimer=new Timer();//Portun hangi zaman aralýklarýnda dinlenmesi için Timer'ýn oluþturulmasý
        
        setup();
	}
	
	private void setup() //Programýndaki menulerin ve satýþ sayfasýnýn oluþrulduðu method
	{
		menu_Satis      = false;
		menu_Kisiler    = false;
		menu_Urunler    = false;
		menu_Istatistik = false;
		portControl = false;
		//menu = "satis";
		//menu = "kisiler";
		//menu = "urunler";
		//menu = "istatistik";
		
		panelSetup();
        menuLabelSetup();
        menuButonSetup();
        menuButonClick();
        satisSetup();
        //kisilerSetup();
	}

	private void panelSetup()//Programdaki panellerin oluþturulduðu method
	{
		menuPanel = new JPanel(null);//Menu panelini oluþtur
        menuPanel.setBounds(0, 0, (width/8),height );//Baþlangýç noktasýný ve boyutunu belirle
        menuPanel.setBackground(new Color(54,33,89));//Arka plan rengini belirle
        anaPanel.add(menuPanel);//Ana panele ekle
        
        titlePanel = new JPanel(null);//Menu panelini oluþtur
        titlePanel.setBounds((width/8), 0, width - (width/8),(width/8) );//Baþlangýç noktasýný ve boyutunu belirle
        titlePanel.setBackground(new Color(122,72,221));//Arka plan rengini belirle
        anaPanel.add(titlePanel);//Ana panele ekle
	}
	
	private void panelEdit()//Programdaki panellerin yeniden düzenlendiði method
	{
        anaPanel.add(menuPanel);//Menu panelini anapanele ekle
        
        anaPanel.add(titlePanel);//Tittle panelini anapanele ekle
	}

	private void menuLabelSetup()//Menudeki labellerin oluþturulduðu method
	{
		logo = new JLabel(new ImageIcon("image/icon.png"));//Logonun resmini belirtilen adresten al
        logo.setBounds(0, 0, (width/8), (width/8));//Baþlangýç noktasýný ve boyutunu belirle
		menuPanel.add(logo);//Menu paneline ekle
		
		title = new JLabel("AHMET KABAKLI ERKEK ÖÐRENCÝ YURDU");//Title yazý yaz 
        title.setBounds((width/8), 0, width - (width/8), (width/8) );//Baþlangýç noktasýný ve boyutunu belirle
        title.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));//Fontunu belirle
        title.setBackground(Color.WHITE);//Rengini belirle
        titlePanel.add(title);//Title paneline ekle
        
        projectName = new JLabel("SMART RECYCLE");//Programýn ismini yaz
        projectName.setBounds(0,(width/8),(width/8),50);//Baþlangýç noktasýný ve boyutunu belirle
        projectName.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 18));//Fontunu belirle
        projectName.setForeground(Color.WHITE);//Rengini belirle
        menuPanel.add(projectName);//Menu paneline ekle
        
        menuSeparetor = new JSeparator();//Ayýrýcýyý oluþtur
        menuSeparetor.setBounds(0,(width/8) + 50, (width/8),10);//Baþlangýç noktasýný ve boyutunu belirle
        menuPanel.add(menuSeparetor);//Menu paneline ekle
	}

	private void menuButonSetup()//Menu butonlarýnýn oluþturulduðu method
	{
		
		ImageIcon icon1 = new ImageIcon("image/icon1.png");//Satýþ resmini al
		btnSatis = new JButton("SATIÞ",icon1);//Butonunu oluþtur, yazý ve resmini belirle
		btnSatis.setBounds(0,250,(width/8),50);//Baþlangýç noktasýný ve boyutunu belirle
		//Arka planý opak(transparant) yap
		btnSatis.setOpaque(false);
		btnSatis.setContentAreaFilled(false);
		btnSatis.setBorderPainted(false);
		btnSatis.setForeground(Color.WHITE);
		btnSatis.setHorizontalAlignment(SwingConstants.LEFT);//Yazýyý sola kaydýrýr
        menuPanel.add(btnSatis);//Menu paneline ekle
        
        ImageIcon icon2 = new ImageIcon("image/icon2.png");//Kiþiler resmini al
        btnKisiler = new JButton("KÝÞÝLER",icon2);//Butonunu oluþtur, yazý ve resmini belirle
        btnKisiler.setBounds(0,300,(width/8),50);//Baþlangýç noktasýný ve boyutunu belirle
        //Arka planý opak(transparant) yap
        btnKisiler.setOpaque(false);
        btnKisiler.setContentAreaFilled(false);
        btnKisiler.setBorderPainted(false);
        btnKisiler.setForeground(Color.WHITE);
        btnKisiler.setHorizontalAlignment(SwingConstants.LEFT);//Yazýyý sola kaydýrýr
        menuPanel.add(btnKisiler);//Menu paneline ekle
        
        ImageIcon icon3 = new ImageIcon("image/icon3.png");//Ürünler resmini al
        btnUrunler = new JButton("ÜRÜNLER",icon3);//Butonunu oluþtur, yazý ve resmini belirle
        btnUrunler.setBounds(0,350,(width/8),50);//Baþlangýç noktasýný ve boyutunu belirle
        //Arka planý opak(transparant) yap
        btnUrunler.setOpaque(false);
        btnUrunler.setContentAreaFilled(false);
        btnUrunler.setBorderPainted(false);
        btnUrunler.setForeground(Color.WHITE);
        btnUrunler.setHorizontalAlignment(SwingConstants.LEFT);//Yazýyý sola kaydýrýr
        menuPanel.add(btnUrunler);//Menu paneline ekle
        
        ImageIcon icon4 = new ImageIcon("image/icon4.png");//Ýstatistik resmini al
        btnIstatistik = new JButton("ÝSTATÝSTÝK",icon4);;//Butonunu oluþtur, yazý ve resmini belirle
        btnIstatistik.setBounds(0,400,(width/8),50);//Baþlangýç noktasýný ve boyutunu belirle
        //Arka planý opak(transparant) yap
        btnIstatistik.setOpaque(false);
        btnIstatistik.setContentAreaFilled(false);
        btnIstatistik.setBorderPainted(false);
        btnIstatistik.setForeground(Color.WHITE);
        btnIstatistik.setHorizontalAlignment(SwingConstants.LEFT);//Yazýyý sola kaydýrýr
        menuPanel.add(btnIstatistik);//Menu paneline ekle
	}

	private void menuButonClick()//Menu butonlarýnýn basýldýðýnda çalýþacak olan method
	{
		btnSatis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//menu = "satis";
				if(menu_Satis == false)
            	{
            		satisSetup();
            	}
            	else
            	{
            		satisEdit();
            	}
			}
		});
		
		btnKisiler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//menu = "kisiler";
				if(menu_Kisiler == false)
            	{
            		kisilerSetup();
            	}
            	else
            	{
            		kisilerEdit();
            	}
			}
		});
		
		btnUrunler.addActionListener(new  ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//menu = "urunler";
				if(menu_Urunler == false)
            	{
            		urunlerSetup();
            	}
            	else
            	{
            		urunlerEdit();
            	}
				
			}
		});
		
		btnIstatistik.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//menu = "istatistik";
				if(menu_Istatistik == false)
            	{
            		istatistikSetup();
            		menu_Istatistik = true;
            	}
            	else
            	{
    				istatistikSetup();
            	}
			}
		});
	}
	
	private void tableCreate(Object[] columns)//Tablonun oluþturulduðu method
    {
    	table = new JTable(); 
		
    	model = new DefaultTableModel()//Tablo üstünde deðiþiklik yapmayý kapat
    	{
		   @Override
		   public boolean isCellEditable(int row, int column)
		   {
		       return false;
		   }
		};
        model.setColumnIdentifiers(columns);
        
        table.setModel(model);
        
		if(columns.length == 4)//Ürünler için tablo boyutlarý
		{
			table.getColumnModel().getColumn(0).setPreferredWidth(((pageWidth / 2) / 20) * 2);
			table.getColumnModel().getColumn(1).setPreferredWidth(((pageWidth / 2) / 20) * 14);
			table.getColumnModel().getColumn(2).setPreferredWidth(((pageWidth / 2) / 20) * 2);
			table.getColumnModel().getColumn(2).setPreferredWidth(((pageWidth / 2) / 20) * 2);
		}
        
        //Tablonun fontlarý
        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.black);
		//table.setBackground(Color.ORANGE);
        //table.setForeground(Color.WHITE);
        //table.setBackground(Color.WHITE);
        //table.setForeground(Color.BLACK);
        Font font = new Font("",1,22);
        table.setFont(font);
        table.setRowHeight(30);
        
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(pagePositionX, pagePositionY, pageWidth / 2, pageHeigth);
        
        anaPanel.add(pane);
    }

	private void executeSQlQuery(String query, String message,int menu)//Gelen sql sorgusunu çalýþtýr
	   {
	       try
	       {
	           if((st.executeUpdate(query)) == 1)
	           {
	        	   if(menu == 1)
	        	   {
	        		   satisEdit();
	        	   }
	        	   else if(menu == 2)
	        	   {
	        		   kisilerEdit();
	        	   }
	        	   else if(menu == 3)
	        	   {
	        		   urunlerEdit();
	        	   }
	        	   else if(menu == 4)
	        	   {
	        		   istatistikEdit();
	        	   }
	        	   JOptionPane.showMessageDialog(null, message+" ÝÞLEMÝ BAÞARIYLA GERÇEKLEÞTÝ","",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("image/ok.png"));
	           }
	           else
	           {
	               JOptionPane.showMessageDialog(null, message + " ÝÞLEMÝ GERÇEKLEÞTÝRÝLEMEDÝ","HATA",JOptionPane.ERROR_MESSAGE);
	           }
	       }
	       catch(Exception ex)
	       {
	           JOptionPane.showMessageDialog(null, "ÝÞLEM GERÇEKLEÞTÝRÝLEMEDÝ","HATA",JOptionPane.ERROR_MESSAGE);
	       }
	   }
	
	private boolean var_mi(String query) //Gelen sql sorgusu veri tabanýnda var mý?
	{
	    try
	    {
	        rs = st.executeQuery(query);
	        if(rs.next())
	        {
	            return true;
	        }
	        else
	        {
	     	   return false;
	        }
	    }
	    catch(Exception ex)
	    {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "VERÝ TABANI HATASI","HATA",JOptionPane.ERROR_MESSAGE);
	    }
			return false;
	}
	
	private void satisSetup()//Satiþ sayfasýnýn oluþturulduðu method
	{
		//Tüm nesneler sil
		container = getContentPane();
        container.removeAll();
        anaPanel.removeAll();
        anaPanel.revalidate();
        anaPanel.repaint();
        
        menu = "satis";
        
		anaPanel.setBackground(Color.WHITE); //Sayfanýn rengini belirliyor
		
		//Port açýksa kapat
		if(portControl == true)
    	{
    		myTimer.cancel();
    		port.closePort();
    		portControl = false;
    	}
        
        panelEdit();  //Panelleri düzenle
        satisLabelSetup();
		satisTxtSetup();
		satisButonSetup();
		satisBtnClick();
		satisValueSetup();
		satisShow();
		satisTableClick();
		//Port kapalýysa aç
		if(portControl == false)
		{
			readKart();
		}
		
		container.add(anaPanel);
        invalidate();
        repaint();
        menu_Satis = true;//Satýþ sayfasý oluþturuldu
	}
	
	private void kisilerSetup()//Kiþiler sayfasýnýn oluþturulduðu method
	{
		//Tüm nesneler sil
		container = getContentPane();
        container.removeAll();
        anaPanel.removeAll();
        anaPanel.revalidate();
        anaPanel.repaint();
        
		menu = "kisiler";
        
		anaPanel.setBackground(Color.ORANGE); //Sayfanýn rengini belirliyor
        
		//Port açýksa kapat
		if(portControl == true)
    	{
    		myTimer.cancel();
    		port.closePort();
    		portControl = false;
    	}
				
        //Panelleri düzenle
        panelEdit();
        kisilerLabelSetup();
		kisilerTxtSetup();
		kisilerButonSetup();
		kisilerBtnClick();
		kisilerValueSetup();
		kisilerShow("SELECT * FROM  `user` ORDER BY ad ASC");
		kisilerTableClick();
		if(portControl == false)
		{
			readKart();
		}
		
		container.add(anaPanel);
        invalidate();
        repaint();
        menu_Kisiler = true;//Kiþiler sayfasý oluþturuldu
	}
	
	private void urunlerSetup()//Ürünler sayfasýnýn oluþturulduðu method
	{
		//Tüm nesneler sil
		container = getContentPane();
        container.removeAll();
        anaPanel.removeAll();
        anaPanel.revalidate();
        anaPanel.repaint();
        
		menu = "urunler";
        
		anaPanel.setBackground(Color.GREEN); //Sayfanýn rengini belirliyor
        
		//Port açýksa kapat
		if(portControl == true)
    	{
    		myTimer.cancel();
    		port.closePort();
    		portControl = false;
    	}
		
        //Panelleri düzenle
        panelEdit();
        urunlerLabelSetup();
		urunlerTxtSetup();
		urunlerButonSetup();
		urunlerBtnClick();
		urunlerValueSetup();
		urunlerShow("SELECT * FROM  `urunler` ORDER BY urunAd ASC");
		urunlerTableClick();
		
		container.add(anaPanel);
        invalidate();
        repaint();
        menu_Urunler = true;//Urunler sayfasý oluþturuldu
	}
	
	private void istatistikSetup()//Ýstatistik sayfasýnýn oluþturulduðu method
	{
		//Tüm nesneler sil
		container = getContentPane();
        container.removeAll();
        anaPanel.removeAll();
        anaPanel.revalidate();
        anaPanel.repaint();
        
        menu = "istatistik";
        
		anaPanel.setBackground(Color.YELLOW); //Sayfanýn rengini belirliyor
        
		//Port açýksa kapat
		if(portControl == true)
    	{
    		myTimer.cancel();
    		port.closePort();
    		portControl = false;
    	}
        //Panelleri düzenle
        panelEdit();
        istatistikLabelSetup();
        istatistikTxtSetup();
        istatistikButonSetup();
        istatistikBtnClick();
        istatistikValueSetup();
        istatistikShow("SELECT * FROM  `satis` ORDER BY tarih ASC");
		
		container.add(anaPanel);
        invalidate();
        repaint();
        menu_Istatistik = true;//Ýstatistik sayfasý oluþturuldu
	}
	
	private void satisValueSetup()//Satýþ sayfasýndaki deðiþkenlerin sýfýrlandýðý method
	{
		ID = "";
		ad = "";
		soyad = "";
		kartID = "";
		mevcutPuan = "";
		toplamPuan = 0;
		str_alinanUrunler = "";
		tableClick = false;
		satisUrunList.removeAll(satisUrunList);
		satisPuanList.removeAll(satisPuanList);
		
		txtAd.setText("");
		txtSoyad.setText("");
		txtKartID.setText("");
		txtMevcutPuan.setText("");
    	alinanUrunler.setText("");
		txtToplamPuan.setText("");
	}
	
	private void satisLabelSetup()//Satýþ sayfasýndaki labellarýn oluþturulduðu method
	{
		labelCmm = new JLabel("COMM PORT :"); //Label oluþtur ve yazý yaz
		labelCmm.setBounds(pagePositionX + (pageWidth / 2) + 10 , pagePositionY + (pageHeigth / 2), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelCmm);	//Ana panele ekle
		
		portList = new JComboBox<String>(); //ComboBox'u oluþtur
		portList.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 8)); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(portList); //Ana panele ekle
		
		labelAd = new JLabel("AD :"); //Label oluþtur ve yazý yaz
		labelAd.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelAd);	//Ana panele ekle
		
		labelSoyad = new JLabel("SOYAD :"); //Label oluþtur ve yazý yaz
		labelSoyad.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelSoyad);	//Ana panele ekle
		
		labelKartID = new JLabel("KART ID :"); //Label oluþtur ve yazý yaz
		labelKartID.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelKartID);	//Ana panele ekle
		
		labelMevcutPuan = new JLabel("MEVCUT PUAN :"); //Label oluþtur ve yazý yaz
		labelMevcutPuan.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelMevcutPuan);	//Ana panele ekle
		
		labelAlinanUrunler = new JLabel("ALINAN ÜRÜNLER"); //Label oluþtur ve yazý yaz
		labelAlinanUrunler.setBounds(pagePositionX + (pageWidth /2) + (pageWidth /5), pagePositionY, ((pageWidth /2)/ 3), ((pageHeigth / 2) / 6)); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelAlinanUrunler); //Ana panele ekle
		
		labelToplamPuan = new JLabel("TOPLAM PUAN :"); //Label oluþtur ve yazý yaz
		labelToplamPuan.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5) * 2) + 10,pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelToplamPuan); //Ana panele ekle
	}
	
	private void satisTxtSetup()//Satýþ sayfasýndaki textlerin oluþturulduðu method
	{
		txtAd = new JTextField(""); //Text oluþtur
		txtAd.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
		txtAd.setEditable(false);//Text üzerinde deðiþiklik yapabilmeyi kapatýyor
		anaPanel.add(txtAd); //Ana panele ekle
		
		txtSoyad = new JTextField(); //Text oluþtur
		txtSoyad.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
		txtSoyad.setEditable(false);//Text üzerinde deðiþiklik yapabilmeyi kapatýyor
		anaPanel.add(txtSoyad); //Ana panele ekle
		
		txtKartID = new JTextField(); //Text oluþtur
		txtKartID.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
		txtKartID.setEditable(false);//Text üzerinde deðiþiklik yapabilmeyi kapatýyor
		anaPanel.add(txtKartID); //Ana panele ekle
		
		txtMevcutPuan = new JTextField(); //Text oluþtur
		txtMevcutPuan.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
		txtMevcutPuan.setEditable(false);//Text üzerinde deðiþiklik yapabilmeyi kapatýyor
		txtMevcutPuan.setForeground(Color.RED); //Text'in yazýsýný kýrmýzý renk yap
		anaPanel.add(txtMevcutPuan); //Ana panele ekle
		
		alinanUrunler = new JTextArea (); //TextArea oluþtur
        JScrollPane areaPane = new JScrollPane(alinanUrunler); //Baþlangýç noktasýný ve boyutunu belirle
		areaPane.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10), pagePositionY + ((pageHeigth / 2) / 4), (((pageWidth / 2) / 7) * 5), (((pageHeigth / 2) / 10) * 5));
		alinanUrunler.setEditable(false);//Text üzerinde deðiþiklik yapabilmeyi kapatýyor
		anaPanel.add(areaPane); //Ana panele ekle
		
		txtToplamPuan = new JTextField(); //Text oluþtur
		txtToplamPuan.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 3) - 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
		txtToplamPuan.setEditable(false);//Text üzerinde deðiþiklik yapabilmeyi kapatýyor
		txtToplamPuan.setForeground(Color.RED); //Text'in yazýsýný kýrmýzý renk yap
		anaPanel.add(txtToplamPuan); //Ana panele ekle
	}
	
	private void satisButonSetup()//Kiþiler sayfasýndaki butonlarýn oluþturulduðu method
	{
		ImageIcon clear = new ImageIcon("image/clear.png"); //Resmi bulunduðu konumdan al
		btnSatisTemizle = new JButton("TEMÝZLE");  //Butonu oluþtur ve resim ekle
		btnSatisTemizle.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10) + (((((pageWidth / 2) / 7) * 5)/ 14) * 10), pagePositionY + ((pageHeigth / 2) / 4) + (((pageHeigth / 2) / 10) * 5) + ((pageHeigth / 2) / 25),(((((pageWidth / 2) / 7) * 5)/ 14) * 4), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		/*//Arka planý ayarla
		btnSatisTemizle.setBackground(new Color(122,72,221));
		btnSatisTemizle.setForeground(Color.WHITE);
		btnSatisTemizle.setHorizontalAlignment(SwingConstants.LEFT);//Yazýyý sola kaydýrýr*/
		anaPanel.add(btnSatisTemizle); //Ana panele ekle
        
		//ImageIcon ekle = new ImageIcon("image/ekle.jpg"); //Resmi bulunduðu konumdan al
		btnSatisAdet = new JButton("EKLE");  //Butonu oluþtur ve resim ekle
		//btnSatisAdet.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		btnSatisAdet.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10) + (((((pageWidth / 2) / 7) * 5)/ 14) * 5), pagePositionY + ((pageHeigth / 2) / 4) + (((pageHeigth / 2) / 10) * 5) + ((pageHeigth / 2) / 25),(((((pageWidth / 2) / 7) * 5)/ 14) * 4), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnSatisAdet); //Ana panele ekle
        
		//ImageIcon ekle = new ImageIcon("image/ekle.jpg"); //Resmi bulunduðu konumdan al
		btnSatisCikar = new JButton("ÇIKAR");  //Butonu oluþtur ve resim ekle
		//btnSatisCikar.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		btnSatisCikar.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10), pagePositionY + ((pageHeigth / 2) / 4) + (((pageHeigth / 2) / 10) * 5) + ((pageHeigth / 2) / 25),(((((pageWidth / 2) / 7) * 5)/ 14) * 4), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnSatisCikar); //Ana panele ekle
        
        ImageIcon satis = new ImageIcon("image/satis.png"); //Resmi bulunduðu konumdan al
        btnSatisSatis = new JButton("SATIÞ", satis);  //Butonu oluþtur ve resim ekle
        btnSatisSatis.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
        //Arka planý ayarla
        btnSatisSatis.setBackground(new Color(122,72,221));
        btnSatisSatis.setForeground(Color.WHITE);
        btnSatisSatis.setHorizontalAlignment(SwingConstants.LEFT);//Yazýyý sola kaydýrýr
        anaPanel.add(btnSatisSatis); //Ana panele ekle
        
        btnSatisYenile = new JButton("YENÝLE");  //Butonu oluþtur ve resim ekle
        btnSatisYenile.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnSatisYenile); //Ana panele ekle
        
        /*ImageIcon yenile = new ImageIcon("image/refresh.png"); //Resmi bulunduðu konumdan al
        btnSatisYenile = new JButton(yenile);  //Butonu oluþtur ve resim ekle
        btnSatisYenile.setBounds(1220,pagePositionY,((pageWidth / 2) / 7),((pageWidth / 2) / 7)); //Baþlangýç noktasýný ve boyutunu belirle
        //Arka planý opak(transparant) yap
        btnSatisYenile.setOpaque(false);
        btnSatisYenile.setContentAreaFilled(false);
        btnSatisYenile.setBorderPainted(false);
        btnSatisYenile.setForeground(Color.WHITE);
        btnSatisYenile.setBackground(Color.GRAY);
        anaPanel.add(btnSatisYenile); //Ana panele ekle*/
	}
	
	private void satisBtnClick()  //Satýþ sayfasýndaki butonlara bastýðýnda çalýþcak olan method
	{
		btnSatisYenile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				satisEdit();
			}
		});
		
		btnSatisTemizle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				satisValueSetup();
			}
		});
		
		btnSatisAdet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//Seçili ürün yok ise
				if(satisUrunList.size() == 0)
				{
					JOptionPane.showMessageDialog(null, "LÜTFEN ÜRÜN SEÇÝNÝZ","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					satisUrunList.add(satisUrunList.get(satisUrunList.size() - 1));//tableUrun);
					satisPuanList.add("" + satisPuanList.get(satisPuanList.size() - 1));//tablePuan);
					satisListShow();
				}
			}
		});
		
		btnSatisCikar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//Seçili ürün yok ise
				if(satisUrunList.size() == 0)
				{
					JOptionPane.showMessageDialog(null, "LÜTFEN ÜRÜN SEÇÝNÝZ","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					satisUrunList.remove(satisUrunList.size() - 1);
					satisPuanList.remove(satisPuanList.size() - 1);
					satisListShow();
				}
			}
		});
		
		btnSatisSatis.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(txtKartID.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "LÜTFEN KARTI OKUTUNUZ","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else if(alinanUrunler.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "LÜTFEN ÜRÜN ALINIZ","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else if(txtAd.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "BU KARTA KAYITLI BÝR KÝÞÝ BULUNAMAMIÞTIR","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else if(Integer.parseInt(txtToplamPuan.getText()) > Integer.parseInt(txtMevcutPuan.getText()))
				{
					JOptionPane.showMessageDialog(null, "BAKÝYE YETERSÝZ","",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					send = ":"  + (Integer.parseInt(txtMevcutPuan.getText()) -  Integer.parseInt(txtToplamPuan.getText()))+ ".";
					sendData(send);
				}
			}
		});
	}
	
	private void satisShow()  //Satýþ ve ürünler sayfasýndaki tabloda verilerin gösterildiði method
	{
		Object[] columns = {"SIRA","ÜRÜN ADI","GRAMAJ/LÝTRE","PUAN"};
		tableCreate(columns);
		ArrayList<urun> list = getUrunlerList("SELECT * FROM  `urunler` ORDER BY urunAd ASC");
		Object[] row = new Object[4];
		for(int i = 0; i < list.size(); i++)
		{
			row[0] = i + 1;
			row[1] = list.get(i).getUrunAd();
			row[2] = list.get(i).getUrunOzellik();
	        row[3] = list.get(i).getUrunPuan();
	        model.addRow(row);
		}
	}
	
	private ArrayList<urun> getUrunlerList(String query)//Satýþ sayfasýndaki verilerin getirildiði method
	{
	   ArrayList<urun> urunList = new ArrayList<urun>();
       
       try 
       {
           rs = st.executeQuery(query);
           urun urun;
           while(rs.next())
           {
        	   urun = new urun(rs.getInt("id"),rs.getString("urunAd"),rs.getString("urunOzellik"),rs.getInt("puan"));
        	   urunList.add(urun);
           }
       } 
      catch (Exception e)
       {
           e.printStackTrace();
       }
       return urunList;
	}
	
	private void satisTableClick()  //Satýþ sayfasýndaki tabloya basýldýðýnda çalýþsacak olan method
	{
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event)
	        {
	        	//Tabloda veri var mý
	        	if(table.getRowCount() > 0)
	        	{
	        		tablePuan = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 3).toString());
	        		tableUrun = "1 adet  X  " + table.getValueAt(table.getSelectedRow(), 1).toString() + "   " + table.getValueAt(table.getSelectedRow(), 1).toString() + "    " + tablePuan;
	        		
		        	if(tableClick == false)
		        	{
		        		tableClick = true;
		        	}
		        	else
		        	{
		        		tableClick = false;
		        		satisUrunList.add(tableUrun);
		        		satisPuanList.add("" + tablePuan);
		        		satisListShow();
		        	}
	        	}
	        }
	        });
	}
	
	private void satisListShow()  //Satýþ sayfasýnda alýnan ürünlerin listelendiðini gösteren method
	{
		str_alinanUrunler = "";
		toplamPuan = 0;
		for(int i=0; i<satisUrunList.size(); i++)
		{
			str_alinanUrunler += satisUrunList.get(i) + "\n";
			toplamPuan += Integer.parseInt(satisPuanList.get(i));
		}
		alinanUrunler.setText(str_alinanUrunler);
    	txtToplamPuan.setText("" + toplamPuan);
	}
	
	private void kisilerValueSetup()  //Kiþiler sayfasýndaki deðiþkenlerin sýfýrlandýðý method
	{
		txtAd.setText("");
		txtSoyad.setText("");
		txtTC.setText("");
		txtKartID.setText("");
		
		ID = "";
		ad = "";
		soyad = "";
		TC = "";
		kartID = "";
	}
	
	private void kisilerLabelSetup()//Kiþiler sayfasýndaki labellarýn oluþturulduðu method
	{
		labelCmm = new JLabel("COMM PORT :"); //Label oluþtur ve yazý yaz
		labelCmm.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY, ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelCmm);	//Ana panele ekle
		
		portList = new JComboBox<String>(); //ComboBox'u oluþtur
		portList.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 8));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(portList);//Ana panele ekle
		
		labelAd = new JLabel("AD :");//Label oluþtur ve yazý yaz
		labelAd.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelAd);//Ana panele ekle
		
		labelSoyad = new JLabel("SOYAD :");//Label oluþtur ve yazý yaz
		labelSoyad.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelSoyad);//Ana panele ekle

		labelTC = new JLabel("TC :");//Label oluþtur ve yazý yaz
		labelTC.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelTC);//Ana panele ekle
		
		labelKartID = new JLabel("KART NO :");//Label oluþtur ve yazý yaz
		labelKartID.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelKartID);//Ana panele ekle 
	}
	
	private void kisilerTxtSetup()//Kiþiler sayfasýndaki textlerin oluþturulduðu method
	{
		txtAd = new JTextField();//Text oluþtur
		txtAd.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtAd);//Ana panele ekle
		
		txtSoyad = new JTextField();//Text oluþtur
		txtSoyad.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtSoyad);//Ana panele ekle
		
		txtTC = new JTextField();//Text oluþtur
		txtTC.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtTC);//Ana panele ekle
		
		txtKartID = new JTextField();//Text oluþtur
		txtKartID.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 4), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		txtKartID.setEditable(false);//Text üzerinde deðiþiklik yapabilmeyi kapatýyor
		anaPanel.add(txtKartID);//Ana panele ekle
	}
	
	private void kisilerButonSetup()//Kiþiler sayfasýndaki butonlarýn oluþturulduðu method
	{
		//ImageIcon ekle = new ImageIcon("image/ekle.jpg");//Resmi bulunduðu konumdan al
		//butonEkle=new JButton(ekle); 
		btnKisilerEkle=new JButton("KAYDET");  //Butonu oluþtur ve resim ekle
		btnKisilerEkle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnKisilerEkle);//Ana panele ekle
        
        //ImageIcon guncelle = new ImageIcon("image/guncelle.jpg");//Resmi bulunduðu konumdan al
		//butonGuncelle =new JButton(guncelle);
        btnKisilerGuncelle =new JButton("GÜNCELLE");//Butonu oluþtur ve resim ekle
        btnKisilerGuncelle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnKisilerGuncelle);//Ana panele ekle
        
        
        //ImageIcon sil = new ImageIcon("image/sil.jpg");//Resmi bulunduðu konumdan al
		//butonSil=new JButton(sil);
        btnKisilerSil=new JButton("SÝL");//Butonu oluþtur ve resim ekle
        btnKisilerSil.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnKisilerSil);//Ana panele ekle
        
        //ImageIcon arama = new ImageIcon("image/arama.jpg");//Resmi bulunduðu konumdan al
        //butonArama=new JButton(arama);
        btnKisilerArama=new JButton("ARAMA");//Butonu oluþtur ve resim ekle
        btnKisilerArama.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 5),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnKisilerArama);//Ana panele ekle
        
        //ImageIcon guncelle = new ImageIcon("image/guncelle.jpg");//Resmi bulunduðu konumdan al
		//butonGuncelle =new JButton(guncelle);
        btnKisilerYenile =new JButton("YENÝLE");  //Butonu oluþtur ve resim ekle
        btnKisilerYenile.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnKisilerYenile);//Ana panele ekle
        
        //ImageIcon guncelle = new ImageIcon("image/guncelle.jpg");//Resmi bulunduðu konumdan al
		//butonGuncelle =new JButton(guncelle);
        btnKisilerTemizle =new JButton("TEMÝZLE");//Butonu oluþtur ve resim ekle
        btnKisilerTemizle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnKisilerTemizle);//Ana panele ekle
	}
	
	private void kisilerBtnClick()//Kiþiler sayfasýndaki butonlara bastýðýnda çalýþcak olan method
	{
		btnKisilerEkle.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(txtAd.getText().isEmpty() || txtSoyad.getText().isEmpty() || txtTC.getText().isEmpty() || txtKartID.getText().isEmpty())
	        	{
	        		JOptionPane.showMessageDialog(null, "TÜM ALANLARIN DOLDURULMASI VE KARTIN OKUTULMASI GEREKÝR","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}
				else if(txtAd.getText().trim().equals("") || txtSoyad.getText().trim().equals("") || txtTC.getText().trim().equals("") || txtKartID.getText().trim().equals(""))
	        	{
	        		JOptionPane.showMessageDialog(null, "HÝÇBÝR ALAN SPACE TUÞU ÝLE DOLDURULAMAZ","",JOptionPane.ERROR_MESSAGE);
	        	}
	        	/*else if(!txtTC.getText().trim().matches("[0-9]+") /*|| txtTC.getText().length() != 11*+/|| Integer.parseInt(txtTC.getText().trim()) == 0)
	        	{
	        		JOptionPane.showMessageDialog(null, "GEÇERSÝZ TC KÝMLÝK NO");
	        	}*/
	        	else if(var_mi("SELECT * FROM `user` WHERE tc LIKE '" + txtTC.getText().trim() + "'"))
	        	{
	        		JOptionPane.showMessageDialog(null, "TC KÝMLÝK NUMARASI KULLANILMAKTA","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}
	        	else if(var_mi("SELECT * FROM `user` WHERE kart_id LIKE '" + txtKartID.getText().trim() + "'"))
	        	{
	        		JOptionPane.showMessageDialog(null, "BU KART BAÞKASI ADINA KULLANILMAKTA","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}
	        	else
	        	{
	        		send =  txtTC.getText().trim() + ":" + "0" + "." ;
	        		sendData(send);
	        	}
			}
		});
		
		btnKisilerGuncelle.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				/*if(ID.equals(""))
	        	{
	        		JOptionPane.showMessageDialog(null, "LÜTFEN BÝLGÝLERÝ GÜNCELLENECEK KÝÞÝYÝ TABLODA SEÇÝNÝZ","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}*/
	        	if(txtAd.getText().isEmpty() || txtSoyad.getText().isEmpty() || txtTC.getText().isEmpty() || txtKartID.getText().isEmpty())
	        	{
	        		JOptionPane.showMessageDialog(null, "LÜTFEN BÝLGÝLERÝ GÜNCELLENECEK KÝÞÝNÝN KARTINI OKUTUNUZ VE TÜM ALANLARI DOLDURUNUZ","UYARI",JOptionPane.WARNING_MESSAGE);
	        		//JOptionPane.showMessageDialog(null, "TÜM ALANLARIN DOLDURULMASI GEREKÝR","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}
	        	else if(txtAd.getText().trim().equals("") || txtSoyad.getText().trim().equals("") || txtTC.getText().trim().equals("") || txtKartID.getText().trim().equals(""))
	        	{
	        		JOptionPane.showMessageDialog(null, "HÝÇBÝR ALAN SPACE TUÞU ÝLE DOLDURULAMAZ","",JOptionPane.ERROR_MESSAGE);
	        	}
	        	/*else if(!txtTC.getText().trim().matches("[0-9]+") /*|| txtTC.getText().length() != 11*+/|| Integer.parseInt(txtTC.getText().trim()) == 0)
	        	{
	        		JOptionPane.showMessageDialog(null, "GEÇERSÝZ TC KÝMLÝK NO");
	        	}*/
	        	else
	        	{
	        		menu = "kisiler_guncelle";
	        		send =  txtTC.getText().trim() + ":" + mevcutPuan + "." ;
	        		System.out.println(send);
	        		sendData(send);
	        		//String query = "UPDATE `user` SET `ad` = '"+txtAd.getText().trim()+"', `soyad` = '"+txtSoyad.getText().trim()+"', `tc` = "+txtTC.getText().trim()+", `kart_id` = '"+txtKartID.getText().trim()+"' WHERE `id` = "+ID;
	            	//executeSQlQuery(query, "GÜNCELLEME",2);
	        	}
			}
		});
		
		btnKisilerSil.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(TC.equals(""))
	        	{
	        		JOptionPane.showMessageDialog(null, "SÝLÝNECEK KÝÞÝYÝ SEÇÝNÝZ","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}
	        	else if(!var_mi("SELECT * FROM `user` WHERE tc LIKE '" + txtTC.getText() + "'"))
	        	{
	        		JOptionPane.showMessageDialog(null, "BU TC KÝMLÝK NO'YA SAHÝP KÝÞÝ BULUNAMADI","",JOptionPane.ERROR_MESSAGE);
	        	}
	        	else
	        	{
	            	int selected = JOptionPane.showConfirmDialog(null, "BU KÝÞÝYÝ SÝLMEK ÝSTEDÝÐÝNÝZE EMÝN MÝSÝNÝZ?","SÝLME",JOptionPane.YES_NO_OPTION);
					if(selected == 0)
					{
		        		String query = "DELETE FROM `user` WHERE tc =" + TC;
		            	executeSQlQuery(query, "SÝLME",2);
					}
	        	}
			}
		});
		
		btnKisilerArama.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(txtAd.getText().isEmpty() && txtSoyad.getText().isEmpty() && txtTC.getText().isEmpty() && txtKartID.getText().isEmpty())
	        	{
	        		JOptionPane.showMessageDialog(null, "ARAMAK ÝSTEDÝÐÝNÝZ KÝÞÝNÝN BÝLGÝLERÝNÝ GÝRÝNÝZ","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}
				else
				{
					search();
				}
				
			}
		});
		
		btnKisilerYenile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				kisilerEdit();
			}
		});
		
		btnKisilerTemizle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				kisilerValueSetup();
			}
		});
	}
	
	private void kisilerShow(String query)//Kiþiler sayfasýndaki tabloda verilerin gösterildiði method
	{
		Object[] columns = {"SIRA","ÝSÝM","SOYÝSÝM","TC","KART ID"};
		tableCreate(columns);
		ArrayList<user> list = getUsersList(query);
        Object[] row = new Object[5];
        for(int i = 0; i < list.size(); i++)
        {
            row[0] = i + 1;
            row[1] = list.get(i).getAd();
            row[2] = list.get(i).getSoyad();
            row[3] = list.get(i).getTc();
            row[4] = list.get(i).getKart_id();
           
            model.addRow(row);
        }
	}
	
	private ArrayList<user> getUsersList(String query)//Kiþiler sayfasýndaki verilerin getirildiði method
    {
        ArrayList<user> usersList = new ArrayList<user>();
        try 
        {
            rs = st.executeQuery(query);
            user user;
            while(rs.next())
            {
        	    user = new user(rs.getInt("id"),rs.getString("ad"),rs.getString("soyad"),rs.getString("tc"),rs.getString("kart_id"));
                usersList.add(user);
            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return usersList;
    }
	
	private void kisilerTableClick()//Satýþ sayfasýndaki tabloya basýldýðýnda çalýþsacak olan method
	{
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event)
	        {
	        	//Tabloda veri var mý
	        	if(table.getRowCount() > 0)
	        	{
	        		ID = table.getValueAt(table.getSelectedRow(), 0).toString();
	            	ad = table.getValueAt(table.getSelectedRow(), 1).toString();
	            	soyad = table.getValueAt(table.getSelectedRow(), 2).toString();
	            	TC = table.getValueAt(table.getSelectedRow(), 3).toString();
	            	//kartID = table.getValueAt(table.getSelectedRow(), 4).toString();
	            	
	            	txtAd.setText(ad);
	            	txtSoyad.setText(soyad);
	            	txtTC.setText(TC);
	            	//txtKartID.setText(kartID);
	        	}
	        	else
	        	{
	        		
	        	}
	        }
	        });
	}

	private void urunlerValueSetup()//Urunler sayfasýndaki deðiþkenlerin sýfýrlandýðý method
	{
		txtUrunAd.setText("");
		txtUrunOzellik.setText("");
		txtUrunPuan.setText("");
		
		ID = "";
		urunAd = "";
		urunOzellik = "";
		urunPuan = "";
	}

	private void urunlerLabelSetup()//Urunler sayfasýndaki labellarýn oluþturulduðu method
	{
		labelUrunAd = new JLabel("ÜRÜN ADI :");//Label oluþtur ve yazý yaz
		labelUrunAd.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY, ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelUrunAd);//Ana panele ekle
		
		labelUrunOzellik = new JLabel("GRAMAJ/LÝTRE :");//Label oluþtur ve yazý yaz
		labelUrunOzellik.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelUrunOzellik);//Ana panele ekle
		
		labelUrunPuan = new JLabel("PUAN :");//Label oluþtur ve yazý yaz
		labelUrunPuan.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelUrunPuan);//Ana panele ekle
	}

	private void urunlerTxtSetup()//Urunler sayfasýndaki textlerin oluþturulduðu method
	{
		txtUrunAd = new JTextField();//Text oluþtur
		txtUrunAd.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 8));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtUrunAd);//Ana panele ekle
		
		txtUrunOzellik = new JTextField();//Text oluþtur
		txtUrunOzellik.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtUrunOzellik);//Ana panele ekle
		
		txtUrunPuan = new JTextField();//Text oluþtur
		txtUrunPuan.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtUrunPuan);//Ana panele ekle
	}

	private void urunlerButonSetup()//Urunler sayfasýndaki butonlarýn oluþturulduðu method
	{
		//ImageIcon ekle = new ImageIcon("image/ekle.jpg");//Resmi bulunduðu konumdan al
		//butonEkle=new JButton(ekle); 
		btnUrunlerEkle=new JButton("KAYDET");  //Butonu oluþtur ve resim ekle
		btnUrunlerEkle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnUrunlerEkle);//Ana panele ekle
        
        //ImageIcon guncelle = new ImageIcon("image/guncelle.jpg");//Resmi bulunduðu konumdan al
		//butonGuncelle =new JButton(guncelle);
        btnUrunlerGuncelle =new JButton("GÜNCELLE");//Butonu oluþtur ve resim ekle
        btnUrunlerGuncelle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnUrunlerGuncelle);//Ana panele ekle
        
        
        //ImageIcon sil = new ImageIcon("image/sil.jpg");//Resmi bulunduðu konumdan al
		//butonSil=new JButton(sil);
        btnUrunlerSil=new JButton("SÝL");//Butonu oluþtur ve resim ekle
        btnUrunlerSil.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnUrunlerSil);//Ana panele ekle
        
        //ImageIcon arama = new ImageIcon("image/arama.jpg");//Resmi bulunduðu konumdan al
        //butonArama=new JButton(arama);
        btnUrunlerArama=new JButton("ARAMA");//Butonu oluþtur ve resim ekle
        btnUrunlerArama.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 5),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnUrunlerArama);//Ana panele ekle
        
        //ImageIcon guncelle = new ImageIcon("image/guncelle.jpg");//Resmi bulunduðu konumdan al
		//butonGuncelle =new JButton(guncelle);
        btnUrunlerYenile =new JButton("YENÝLE");  //Butonu oluþtur ve resim ekle
        btnUrunlerYenile.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnUrunlerYenile);//Ana panele ekle
        
        //ImageIcon guncelle = new ImageIcon("image/guncelle.jpg");//Resmi bulunduðu konumdan al
		//butonGuncelle =new JButton(guncelle);
        btnUrunlerTemizle =new JButton("TEMÝZLE");  //Butonu oluþtur ve resim ekle
        btnUrunlerTemizle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnUrunlerTemizle);//Ana panele ekle
	}

	private void urunlerBtnClick()//Urunler sayfasýndaki butonlara bastýðýnda çalýþcak olan method
	{
		btnUrunlerEkle.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(txtUrunAd.getText().isEmpty() || txtUrunPuan.getText().isEmpty() || txtUrunOzellik.getText().isEmpty())
	        	{
	        		JOptionPane.showMessageDialog(null, "TÜM ALANLARIN DOLDURULMASI GEREKÝR","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}
				else if(txtUrunAd.getText().trim().equals("") || txtUrunPuan.getText().trim().equals("") || txtUrunOzellik.getText().trim().equals(""))
	        	{
	        		JOptionPane.showMessageDialog(null, "HÝÇBÝR ALAN SPACE TUÞU ÝLE DOLDURULAMAZ","",JOptionPane.ERROR_MESSAGE);
	        	}
				else if(!txtUrunPuan.getText().trim().matches("[0-9]+") || Integer.parseInt(txtUrunPuan.getText().trim()) == 0)
		    	{
		    		JOptionPane.showMessageDialog(null, "GEÇERSÝZ PUAN","HATA",JOptionPane.ERROR_MESSAGE);
		    	}
	        	else if(var_mi("SELECT * FROM `urunler` WHERE urunAd = '" + txtUrunAd.getText() + "' AND urunOzellik = '" + txtUrunOzellik.getText() + "'"))
	        	{
	        		JOptionPane.showMessageDialog(null, "BU ÜRÜN KAYITLI","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}
	        	else
	        	{
	        		String query = "INSERT INTO `urunler` (`urunAd`, `urunOzellik`, `puan`) VALUES ('"+txtUrunAd.getText().trim()+"', '"+txtUrunOzellik.getText().trim()+"','"+txtUrunPuan.getText().trim()+"')";
	            	executeSQlQuery(query, "EKLEME",3);
	        	}
			}
		});
    	
		
		btnUrunlerGuncelle.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(ID.equals(""))
				{
					JOptionPane.showMessageDialog(null, "LÜTFEN BÝLGÝLERÝ GÜNCELLENECEK ÜRÜNÜ TABLODA SEÇÝNÝZ","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else if(txtUrunAd.getText().isEmpty() || txtUrunPuan.getText().isEmpty() || txtUrunOzellik.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "TÜM ALANLARIN DOLDURULMASI GEREKÝR","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else if(txtUrunAd.getText().trim().equals("") || txtUrunPuan.getText().trim().equals("") || txtUrunOzellik.getText().trim().equals(""))
	        	{
	        		JOptionPane.showMessageDialog(null, "HÝÇBÝR ALAN SPACE TUÞU ÝLE DOLDURULAMAZ","",JOptionPane.ERROR_MESSAGE);
	        	}
				else if(!txtUrunPuan.getText().trim().matches("[0-9]+") || Integer.parseInt(txtUrunPuan.getText().trim()) == 0)
		    	{
		    		JOptionPane.showMessageDialog(null, "GEÇERSÝZ PUAN","",JOptionPane.ERROR_MESSAGE);
		    	}
				else
				{
		        	//String query = "UPDATE `urunler` SET `urunAd` = '"+txtUrunAd.getText().trim()+"', `urunOzellik` = '"+txtUrunOzellik.getText().trim()+"', `puan` = '"+txtUrunPuan.getText().trim()+"' WHERE `id` = "+ ID;
					String query = "UPDATE `urunler` SET `urunAd` = '"+txtUrunAd.getText().trim()+"', `urunOzellik` = '"+txtUrunOzellik.getText().trim()+"', `puan` = '"+txtUrunPuan.getText().trim()+"' WHERE urunAd = '" + urunAd + "' AND urunOzellik = '" + urunOzellik + "' AND puan = '" + urunPuan + "'";
					executeSQlQuery(query, "GÜNCELLEME",3);
				}
				
			}
		});
		
		btnUrunlerSil.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(ID.equals(""))
				{
					JOptionPane.showMessageDialog(null, "SÝLÝNECEK ÜRÜNÜ SEÇÝNÝZ","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else if(!var_mi("SELECT * FROM `urunler` WHERE urunAd LIKE '" + txtUrunAd.getText() + "'"))
				{
					JOptionPane.showMessageDialog(null, "BU ÝSÝMDE BÝR ÜRÜN BULUNAMADI","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					int selected = JOptionPane.showConfirmDialog(null, "BU ÜRÜNÜ SÝLMEK ÝSTEDÝÐÝNÝZE EMÝN MÝSÝNÝZ?","SÝLME",JOptionPane.YES_NO_OPTION);
					if(selected == 0)
					{
						//String query = "DELETE FROM `urunler` WHERE id = " + ID;
						String query = "DELETE FROM `urunler` WHERE urunAd = '" + txtUrunAd.getText() + "' AND urunOzellik = '" + txtUrunOzellik.getText() + "' AND puan = '" + txtUrunPuan.getText() + "'";
						executeSQlQuery(query, "SÝLME",3);
					}
					
				}
				
			}
		});
		
		btnUrunlerArama.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(txtUrunAd.getText().isEmpty() && txtUrunPuan.getText().isEmpty() && txtUrunOzellik.getText().isEmpty())
	        	{
					JOptionPane.showMessageDialog(null, "ARAMAK ÝSTEDÝÐÝNÝZ ÜRÜNÜN BÝLGÝLERÝNÝ GÝRÝNÝZ","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}
				else
				{
					search();
				}
			}
		});
		
		btnUrunlerYenile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				urunlerEdit();
			}
		});
		
		btnUrunlerTemizle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				urunlerValueSetup();
			}
		});
	}

	private void urunlerShow(String query)//Urunlersayfasýndaki tabloda verilerin gösterildiði method
	{
		Object[] columns = {"SIRA","ÜRÜN ADI","GRAMAJ/LÝTRE","PUAN"};
		tableCreate(columns);
		ArrayList<urun> list = getUrunlerList(query);
		Object[] row = new Object[4];
		for(int i = 0; i < list.size(); i++)
		{
			row[0] = i + 1;
			row[1] = list.get(i).getUrunAd();
			row[2] = list.get(i).getUrunOzellik();
			row[3] = list.get(i).getUrunPuan();
			
			model.addRow(row);
		}
	}

	private void urunlerTableClick()//Satýþ sayfasýndaki tabloya basýldýðýnda çalýþsacak olan method
	{
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        public void valueChanged(ListSelectionEvent event)
        {
        	ID          = table.getValueAt(table.getSelectedRow(), 0).toString();
        	urunAd      = table.getValueAt(table.getSelectedRow(), 1).toString();
        	urunOzellik = table.getValueAt(table.getSelectedRow(), 2).toString();
        	urunPuan    = table.getValueAt(table.getSelectedRow(), 3).toString();
        	txtUrunAd.setText(urunAd);
        	txtUrunOzellik.setText(urunOzellik);
        	txtUrunPuan.setText(urunPuan);
        }
        });
	}
	
	private void istatistikValueSetup()//Ýstatistik sayfasýndaki deðiþkenlerin sýfýrlandýðý method
	{
		txtilkTarih.setText("");
		txtsonTarih.setText("");
		txtTarihSatis.setText("");
		txtToplamSatis.setText("");
		
		ilktarih    = "";
		sontarih    = "";
		tarihSatis  = "";
		toplamSatis = "";
	}

	private void istatistikLabelSetup()//Ýstatistik sayfasýndaki labellarýn oluþturulduðu method
	{
		labelilkTarih = new JLabel("BAÞLANGIÇ TARÝHÝ :");//Label oluþtur ve yazý yaz
		labelilkTarih.setBounds(pagePositionX + (pageWidth / 2) + 10 , pagePositionY, ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelilkTarih);//Ana panele ekle
		
		labelsonTarih = new JLabel("BÝTÝÞ TARÝHÝ :");//Label oluþtur ve yazý yaz
		labelsonTarih.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelsonTarih);//Ana panele ekle
		
		labelTarihSatis = new JLabel("ARA SATIÞ :");//Label oluþtur ve yazý yaz
		labelTarihSatis.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelTarihSatis);//Ana panele ekle
		
		labelToplamSatis = new JLabel("TOPLAM SATIÞ :");//Label oluþtur ve yazý yaz
		labelToplamSatis.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelToplamSatis);//Ana panele ekle
	}

	private void istatistikTxtSetup()//Ýstatistik sayfasýndaki textlerin oluþturulduðu method
	{
		txtilkTarih = new JTextField();//Text oluþtur
		txtilkTarih.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 20, pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 8));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtilkTarih);//Ana panele ekle
		
		txtsonTarih = new JTextField();//Text oluþtur
		txtsonTarih.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 20, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtsonTarih);//Ana panele ekle
		
		txtTarihSatis = new JTextField();//Text oluþtur
		txtTarihSatis.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 20, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		txtTarihSatis.setEditable(false);//Text üzerinde deðiþiklik yapabilmeyi kapatýyor
		txtTarihSatis.setForeground(Color.RED); //Text'in yazýsýný kýrmýzý renk yap
		anaPanel.add(txtTarihSatis);//Ana panele ekle
		
		txtToplamSatis = new JTextField();//Text oluþtur
		txtToplamSatis.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 20, pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		txtToplamSatis.setEditable(false);//Text üzerinde deðiþiklik yapabilmeyi kapatýyor
		txtToplamSatis.setForeground(Color.RED); //Text'in yazýsýný kýrmýzý renk yap
		anaPanel.add(txtToplamSatis);//Ana panele ekle
	}

	private void istatistikButonSetup()//Ýstatistik sayfasýndaki butonlarýn oluþturulduðu method
	{
		//ImageIcon ekle = new ImageIcon("image/ekle.jpg");//Resmi bulunduðu konumdan al
		//butonEkle=new JButton(ekle); 
		btnIstatistikYenile=new JButton("YENÝLE");  //Butonu oluþtur ve resim ekle
		btnIstatistikYenile.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnIstatistikYenile);//Ana panele ekle

		//ImageIcon ekle = new ImageIcon("image/ekle.jpg");//Resmi bulunduðu konumdan al
		//butonEkle=new JButton(ekle); 
		btnIstatistikTemizle=new JButton("TEMÝZLE");  //Butonu oluþtur ve resim ekle
		btnIstatistikTemizle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnIstatistikTemizle);//Ana panele ekle
        
		//ImageIcon ekle = new ImageIcon("image/ekle.jpg");//Resmi bulunduðu konumdan al
		//butonEkle=new JButton(ekle); 
		btnIstatistikArama=new JButton("ARAMA");  //Butonu oluþtur ve resim ekle
		btnIstatistikArama.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnIstatistikArama);//Ana panele ekle
        
	}

	private void istatistikBtnClick()//Ýstatistik sayfasýndaki butonlara bastýðýnda çalýþcak olan metho
	{
		btnIstatistikArama.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(txtilkTarih.getText().trim().isEmpty() || txtsonTarih.getText().trim().isEmpty())
				{
					//ImageIcon image = new ImageIcon("image/ok.png");
					JOptionPane.showMessageDialog(null, "TÜM ALANLARIN DOLDURULMASI GEREKÝR","UYARI",JOptionPane.WARNING_MESSAGE);
					//JOptionPane.showMessageDialog(null, "TÜM ALANLARIN DOLDURULMASI GEREKÝR","",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("image/ok.png"));
					//JOptionPane.showMessageDialog(null, "TÜM ALANLARIN DOLDURULMASI GEREKÝR", "AAA", JOptionPane.WARNING_MESSAGE);
					
					
					//JOptionPane.showMessageDialog(null, "TÜ", "AAA", JOptionPane.ERROR_MESSAGE);
					//JOptionPane.showMessageDialog(null, "TÜM", "AAA", JOptionPane.QUESTION_MESSAGE);
					//JOptionPane.showMessageDialog(null, "TÜ", "UYARI", JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					//Tarihler GG-AA-YYYY formatýnda mý
					if(dateEdit(txtilkTarih.getText().trim()).equals("") || dateEdit(txtsonTarih.getText().trim()).equals(""))
					{
						JOptionPane.showMessageDialog(null, "                   GEÇERSÝZ TARÝH\nTARÝH   GG-AA-YYYY   ÞEKLÝNDE OLMALIDIR","",JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						//Tabloyu Boþalt
						model.setRowCount(0);
						/*DefaultTableModel dm = (DefaultTableModel) table.getModel();
						int rowCount = dm.getRowCount();
						//Remove rows one by one from the end of the table
						for (int i = rowCount - 1; i >= 0; i--) {
						    dm.removeRow(i);
						}*/
						menu = "istatistik_arama";
						ilktarih = dateEdit(txtilkTarih.getText().trim()) + "T00:00:00.000";
						sontarih = dateEdit(txtsonTarih.getText().trim()) + "T23:59:59.999";
						
						// "SELECT * FROM `satis` WHERE tarih between '" + ilktarih + "' AND '" + sontarih + "' "
						String query = "SELECT * FROM `satis` WHERE tarih >= '" + ilktarih + "' AND tarih <= '" + sontarih + "'";
						if(var_mi(query))
						{
							istatistikShow(query);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "BU TARÝHLER ARASI SATIÞ BULUNMAMAKTADIR", "UYARI", JOptionPane.WARNING_MESSAGE);
						}
		           	 	
		           	 	
		           	 	tarihSatis = istatistikQuery("SELECT SUM(puan) AS 'satis' FROM satis WHERE tarih >= '" + ilktarih + "' AND tarih <= '" + sontarih + "'");
		           	 	toplamSatis = istatistikQuery("SELECT SUM(puan) AS 'satis' FROM satis");
		        		
		        		txtTarihSatis.setText(tarihSatis);
		        		txtToplamSatis.setText(toplamSatis);
		        		menu = "istatistik";
		        		System.out.println(query);
					}
				}
			}
		});
		
		btnIstatistikTemizle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				istatistikValueSetup();
				
				istatistikShow("SELECT * FROM  `satis` ORDER BY tarih ASC");
			}
		});
		
		btnIstatistikYenile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				istatistikEdit();
			}
		});
	}

	private void istatistikShow(String query)//Ýstatistik sayfasýndaki tabloda verilerin gösterildiði method
	{
		if(!menu.equals("istatistik_arama"))
		{
			Object[] columns = {"SIRA","KART ID","TARÝH", "ALINAN ÜRÜNLER", "PUAN"};
			tableCreate(columns);
		}
		ArrayList<satis> list = getSatislarList(query);
		Object[] row = new Object[5];
			    
		for(int i = 0; i < list.size(); i++)
		{
			row[0] = i + 1;
	        row[1] = list.get(i).getKart_id();
	        row[2] = list.get(i).getTarih();
	        row[3] = list.get(i).getSatilanUrunler();
	        row[4] = list.get(i).getPuan();
	        model.addRow(row);
		}
		
		//Toplam puanlarý al
		toplamSatis = istatistikQuery("SELECT SUM(puan) AS 'satis' FROM satis");
		txtToplamSatis.setText(toplamSatis);
	}
	
	private ArrayList<satis> getSatislarList(String query)//Ýstatistik sayfasýndaki verilerin getirildiði method
	{
		ArrayList<satis> satisList = new ArrayList<satis>();
        try 
        {
            rs = st.executeQuery(query);
            satis satis;
            while(rs.next())
            {
        	    satis = new satis(rs.getInt("id"),rs.getString("kard_id"),rs.getString("tarih"),rs.getString("satilan_urunler"),rs.getInt("puan"));
        	    satisList.add(satis);
            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return satisList;
	}
	
	private String istatistikQuery(String query)//Ýstatistik sayfasýndaki verilen gereken sql sorgularýný saðlayan method
	{
		try
		{
			rs = st.executeQuery(query);
			if(rs.next())
			{
				return rs.getString("satis");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String dateEdit(String date)//Ýstatistik sayfasýndaki tarihin istenilen formata dönüþtürülmesini saðlayan method
	{
		if (!date.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})"))
		{
			return "";
		}
		try {
		    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		    return sdf2.format(sdf.parse(date));
		}
		catch (ParseException e)
		{
		    e.printStackTrace();
		}
		
		return "";
	}
	
	private void satisEdit()//Satýþ sayfasýnda verilerin yeniden düzenlendiði method
    {
		//Tüm nesneler sil
		container = getContentPane();
        container.removeAll();
        anaPanel.removeAll();
        anaPanel.revalidate();
        anaPanel.repaint();
        
        menu = "satis";
        
		anaPanel.setBackground(Color.WHITE); //Sayfanýn rengini belirliyor
		
		//Port açýksa kapat
		if(portControl == true)
    	{
    		myTimer.cancel();
    		port.closePort();
    		portControl = false;
    	}
        
        panelEdit();  //Panelleri düzenle
        satisLabelEdit();
        satisTxtEdit();
        satisBtnEdit();
		satisValueSetup();
		satisShow();
		satisTableClick();
		//Port kapalýysa aç
		if(portControl == false)
		{
			readKart();
		}
		
		container.add(anaPanel);
        invalidate();
        repaint();
	}
	
	private void kisilerEdit()//Kiþiler sayfasýnda verilerin yeniden düzenlendiði method
	{
		//Tüm nesneler sil
		container = getContentPane();
        container.removeAll();
        anaPanel.removeAll();
        anaPanel.revalidate();
        anaPanel.repaint();
        
		menu = "kisiler";
        
		anaPanel.setBackground(Color.ORANGE); //Sayfanýn rengini belirliyor
        
		if(portControl == true)
    	{
    		myTimer.cancel();
    		port.closePort();
    		portControl = false;
    	}
		
        //Panelleri düzenle
        panelEdit();
        kisilerLabelEdit();
        kisilerTxtEdit();
        kisilerBtnEdit();
        kisilerValueSetup();
		kisilerShow("SELECT * FROM  `user` ORDER BY ad ASC");
		kisilerTableClick();
		if(portControl == false)
		{
			readKart();
		}
		
		container.add(anaPanel);
        invalidate();
        repaint();
	}
	
	private void urunlerEdit()//Ürünler sayfasýnda verilerin yeniden düzenlendiði method
	{
		//Tüm nesneler sil
		container = getContentPane();
        container.removeAll();
        anaPanel.removeAll();
        anaPanel.revalidate();
        anaPanel.repaint();
        
		menu = "urunler";
        
		anaPanel.setBackground(Color.GREEN); //Sayfanýn rengini belirliyor
		
		if(portControl == true)
    	{
    		myTimer.cancel();
    		port.closePort();
    		portControl = false;
    	}
		
        //Panelleri düzenle
        panelEdit();
        urunlerLabelEdit();
        urunlerTxtEdit();
        urunlerBtnEdit();
        urunlerValueSetup();
		urunlerShow("SELECT * FROM  `urunler` ORDER BY urunAd ASC");
		urunlerTableClick();
        
        container.add(anaPanel);
        invalidate();
        repaint();
	}
	
	private void istatistikEdit()//Ýstatistik sayfasýnda verilerin yeniden düzenlendiði method
	{
		//Tüm nesneler sil
		container = getContentPane();
        container.removeAll();
        anaPanel.removeAll();
        anaPanel.revalidate();
        anaPanel.repaint();
        
		menu = "istatistik";
        
		anaPanel.setBackground(Color.YELLOW); //Sayfanýn rengini belirliyor
		
		if(portControl == true)
    	{
    		myTimer.cancel();
    		port.closePort();
    		portControl = false;
    	}
		
        //Panelleri düzenle
        panelEdit();
        istatistikLabelEdit();
        istatistikTxtEdit();
        istatistikBtnEdit();
        istatistikValueSetup();
        istatistikShow("SELECT * FROM  `satis` ORDER BY tarih ASC");
        
        container.add(anaPanel);
        invalidate();
        repaint();
	}

	private void satisLabelEdit()//Satýþ sayfasýnda labellarýn yeniden düzenlendiði method
	{
		labelCmm.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelCmm);	//Ana panele ekle
		
		portList.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 8)); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(portList); //Ana panele ekle
		
		labelAd.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelAd);	//Ana panele ekle
		
		labelSoyad.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelSoyad);	//Ana panele ekle
		
		labelKartID.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelKartID);	//Ana panele ekle
		
		labelMevcutPuan.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelMevcutPuan);	//Ana panele ekle
		
		labelAlinanUrunler.setBounds(pagePositionX + (pageWidth /2) + (pageWidth /5), pagePositionY, ((pageWidth /2)/ 3), ((pageHeigth / 2) / 6)); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelAlinanUrunler); //Ana panele ekle
		
		labelToplamPuan.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5) * 2) + 10,pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelToplamPuan); //Ana panele ekle
	}

	private void satisTxtEdit()//Satýþ sayfasýnda textlerin yeniden düzenlendiði method
	{
		txtAd.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
		txtAd.setEditable(false);//Text üzerinde deðiþiklik yapabilmeyi kapatýyor
		anaPanel.add(txtAd); //Ana panele ekle
		
		txtSoyad.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
		txtSoyad.setEditable(false);//Text üzerinde deðiþiklik yapabilmeyi kapatýyor
		anaPanel.add(txtSoyad); //Ana panele ekle
		
		txtKartID.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
		txtKartID.setEditable(false);//Text üzerinde deðiþiklik yapabilmeyi kapatýyor
		anaPanel.add(txtKartID); //Ana panele ekle
		
		txtMevcutPuan.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
		txtMevcutPuan.setEditable(false);//Text üzerinde deðiþiklik yapabilmeyi kapatýyor
		anaPanel.add(txtMevcutPuan); //Ana panele ekle
		
        JScrollPane areaPane = new JScrollPane(alinanUrunler); //Baþlangýç noktasýný ve boyutunu belirle
		areaPane.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10), pagePositionY + ((pageHeigth / 2) / 4), (((pageWidth / 2) / 7) * 5), (((pageHeigth / 2) / 10) * 5));
		anaPanel.add(areaPane); //Ana panele ekle
		
		txtToplamPuan.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 3) - 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
		txtToplamPuan.setEditable(false);//Text üzerinde deðiþiklik yapabilmeyi kapatýyor
		anaPanel.add(txtToplamPuan); //Ana panele ekle
	}

	private void satisBtnEdit()//Satýþ sayfasýnda butonlarýn yeniden düzenlendiði method
	{
		//btnSatisTemizle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		btnSatisTemizle.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10) + (((((pageWidth / 2) / 7) * 5)/ 8) * 6), pagePositionY + ((pageHeigth / 2) / 4) + (((pageHeigth / 2) / 10) * 5) + ((pageHeigth / 2) / 25),(((((pageWidth / 2) / 7) * 5)/ 8) * 2), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(btnSatisTemizle); //Ana panele ekle
        
		//btnSatisAdet.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		btnSatisAdet.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10) + (((((pageWidth / 2) / 7) * 5)/ 8) * 3), pagePositionY + ((pageHeigth / 2) / 4) + (((pageHeigth / 2) / 10) * 5) + ((pageHeigth / 2) / 25),(((((pageWidth / 2) / 7) * 5)/ 8) * 2), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(btnSatisAdet); //Ana panele ekle
        
		//btnSatisCikar.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		btnSatisCikar.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10), pagePositionY + ((pageHeigth / 2) / 4) + (((pageHeigth / 2) / 10) * 5) + ((pageHeigth / 2) / 25),(((((pageWidth / 2) / 7) * 5)/ 8) * 2), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(btnSatisCikar); //Ana panele ekle
        
        btnSatisSatis.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnSatisSatis); //Ana panele ekle
        
        btnSatisYenile.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnSatisYenile); //Ana panele ekle
	}

	private void kisilerLabelEdit()//Kiþiler sayfasýnda labellarýn yeniden düzenlendiði method
	{
		labelCmm.setBounds(pagePositionX + (pageWidth / 2) + 10 , pagePositionY, ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelCmm);	//Ana panele ekle
		
		portList.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 8));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(portList);//Ana panele ekle
		
		labelAd.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelAd);//Ana panele ekle
		
		labelSoyad.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelSoyad);//Ana panele ekle

		labelTC.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelTC);//Ana panele ekle
		
		labelKartID.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelKartID);//Ana panele ekle
	}

	private void kisilerTxtEdit()//Kiþiler sayfasýnda textlerin yeniden düzenlendiði method
	{
		txtAd.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		txtAd.setEditable(true);//Text üzerinde deðiþiklik yapabilmeyi saðlýyor
		anaPanel.add(txtAd);//Ana panele ekle
		
		txtSoyad.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		txtSoyad.setEditable(true);//Text üzerinde deðiþiklik yapabilmeyi saðlýyor
		anaPanel.add(txtSoyad);//Ana panele ekle
		
		txtTC.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		txtTC.setEditable(true);//Text üzerinde deðiþiklik yapabilmeyi saðlýyor
		anaPanel.add(txtTC);//Ana panele ekle
		
		txtKartID.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 4), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtKartID);//Ana panele ekle
	}

	private void kisilerBtnEdit()//Kiþiler sayfasýnda butonlarýn yeniden düzenlendiði method
	{
		btnKisilerEkle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnKisilerEkle);//Ana panele ekle
        
        btnKisilerGuncelle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnKisilerGuncelle);//Ana panele ekle
        
        btnKisilerSil.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnKisilerSil);//Ana panele ekle
        
        btnKisilerArama.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 5),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnKisilerArama);//Ana panele ekle
        
        btnKisilerYenile.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnKisilerYenile);//Ana panele ekle
        
        btnKisilerTemizle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnKisilerTemizle);//Ana panele ekle
	}
	
	private void urunlerLabelEdit()//Ürünler sayfasýnda labellarýn yeniden düzenlendiði method
	{
		labelUrunAd.setBounds(pagePositionX + (pageWidth / 2) + 10 , pagePositionY, ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelUrunAd);//Ana panele ekle
		
		labelUrunOzellik.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelUrunOzellik);//Ana panele ekle
		
		labelUrunPuan.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelUrunPuan);//Ana panele ekle
	}

	private void urunlerTxtEdit()//Ürünler sayfasýnda textlerin yeniden düzenlendiði method
	{
		txtUrunAd.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 8));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtUrunAd);//Ana panele ekle
		
		txtUrunOzellik.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtUrunOzellik);//Ana panele ekle
		
		txtUrunPuan.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtUrunPuan);//Ana panele ekle
	}

	private void urunlerBtnEdit()//Ürünler sayfasýnda butonlarýn yeniden düzenlendiði method
	{
		btnUrunlerEkle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnUrunlerEkle);//Ana panele ekle
        
        btnUrunlerGuncelle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnUrunlerGuncelle);//Ana panele ekle
        
        btnUrunlerSil.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnUrunlerSil);//Ana panele ekle
        
        btnUrunlerArama.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 5),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnUrunlerArama);//Ana panele ekle
        
        btnUrunlerYenile.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnUrunlerYenile);//Ana panele ekle
        
        btnUrunlerTemizle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnUrunlerTemizle);//Ana panele ekle
	}

	private void istatistikLabelEdit()//Ýstatistik sayfasýnda labellarýn yeniden düzenlendiði method
	{
		labelilkTarih.setBounds(pagePositionX + (pageWidth / 2) + 10 , pagePositionY, ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelilkTarih);//Ana panele ekle
		
		labelsonTarih.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelsonTarih);//Ana panele ekle
		
		labelTarihSatis.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelTarihSatis);//Ana panele ekle
		
		labelToplamSatis.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(labelToplamSatis);//Ana panele ekle
	}

	private void istatistikTxtEdit()//Ýstatistik sayfasýnda textlerin yeniden düzenlendiði method
	{
		txtilkTarih.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 20, pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 8));//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtilkTarih);//Ana panele ekle
		
		txtsonTarih.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 20, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtsonTarih);//Ana panele ekle
		
		txtTarihSatis.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 20, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtTarihSatis);//Ana panele ekle
		
		txtToplamSatis.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 20, pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
		anaPanel.add(txtToplamSatis);//Ana panele ekle
	}

	private void istatistikBtnEdit()//Ýstatistik sayfasýnda butonlarýn yeniden düzenlendiði method
	{
		btnIstatistikYenile.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnIstatistikYenile);//Ana panele ekle

		btnIstatistikTemizle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnIstatistikTemizle);//Ana panele ekle
        
		btnIstatistikArama.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Baþlangýç noktasýný ve boyutunu belirle
        anaPanel.add(btnIstatistikArama);//Ana panele ekle
	}
	
	public static void main(String[] args) throws SQLException
	{
		recycle nesne = new recycle();
		nesne.setVisible(true);
	}
}
