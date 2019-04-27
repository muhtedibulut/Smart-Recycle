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

	private Container container; //Eklenen componentlerin tutuldu�u nesne
	private JPanel anaPanel,menuPanel,titlePanel,panel3; //Programda bulunan paneller
	private int height,width; //Ekran�n geni�lik ve y�ksekli�inin tutuldu�u de�i�kenler
	private int pagePositionX,pagePositionY; //Sayfalar�n ba�lang�� pozisyonlar�
	private int pageWidth,pageHeigth; ////Sayfalar�n geni�lik ve y�ksekli�inin tutuldu�u de�i�kenler
	private Connection con; //Veri taban� i�lmeleri
    private Statement st; //Veri taban� i�lmeleri
    private ResultSet rs; //Veri taban� i�lmeleri
    private JTable table; //Verilerin listelenmesi
    private DefaultTableModel model; //Verilerin listelenmesi
    private boolean menu_Satis,menu_Kisiler,menu_Urunler,menu_Istatistik; //Menulerin olu�turulup olu�turulup olu�turulmad���n� kontrol eden de�i�kenler
	private String menu; //Program�n hangi menude oldu�unu tutan de�i�ken
    private String ID,ad,soyad,kartID,mevcutPuan,str_alinanUrunler,TC,urunAd,urunPuan,urunOzellik,portData,tableUrun,send,ilktarih,sontarih,tarihSatis,toplamSatis;//Programda kullan�lan de�i�kenler
	private JButton btnSatis,btnKisiler,btnUrunler,btnIstatistik; //Menudeki butonlar
	private JLabel title,projectName,logo; //Menude yer alan labeller ve logosu
	private JSeparator menuSeparetor; //Menude yer alan ay�r�c�
    private JComboBox<String> portList; //Sat�� ve ki�iler sayfas�ndaki COMM listesi
    private JLabel labelCmm,labelAd,labelSoyad,labelKartID,labelMevcutPuan,labelAlinanUrunler,labelToplamPuan,labelTC; //Sat�� sayfas�ndaki gerekli olan labeller
    private JTextField txtAd,txtSoyad,txtKartID,txtMevcutPuan,txtAlinanUrunler,txtToplamPuan,txtTC; //Sat�� sayfas�ndaki gerekli olan textler
    private JTextArea alinanUrunler; //Sat�� sayfas�ndaki aln�nan �r�nler
    private JButton btnSatisSatis,btnSatisTemizle,btnSatisYenile,btnSatisAdet,btnSatisCikar;  //Sat�� sayfas�ndaki gerekli olan butonlar
    private int tablePuan,toplamPuan; //Sat�� sayfas�ndaki se�ilen �r�nlerin toplam puan�n�n sakland��� de�i�ken
    private boolean tableClick; //Tabloya t�klan�p t�klanmad���n�n kontrol�n�n yap�ld��� de�i�ken
    private ArrayList<String> satisUrunList,satisPuanList; //Sat�� sayfas�nladkii se�ilen �r�nlerin tutuldu�u array
    private SerialPort[] portNames; //Bilgisayara ba�l� olan portlar�n tutuldu�u de�i�ken
    private SerialPort port; //Ardunio ile haberle�ilecek olan port
    private boolean portControl; //Portun a��k olup olmad���n�n tutuldu�unu de�i�ken
    private Timer myTimer; //Portun hangi zaman aral�klar�nda dinlenece�inin tuttuldu�u de�i�ken
    private TimerTask gorev;  //Portun hangi zaman aral�klar�nda dinlenece�inin tuttuldu�u de�i�ken
    private JButton btnKisilerEkle,btnKisilerGuncelle,btnKisilerSil,btnKisilerArama,btnKisilerTemizle,btnKisilerYenile;//Ki�iler sayfas�ndaki gerekli olan butonlar
    private JLabel labelUrunAd,labelUrunOzellik,labelUrunPuan; //�r�nler sayfas�ndaki gerekli olan labeller
    private JTextField txtUrunAd,txtUrunOzellik,txtUrunPuan; //�r�nler sayfas�ndaki gerekli olan textler
    private JButton btnUrunlerEkle,btnUrunlerGuncelle,btnUrunlerSil,btnUrunlerArama,btnUrunlerTemizle,btnUrunlerYenile;//�r�nler sayfas�ndaki gerekli olan butonlar
    private JLabel labelilkTarih,labelsonTarih,labelTarihSatis,labelToplamSatis;//�statistik sayfas�ndaki gerekli olan labeller
    private JTextField txtilkTarih,txtsonTarih,txtTarihSatis,txtToplamSatis;//�statistik sayfas�ndaki gerekli olan textler
    private JButton btnIstatistikArama,btnIstatistikTemizle,btnIstatistikYenile;//�statistik sayfas�ndaki gerekli olan butonlar

	public recycle() throws SQLException
	{
		super("SMART RECYCLE"); //Pencerede ��kan isim
    	//Bilgisayar�n ekran y�ksekil�ini ve geni�li�ini al
    	Toolkit tk = Toolkit.getDefaultToolkit();

    	height = ((int) tk.getScreenSize().getHeight());
        width  = ((int) tk.getScreenSize().getWidth()) - 50;

        pagePositionX = (int)width / 8;
        pagePositionY = (int)width / 8;

        pageWidth = width - (width / 8);
        pageHeigth = height - (width / 8);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);//Ekrana boyut veriliyor
        this.setResizable(false); //Ekran�n k���ltilmesini engelle sabit boyutlu

        container = getContentPane();
        container.removeAll();

        anaPanel = new JPanel(null); //Ana panel olu�turuluyor
        anaPanel.setBounds(0, 0, width, height);//Ba�lang�� noktas� ve boyutlar� veriliyor
        anaPanel.setBackground(Color.WHITE);//Panele renk ver

        con = database.dbConnection();
        st = (Statement) con.createStatement();

        //Sat�� sayfas�ndaki arraylerin olu�turulmas�
        satisUrunList = new ArrayList<String>();
        satisPuanList = new ArrayList<String>();

        myTimer=new Timer();//Portun hangi zaman aral�klar�nda dinlenmesi i�in Timer'�n olu�turulmas�

        setup();
	}

	private void setup() //Program�ndaki menulerin ve sat�� sayfas�n�n olu�ruldu�u method
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

	private void panelSetup()//Programdaki panellerin olu�turuldu�u method
	{
		menuPanel = new JPanel(null);//Menu panelini olu�tur
        menuPanel.setBounds(0, 0, (width/8),height );//Ba�lang�� noktas�n� ve boyutunu belirle
        menuPanel.setBackground(new Color(54,33,89));//Arka plan rengini belirle
        anaPanel.add(menuPanel);//Ana panele ekle

        titlePanel = new JPanel(null);//Menu panelini olu�tur
        titlePanel.setBounds((width/8), 0, width - (width/8),(width/8) );//Ba�lang�� noktas�n� ve boyutunu belirle
        titlePanel.setBackground(new Color(122,72,221));//Arka plan rengini belirle
        anaPanel.add(titlePanel);//Ana panele ekle
	}

	private void panelEdit()//Programdaki panellerin yeniden d�zenlendi�i method
	{
        anaPanel.add(menuPanel);//Menu panelini anapanele ekle

        anaPanel.add(titlePanel);//Tittle panelini anapanele ekle
	}

	private void menuLabelSetup()//Menudeki labellerin olu�turuldu�u method
	{
		logo = new JLabel(new ImageIcon("image/icon.png"));//Logonun resmini belirtilen adresten al
        logo.setBounds(0, 0, (width/8), (width/8));//Ba�lang�� noktas�n� ve boyutunu belirle
		menuPanel.add(logo);//Menu paneline ekle

		title = new JLabel("AHMET KABAKLI ERKEK ��RENC� YURDU");//Title yaz� yaz
        title.setBounds((width/8), 0, width - (width/8), (width/8) );//Ba�lang�� noktas�n� ve boyutunu belirle
        title.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));//Fontunu belirle
        title.setBackground(Color.WHITE);//Rengini belirle
        titlePanel.add(title);//Title paneline ekle

        projectName = new JLabel("SMART RECYCLE");//Program�n ismini yaz
        projectName.setBounds(0,(width/8),(width/8),50);//Ba�lang�� noktas�n� ve boyutunu belirle
        projectName.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 18));//Fontunu belirle
        projectName.setForeground(Color.WHITE);//Rengini belirle
        menuPanel.add(projectName);//Menu paneline ekle

        menuSeparetor = new JSeparator();//Ay�r�c�y� olu�tur
        menuSeparetor.setBounds(0,(width/8) + 50, (width/8),10);//Ba�lang�� noktas�n� ve boyutunu belirle
        menuPanel.add(menuSeparetor);//Menu paneline ekle
	}

	private void menuButonSetup()//Menu butonlar�n�n olu�turuldu�u method
	{

		ImageIcon icon1 = new ImageIcon("image/icon1.png");//Sat�� resmini al
		btnSatis = new JButton("SATI�",icon1);//Butonunu olu�tur, yaz� ve resmini belirle
		btnSatis.setBounds(0,250,(width/8),50);//Ba�lang�� noktas�n� ve boyutunu belirle
		//Arka plan� opak(transparant) yap
		btnSatis.setOpaque(false);
		btnSatis.setContentAreaFilled(false);
		btnSatis.setBorderPainted(false);
		btnSatis.setForeground(Color.WHITE);
		btnSatis.setHorizontalAlignment(SwingConstants.LEFT);//Yaz�y� sola kayd�r�r
        menuPanel.add(btnSatis);//Menu paneline ekle

        ImageIcon icon2 = new ImageIcon("image/icon2.png");//Ki�iler resmini al
        btnKisiler = new JButton("K���LER",icon2);//Butonunu olu�tur, yaz� ve resmini belirle
        btnKisiler.setBounds(0,300,(width/8),50);//Ba�lang�� noktas�n� ve boyutunu belirle
        //Arka plan� opak(transparant) yap
        btnKisiler.setOpaque(false);
        btnKisiler.setContentAreaFilled(false);
        btnKisiler.setBorderPainted(false);
        btnKisiler.setForeground(Color.WHITE);
        btnKisiler.setHorizontalAlignment(SwingConstants.LEFT);//Yaz�y� sola kayd�r�r
        menuPanel.add(btnKisiler);//Menu paneline ekle

        ImageIcon icon3 = new ImageIcon("image/icon3.png");//�r�nler resmini al
        btnUrunler = new JButton("�R�NLER",icon3);//Butonunu olu�tur, yaz� ve resmini belirle
        btnUrunler.setBounds(0,350,(width/8),50);//Ba�lang�� noktas�n� ve boyutunu belirle
        //Arka plan� opak(transparant) yap
        btnUrunler.setOpaque(false);
        btnUrunler.setContentAreaFilled(false);
        btnUrunler.setBorderPainted(false);
        btnUrunler.setForeground(Color.WHITE);
        btnUrunler.setHorizontalAlignment(SwingConstants.LEFT);//Yaz�y� sola kayd�r�r
        menuPanel.add(btnUrunler);//Menu paneline ekle

        ImageIcon icon4 = new ImageIcon("image/icon4.png");//�statistik resmini al
        btnIstatistik = new JButton("�STAT�ST�K",icon4);;//Butonunu olu�tur, yaz� ve resmini belirle
        btnIstatistik.setBounds(0,400,(width/8),50);//Ba�lang�� noktas�n� ve boyutunu belirle
        //Arka plan� opak(transparant) yap
        btnIstatistik.setOpaque(false);
        btnIstatistik.setContentAreaFilled(false);
        btnIstatistik.setBorderPainted(false);
        btnIstatistik.setForeground(Color.WHITE);
        btnIstatistik.setHorizontalAlignment(SwingConstants.LEFT);//Yaz�y� sola kayd�r�r
        menuPanel.add(btnIstatistik);//Menu paneline ekle
	}

	private void menuButonClick()//Menu butonlar�n�n bas�ld���nda �al��acak olan method
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

	private void tableCreate(Object[] columns)//Tablonun olu�turuldu�u method
    {
    	table = new JTable();

    	model = new DefaultTableModel()//Tablo �st�nde de�i�iklik yapmay� kapat
    	{
		   @Override
		   public boolean isCellEditable(int row, int column)
		   {
		       return false;
		   }
		};
        model.setColumnIdentifiers(columns);

        table.setModel(model);

		if(columns.length == 4)//�r�nler i�in tablo boyutlar�
		{
			table.getColumnModel().getColumn(0).setPreferredWidth(((pageWidth / 2) / 20) * 2);
			table.getColumnModel().getColumn(1).setPreferredWidth(((pageWidth / 2) / 20) * 14);
			table.getColumnModel().getColumn(2).setPreferredWidth(((pageWidth / 2) / 20) * 2);
			table.getColumnModel().getColumn(2).setPreferredWidth(((pageWidth / 2) / 20) * 2);
		}

        //Tablonun fontlar�
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

	private void executeSQlQuery(String query, String message,int menu)//Gelen sql sorgusunu �al��t�r
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
	        	   JOptionPane.showMessageDialog(null, message+" ��LEM� BA�ARIYLA GER�EKLE�T�","",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("image/ok.png"));
	           }
	           else
	           {
	               JOptionPane.showMessageDialog(null, message + " ��LEM� GER�EKLE�T�R�LEMED�","HATA",JOptionPane.ERROR_MESSAGE);
	           }
	       }
	       catch(Exception ex)
	       {
	           JOptionPane.showMessageDialog(null, "��LEM GER�EKLE�T�R�LEMED�","HATA",JOptionPane.ERROR_MESSAGE);
	       }
	   }

	private boolean var_mi(String query) //Gelen sql sorgusu veri taban�nda var m�?
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
	        JOptionPane.showMessageDialog(null, "VER� TABANI HATASI","HATA",JOptionPane.ERROR_MESSAGE);
	    }
			return false;
	}

    private void veriGetir(String data)//Veritaban�ndan bilgi getiren method
	{
       String query = "SELECT * FROM `user` WHERE kart_id = '" + data +"'"; //Veri taban�ndan okutulan kart� bul

       try
       {
           rs = st.executeQuery(query);
           if(rs.next())//Kart veritaban�na kay�tl� ise
           {
        	   ID = "" +  rs.getInt("id");
        	   ad = rs.getString("ad");
        	   soyad = rs.getString("soyad");
        	   TC = "" + rs.getInt("tc");
        	   kartID = rs.getString("kart_id");
        	   txtAd.setText(ad);
        	   txtSoyad.setText(soyad);
        	   txtKartID.setText(kartID);
        	   if(menu.equals("satis"))
        	   {
        		   txtMevcutPuan.setText(mevcutPuan);
        	   }
        	   else if(menu.equals("kisiler"))
        	   {
        		   txtTC.setText(TC);
        	   }
           }
           else if(menu.equals("satis"))//Kart veritaban�na kay�tl� de�il ise
           {
        	   //Temizle butonunu �al��t�r
        	   btnSatisTemizle.doClick();
        	   JOptionPane.showMessageDialog(null, "BU KARTA KAYITLI B�R K��� BULUNAMAMI�TIR","UYARI",JOptionPane.WARNING_MESSAGE);
           }
           /*else if(x == 1) //Kart veritaban�na kay�tl� de�il ise
           {
        	   //Temizle butonunu �al��t�r
        	   btnSatisTemizle.doClick();
        	   JOptionPane.showMessageDialog(null, "BU KARTA KAYITLI B�R K��� BULUNAMAMI�TIR");
           }*/

       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
	}

    private void sendData(String send)//Verileri ardunio'ya g�nderen method
	{
		try {
			System.out.println(send);
			port.getOutputStream().write(send.getBytes());
			port.getOutputStream().flush();
			Thread.sleep(1000);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "��LEM GER�EKLE�T�R�LEMED�","HATA",JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
	}

	private void search()//Arama butonuna bas�ld���nda veritaban�nda bilgileri arayan method
	{
		String query1;
		String query2 = "";
		//gelen de�er 1 ise ki�iler sayfas�nda arama yap
		if(menu.equals("kisiler"))
		{
			query1 = "SELECT * FROM `user` WHERE ";
			if(!txtAd.getText().trim().equals(""))
			{
				query2 += "ad = '" + txtAd.getText().trim() + "'";
			}
			if(!txtSoyad.getText().trim().equals(""))
			{
				if(!query2.equals(""))
				{
					query2 += " AND ";
				}
				query2 += "soyad = '"  + txtSoyad.getText().trim() + "'";
			}
			if(!txtTC.getText().trim().equals(""))
			{
				if(!query2.equals(""))
				{
					query2 += " AND ";
				}
				query2 += "tc = '" + txtTC.getText().trim() + "'";
			}
			if(!txtKartID.getText().trim().equals(""))
			{
				if(!query2.equals(""))
				{
					query2 += " AND ";
				}
				query2 += "kart_id = '" + txtKartID.getText().trim() + "'";
			}
			//System.out.println(query1 + query2);
			//veri taban�nda b�yle bir veri var m�
			if(var_mi(query1 + query2))
			{
				kisilerShow(query1 + query2);
			}
			else
			{
        		JOptionPane.showMessageDialog(null, "B�YLE B�R K��� KAYITLI DE��L","UYARI",JOptionPane.WARNING_MESSAGE);
			}

		}
		//gelen de�er 1 de�il ise �r�nler sayfas�nda arama yap
		else
		{
			query1 = "SELECT * FROM `urunler` WHERE ";
			if(!txtUrunAd.getText().trim().equals(""))
			{
				query2 += "urunAd = '" + txtUrunAd.getText().trim() + "'";
			}
			if(!txtUrunOzellik.getText().trim().equals(""))
			{
				if(!query2.equals(""))
				{
					query2 += " AND ";
				}
				query2 += "urunOzellik = '" + txtUrunOzellik.getText().trim() + "'";
			}
			if(!txtUrunPuan.getText().trim().equals(""))
			{
				if(!query2.equals(""))
				{
					query2 += " AND ";
				}
				query2 += "puan = '" + txtUrunPuan.getText().trim() + "'";
			}
			//System.out.println(query1 + query2);
			//veri taban�nda b�yle bir veri var m�
			if(var_mi(query1 + query2))
			{
				urunlerShow(query1 + query2);
			}
			else
			{
        		JOptionPane.showMessageDialog(null, "B�YLE B�R �R�N KAYITLI DE��L","UYARI",JOptionPane.WARNING_MESSAGE);
			}

		}
	}

	private void satisSetup()//Sati� sayfas�n�n olu�turuldu�u method
	{
		//T�m nesneler sil
		container = getContentPane();
        container.removeAll();
        anaPanel.removeAll();
        anaPanel.revalidate();
        anaPanel.repaint();

        menu = "satis";

		anaPanel.setBackground(Color.WHITE); //Sayfan�n rengini belirliyor

		//Port a��ksa kapat
		if(portControl == true)
    	{
    		myTimer.cancel();
    		port.closePort();
    		portControl = false;
    	}

        panelEdit();  //Panelleri d�zenle
        satisLabelSetup();
		satisTxtSetup();
		satisButonSetup();
		satisBtnClick();
		satisValueSetup();
		satisShow();
		satisTableClick();
		//Port kapal�ysa a�
		if(portControl == false)
		{
			readKart();
		}

		container.add(anaPanel);
        invalidate();
        repaint();
        menu_Satis = true;//Sat�� sayfas� olu�turuldu
	}

	private void kisilerSetup()//Ki�iler sayfas�n�n olu�turuldu�u method
	{
		//T�m nesneler sil
		container = getContentPane();
        container.removeAll();
        anaPanel.removeAll();
        anaPanel.revalidate();
        anaPanel.repaint();

		menu = "kisiler";

		anaPanel.setBackground(Color.ORANGE); //Sayfan�n rengini belirliyor

		//Port a��ksa kapat
		if(portControl == true)
    	{
    		myTimer.cancel();
    		port.closePort();
    		portControl = false;
    	}

        //Panelleri d�zenle
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
        menu_Kisiler = true;//Ki�iler sayfas� olu�turuldu
	}

	private void urunlerSetup()//�r�nler sayfas�n�n olu�turuldu�u method
	{
		//T�m nesneler sil
		container = getContentPane();
        container.removeAll();
        anaPanel.removeAll();
        anaPanel.revalidate();
        anaPanel.repaint();

		menu = "urunler";

		anaPanel.setBackground(Color.GREEN); //Sayfan�n rengini belirliyor

		//Port a��ksa kapat
		if(portControl == true)
    	{
    		myTimer.cancel();
    		port.closePort();
    		portControl = false;
    	}

        //Panelleri d�zenle
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
        menu_Urunler = true;//Urunler sayfas� olu�turuldu
	}

	private void satisValueSetup()//Sat�� sayfas�ndaki de�i�kenlerin s�f�rland��� method
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

	private void satisLabelSetup()//Sat�� sayfas�ndaki labellar�n olu�turuldu�u method
	{
		labelCmm = new JLabel("COMM PORT :"); //Label olu�tur ve yaz� yaz
		labelCmm.setBounds(pagePositionX + (pageWidth / 2) + 10 , pagePositionY + (pageHeigth / 2), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelCmm);	//Ana panele ekle

		portList = new JComboBox<String>(); //ComboBox'u olu�tur
		portList.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 8)); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(portList); //Ana panele ekle

		labelAd = new JLabel("AD :"); //Label olu�tur ve yaz� yaz
		labelAd.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelAd);	//Ana panele ekle

		labelSoyad = new JLabel("SOYAD :"); //Label olu�tur ve yaz� yaz
		labelSoyad.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelSoyad);	//Ana panele ekle

		labelKartID = new JLabel("KART ID :"); //Label olu�tur ve yaz� yaz
		labelKartID.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelKartID);	//Ana panele ekle

		labelMevcutPuan = new JLabel("MEVCUT PUAN :"); //Label olu�tur ve yaz� yaz
		labelMevcutPuan.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelMevcutPuan);	//Ana panele ekle

		labelAlinanUrunler = new JLabel("ALINAN �R�NLER"); //Label olu�tur ve yaz� yaz
		labelAlinanUrunler.setBounds(pagePositionX + (pageWidth /2) + (pageWidth /5), pagePositionY, ((pageWidth /2)/ 3), ((pageHeigth / 2) / 6)); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelAlinanUrunler); //Ana panele ekle

		labelToplamPuan = new JLabel("TOPLAM PUAN :"); //Label olu�tur ve yaz� yaz
		labelToplamPuan.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5) * 2) + 10,pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelToplamPuan); //Ana panele ekle
	}

	private void satisTxtSetup()//Sat�� sayfas�ndaki textlerin olu�turuldu�u method
	{
		txtAd = new JTextField(""); //Text olu�tur
		txtAd.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
		txtAd.setEditable(false);//Text �zerinde de�i�iklik yapabilmeyi kapat�yor
		anaPanel.add(txtAd); //Ana panele ekle

		txtSoyad = new JTextField(); //Text olu�tur
		txtSoyad.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
		txtSoyad.setEditable(false);//Text �zerinde de�i�iklik yapabilmeyi kapat�yor
		anaPanel.add(txtSoyad); //Ana panele ekle

		txtKartID = new JTextField(); //Text olu�tur
		txtKartID.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
		txtKartID.setEditable(false);//Text �zerinde de�i�iklik yapabilmeyi kapat�yor
		anaPanel.add(txtKartID); //Ana panele ekle

		txtMevcutPuan = new JTextField(); //Text olu�tur
		txtMevcutPuan.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
		txtMevcutPuan.setEditable(false);//Text �zerinde de�i�iklik yapabilmeyi kapat�yor
		txtMevcutPuan.setForeground(Color.RED); //Text'in yaz�s�n� k�rm�z� renk yap
		anaPanel.add(txtMevcutPuan); //Ana panele ekle

		alinanUrunler = new JTextArea (); //TextArea olu�tur
        JScrollPane areaPane = new JScrollPane(alinanUrunler); //Ba�lang�� noktas�n� ve boyutunu belirle
		areaPane.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10), pagePositionY + ((pageHeigth / 2) / 4), (((pageWidth / 2) / 7) * 5), (((pageHeigth / 2) / 10) * 5));
		alinanUrunler.setEditable(false);//Text �zerinde de�i�iklik yapabilmeyi kapat�yor
		anaPanel.add(areaPane); //Ana panele ekle

		txtToplamPuan = new JTextField(); //Text olu�tur
		txtToplamPuan.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 3) - 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
		txtToplamPuan.setEditable(false);//Text �zerinde de�i�iklik yapabilmeyi kapat�yor
		txtToplamPuan.setForeground(Color.RED); //Text'in yaz�s�n� k�rm�z� renk yap
		anaPanel.add(txtToplamPuan); //Ana panele ekle
	}

	private void satisButonSetup()//Ki�iler sayfas�ndaki butonlar�n olu�turuldu�u method
	{
		ImageIcon clear = new ImageIcon("image/clear.png"); //Resmi bulundu�u konumdan al
		btnSatisTemizle = new JButton("TEM�ZLE");  //Butonu olu�tur ve resim ekle
		btnSatisTemizle.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10) + (((((pageWidth / 2) / 7) * 5)/ 14) * 10), pagePositionY + ((pageHeigth / 2) / 4) + (((pageHeigth / 2) / 10) * 5) + ((pageHeigth / 2) / 25),(((((pageWidth / 2) / 7) * 5)/ 14) * 4), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		/*//Arka plan� ayarla
		btnSatisTemizle.setBackground(new Color(122,72,221));
		btnSatisTemizle.setForeground(Color.WHITE);
		btnSatisTemizle.setHorizontalAlignment(SwingConstants.LEFT);//Yaz�y� sola kayd�r�r*/
		anaPanel.add(btnSatisTemizle); //Ana panele ekle

		//ImageIcon ekle = new ImageIcon("image/ekle.jpg"); //Resmi bulundu�u konumdan al
		btnSatisAdet = new JButton("EKLE");  //Butonu olu�tur ve resim ekle
		//btnSatisAdet.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		btnSatisAdet.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10) + (((((pageWidth / 2) / 7) * 5)/ 14) * 5), pagePositionY + ((pageHeigth / 2) / 4) + (((pageHeigth / 2) / 10) * 5) + ((pageHeigth / 2) / 25),(((((pageWidth / 2) / 7) * 5)/ 14) * 4), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnSatisAdet); //Ana panele ekle

		//ImageIcon ekle = new ImageIcon("image/ekle.jpg"); //Resmi bulundu�u konumdan al
		btnSatisCikar = new JButton("�IKAR");  //Butonu olu�tur ve resim ekle
		//btnSatisCikar.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		btnSatisCikar.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10), pagePositionY + ((pageHeigth / 2) / 4) + (((pageHeigth / 2) / 10) * 5) + ((pageHeigth / 2) / 25),(((((pageWidth / 2) / 7) * 5)/ 14) * 4), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnSatisCikar); //Ana panele ekle

        ImageIcon satis = new ImageIcon("image/satis.png"); //Resmi bulundu�u konumdan al
        btnSatisSatis = new JButton("SATI�", satis);  //Butonu olu�tur ve resim ekle
        btnSatisSatis.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
        //Arka plan� ayarla
        btnSatisSatis.setBackground(new Color(122,72,221));
        btnSatisSatis.setForeground(Color.WHITE);
        btnSatisSatis.setHorizontalAlignment(SwingConstants.LEFT);//Yaz�y� sola kayd�r�r
        anaPanel.add(btnSatisSatis); //Ana panele ekle

        btnSatisYenile = new JButton("YEN�LE");  //Butonu olu�tur ve resim ekle
        btnSatisYenile.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnSatisYenile); //Ana panele ekle

        /*ImageIcon yenile = new ImageIcon("image/refresh.png"); //Resmi bulundu�u konumdan al
        btnSatisYenile = new JButton(yenile);  //Butonu olu�tur ve resim ekle
        btnSatisYenile.setBounds(1220,pagePositionY,((pageWidth / 2) / 7),((pageWidth / 2) / 7)); //Ba�lang�� noktas�n� ve boyutunu belirle
        //Arka plan� opak(transparant) yap
        btnSatisYenile.setOpaque(false);
        btnSatisYenile.setContentAreaFilled(false);
        btnSatisYenile.setBorderPainted(false);
        btnSatisYenile.setForeground(Color.WHITE);
        btnSatisYenile.setBackground(Color.GRAY);
        anaPanel.add(btnSatisYenile); //Ana panele ekle*/
	}

	private void satisBtnClick()  //Sat�� sayfas�ndaki butonlara bast���nda �al��cak olan method
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
				//Se�ili �r�n yok ise
				if(satisUrunList.size() == 0)
				{
					JOptionPane.showMessageDialog(null, "L�TFEN �R�N SE��N�Z","UYARI",JOptionPane.WARNING_MESSAGE);
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
				//Se�ili �r�n yok ise
				if(satisUrunList.size() == 0)
				{
					JOptionPane.showMessageDialog(null, "L�TFEN �R�N SE��N�Z","UYARI",JOptionPane.WARNING_MESSAGE);
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
					JOptionPane.showMessageDialog(null, "L�TFEN KARTI OKUTUNUZ","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else if(alinanUrunler.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "L�TFEN �R�N ALINIZ","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else if(txtAd.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "BU KARTA KAYITLI B�R K��� BULUNAMAMI�TIR","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else if(Integer.parseInt(txtToplamPuan.getText()) > Integer.parseInt(txtMevcutPuan.getText()))
				{
					JOptionPane.showMessageDialog(null, "BAK�YE YETERS�Z","",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					send = ":"  + (Integer.parseInt(txtMevcutPuan.getText()) -  Integer.parseInt(txtToplamPuan.getText()))+ ".";
					sendData(send);
				}
			}
		});
	}

	private void satisShow()  //Sat�� ve �r�nler sayfas�ndaki tabloda verilerin g�sterildi�i method
	{
		Object[] columns = {"SIRA","�R�N ADI","GRAMAJ/L�TRE","PUAN"};
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

	private ArrayList<urun> getUrunlerList(String query)//Sat�� sayfas�ndaki verilerin getirildi�i method
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

	private void satisTableClick()  //Sat�� sayfas�ndaki tabloya bas�ld���nda �al��sacak olan method
	{
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event)
	        {
	        	//Tabloda veri var m�
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

	private void satisListShow()  //Sat�� sayfas�nda al�nan �r�nlerin listelendi�ini g�steren method
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

	private void kisilerValueSetup()  //Ki�iler sayfas�ndaki de�i�kenlerin s�f�rland��� method
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

	private void kisilerLabelSetup()//Ki�iler sayfas�ndaki labellar�n olu�turuldu�u method
	{
		labelCmm = new JLabel("COMM PORT :"); //Label olu�tur ve yaz� yaz
		labelCmm.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY, ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelCmm);	//Ana panele ekle

		portList = new JComboBox<String>(); //ComboBox'u olu�tur
		portList.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 8));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(portList);//Ana panele ekle

		labelAd = new JLabel("AD :");//Label olu�tur ve yaz� yaz
		labelAd.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelAd);//Ana panele ekle

		labelSoyad = new JLabel("SOYAD :");//Label olu�tur ve yaz� yaz
		labelSoyad.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelSoyad);//Ana panele ekle

		labelTC = new JLabel("TC :");//Label olu�tur ve yaz� yaz
		labelTC.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelTC);//Ana panele ekle

		labelKartID = new JLabel("KART NO :");//Label olu�tur ve yaz� yaz
		labelKartID.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelKartID);//Ana panele ekle
	}

	private void kisilerTxtSetup()//Ki�iler sayfas�ndaki textlerin olu�turuldu�u method
	{
		txtAd = new JTextField();//Text olu�tur
		txtAd.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(txtAd);//Ana panele ekle

		txtSoyad = new JTextField();//Text olu�tur
		txtSoyad.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(txtSoyad);//Ana panele ekle

		txtTC = new JTextField();//Text olu�tur
		txtTC.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(txtTC);//Ana panele ekle

		txtKartID = new JTextField();//Text olu�tur
		txtKartID.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 4), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		txtKartID.setEditable(false);//Text �zerinde de�i�iklik yapabilmeyi kapat�yor
		anaPanel.add(txtKartID);//Ana panele ekle
	}

	private void kisilerButonSetup()//Ki�iler sayfas�ndaki butonlar�n olu�turuldu�u method
	{
		//ImageIcon ekle = new ImageIcon("image/ekle.jpg");//Resmi bulundu�u konumdan al
		//butonEkle=new JButton(ekle);
		btnKisilerEkle=new JButton("KAYDET");  //Butonu olu�tur ve resim ekle
		btnKisilerEkle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnKisilerEkle);//Ana panele ekle

        //ImageIcon guncelle = new ImageIcon("image/guncelle.jpg");//Resmi bulundu�u konumdan al
		//butonGuncelle =new JButton(guncelle);
        btnKisilerGuncelle =new JButton("G�NCELLE");//Butonu olu�tur ve resim ekle
        btnKisilerGuncelle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnKisilerGuncelle);//Ana panele ekle


        //ImageIcon sil = new ImageIcon("image/sil.jpg");//Resmi bulundu�u konumdan al
		//butonSil=new JButton(sil);
        btnKisilerSil=new JButton("S�L");//Butonu olu�tur ve resim ekle
        btnKisilerSil.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnKisilerSil);//Ana panele ekle

        //ImageIcon arama = new ImageIcon("image/arama.jpg");//Resmi bulundu�u konumdan al
        //butonArama=new JButton(arama);
        btnKisilerArama=new JButton("ARAMA");//Butonu olu�tur ve resim ekle
        btnKisilerArama.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 5),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnKisilerArama);//Ana panele ekle

        //ImageIcon guncelle = new ImageIcon("image/guncelle.jpg");//Resmi bulundu�u konumdan al
		//butonGuncelle =new JButton(guncelle);
        btnKisilerYenile =new JButton("YEN�LE");  //Butonu olu�tur ve resim ekle
        btnKisilerYenile.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnKisilerYenile);//Ana panele ekle

        //ImageIcon guncelle = new ImageIcon("image/guncelle.jpg");//Resmi bulundu�u konumdan al
		//butonGuncelle =new JButton(guncelle);
        btnKisilerTemizle =new JButton("TEM�ZLE");//Butonu olu�tur ve resim ekle
        btnKisilerTemizle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnKisilerTemizle);//Ana panele ekle
	}

	private void kisilerBtnClick()//Ki�iler sayfas�ndaki butonlara bast���nda �al��cak olan method
	{
		btnKisilerEkle.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(txtAd.getText().isEmpty() || txtSoyad.getText().isEmpty() || txtTC.getText().isEmpty() || txtKartID.getText().isEmpty())
	        	{
	        		JOptionPane.showMessageDialog(null, "T�M ALANLARIN DOLDURULMASI VE KARTIN OKUTULMASI GEREK�R","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}
				else if(txtAd.getText().trim().equals("") || txtSoyad.getText().trim().equals("") || txtTC.getText().trim().equals("") || txtKartID.getText().trim().equals(""))
	        	{
	        		JOptionPane.showMessageDialog(null, "H��B�R ALAN SPACE TU�U �LE DOLDURULAMAZ","",JOptionPane.ERROR_MESSAGE);
	        	}
	        	/*else if(!txtTC.getText().trim().matches("[0-9]+") /*|| txtTC.getText().length() != 11*+/|| Integer.parseInt(txtTC.getText().trim()) == 0)
	        	{
	        		JOptionPane.showMessageDialog(null, "GE�ERS�Z TC K�ML�K NO");
	        	}*/
	        	else if(var_mi("SELECT * FROM `user` WHERE tc LIKE '" + txtTC.getText().trim() + "'"))
	        	{
	        		JOptionPane.showMessageDialog(null, "TC K�ML�K NUMARASI KULLANILMAKTA","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}
	        	else if(var_mi("SELECT * FROM `user` WHERE kart_id LIKE '" + txtKartID.getText().trim() + "'"))
	        	{
	        		JOptionPane.showMessageDialog(null, "BU KART BA�KASI ADINA KULLANILMAKTA","UYARI",JOptionPane.WARNING_MESSAGE);
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
	        		JOptionPane.showMessageDialog(null, "L�TFEN B�LG�LER� G�NCELLENECEK K���Y� TABLODA SE��N�Z","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}*/
	        	if(txtAd.getText().isEmpty() || txtSoyad.getText().isEmpty() || txtTC.getText().isEmpty() || txtKartID.getText().isEmpty())
	        	{
	        		JOptionPane.showMessageDialog(null, "L�TFEN B�LG�LER� G�NCELLENECEK K���N�N KARTINI OKUTUNUZ VE T�M ALANLARI DOLDURUNUZ","UYARI",JOptionPane.WARNING_MESSAGE);
	        		//JOptionPane.showMessageDialog(null, "T�M ALANLARIN DOLDURULMASI GEREK�R","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}
	        	else if(txtAd.getText().trim().equals("") || txtSoyad.getText().trim().equals("") || txtTC.getText().trim().equals("") || txtKartID.getText().trim().equals(""))
	        	{
	        		JOptionPane.showMessageDialog(null, "H��B�R ALAN SPACE TU�U �LE DOLDURULAMAZ","",JOptionPane.ERROR_MESSAGE);
	        	}
	        	/*else if(!txtTC.getText().trim().matches("[0-9]+") /*|| txtTC.getText().length() != 11*+/|| Integer.parseInt(txtTC.getText().trim()) == 0)
	        	{
	        		JOptionPane.showMessageDialog(null, "GE�ERS�Z TC K�ML�K NO");
	        	}*/
	        	else
	        	{
	        		menu = "kisiler_guncelle";
	        		send =  txtTC.getText().trim() + ":" + mevcutPuan + "." ;
	        		System.out.println(send);
	        		sendData(send);
	        		//String query = "UPDATE `user` SET `ad` = '"+txtAd.getText().trim()+"', `soyad` = '"+txtSoyad.getText().trim()+"', `tc` = "+txtTC.getText().trim()+", `kart_id` = '"+txtKartID.getText().trim()+"' WHERE `id` = "+ID;
	            	//executeSQlQuery(query, "G�NCELLEME",2);
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
	        		JOptionPane.showMessageDialog(null, "S�L�NECEK K���Y� SE��N�Z","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}
	        	else if(!var_mi("SELECT * FROM `user` WHERE tc LIKE '" + txtTC.getText() + "'"))
	        	{
	        		JOptionPane.showMessageDialog(null, "BU TC K�ML�K NO'YA SAH�P K��� BULUNAMADI","",JOptionPane.ERROR_MESSAGE);
	        	}
	        	else
	        	{
	            	int selected = JOptionPane.showConfirmDialog(null, "BU K���Y� S�LMEK �STED���N�ZE EM�N M�S�N�Z?","S�LME",JOptionPane.YES_NO_OPTION);
					if(selected == 0)
					{
		        		String query = "DELETE FROM `user` WHERE tc =" + TC;
		            	executeSQlQuery(query, "S�LME",2);
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
	        		JOptionPane.showMessageDialog(null, "ARAMAK �STED���N�Z K���N�N B�LG�LER�N� G�R�N�Z","UYARI",JOptionPane.WARNING_MESSAGE);
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

	private void kisilerShow(String query)//Ki�iler sayfas�ndaki tabloda verilerin g�sterildi�i method
	{
		Object[] columns = {"SIRA","�S�M","SOY�S�M","TC","KART ID"};
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

	private ArrayList<user> getUsersList(String query)//Ki�iler sayfas�ndaki verilerin getirildi�i method
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

	private void kisilerTableClick()//Sat�� sayfas�ndaki tabloya bas�ld���nda �al��sacak olan method
	{
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event)
	        {
	        	//Tabloda veri var m�
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

	private void urunlerValueSetup()//Urunler sayfas�ndaki de�i�kenlerin s�f�rland��� method
	{
		txtUrunAd.setText("");
		txtUrunOzellik.setText("");
		txtUrunPuan.setText("");

		ID = "";
		urunAd = "";
		urunOzellik = "";
		urunPuan = "";
	}

	private void urunlerLabelSetup()//Urunler sayfas�ndaki labellar�n olu�turuldu�u method
	{
		labelUrunAd = new JLabel("�R�N ADI :");//Label olu�tur ve yaz� yaz
		labelUrunAd.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY, ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelUrunAd);//Ana panele ekle

		labelUrunOzellik = new JLabel("GRAMAJ/L�TRE :");//Label olu�tur ve yaz� yaz
		labelUrunOzellik.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelUrunOzellik);//Ana panele ekle

		labelUrunPuan = new JLabel("PUAN :");//Label olu�tur ve yaz� yaz
		labelUrunPuan.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelUrunPuan);//Ana panele ekle
	}

	private void urunlerTxtSetup()//Urunler sayfas�ndaki textlerin olu�turuldu�u method
	{
		txtUrunAd = new JTextField();//Text olu�tur
		txtUrunAd.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 8));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(txtUrunAd);//Ana panele ekle

		txtUrunOzellik = new JTextField();//Text olu�tur
		txtUrunOzellik.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(txtUrunOzellik);//Ana panele ekle

		txtUrunPuan = new JTextField();//Text olu�tur
		txtUrunPuan.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(txtUrunPuan);//Ana panele ekle
	}

	private void urunlerButonSetup()//Urunler sayfas�ndaki butonlar�n olu�turuldu�u method
	{
		//ImageIcon ekle = new ImageIcon("image/ekle.jpg");//Resmi bulundu�u konumdan al
		//butonEkle=new JButton(ekle);
		btnUrunlerEkle=new JButton("KAYDET");  //Butonu olu�tur ve resim ekle
		btnUrunlerEkle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnUrunlerEkle);//Ana panele ekle

        //ImageIcon guncelle = new ImageIcon("image/guncelle.jpg");//Resmi bulundu�u konumdan al
		//butonGuncelle =new JButton(guncelle);
        btnUrunlerGuncelle =new JButton("G�NCELLE");//Butonu olu�tur ve resim ekle
        btnUrunlerGuncelle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnUrunlerGuncelle);//Ana panele ekle


        //ImageIcon sil = new ImageIcon("image/sil.jpg");//Resmi bulundu�u konumdan al
		//butonSil=new JButton(sil);
        btnUrunlerSil=new JButton("S�L");//Butonu olu�tur ve resim ekle
        btnUrunlerSil.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnUrunlerSil);//Ana panele ekle

        //ImageIcon arama = new ImageIcon("image/arama.jpg");//Resmi bulundu�u konumdan al
        //butonArama=new JButton(arama);
        btnUrunlerArama=new JButton("ARAMA");//Butonu olu�tur ve resim ekle
        btnUrunlerArama.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 5),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnUrunlerArama);//Ana panele ekle

        //ImageIcon guncelle = new ImageIcon("image/guncelle.jpg");//Resmi bulundu�u konumdan al
		//butonGuncelle =new JButton(guncelle);
        btnUrunlerYenile =new JButton("YEN�LE");  //Butonu olu�tur ve resim ekle
        btnUrunlerYenile.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnUrunlerYenile);//Ana panele ekle

        //ImageIcon guncelle = new ImageIcon("image/guncelle.jpg");//Resmi bulundu�u konumdan al
		//butonGuncelle =new JButton(guncelle);
        btnUrunlerTemizle =new JButton("TEM�ZLE");  //Butonu olu�tur ve resim ekle
        btnUrunlerTemizle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnUrunlerTemizle);//Ana panele ekle
	}

	private void urunlerBtnClick()//Urunler sayfas�ndaki butonlara bast���nda �al��cak olan method
	{
		btnUrunlerEkle.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(txtUrunAd.getText().isEmpty() || txtUrunPuan.getText().isEmpty() || txtUrunOzellik.getText().isEmpty())
	        	{
	        		JOptionPane.showMessageDialog(null, "T�M ALANLARIN DOLDURULMASI GEREK�R","UYARI",JOptionPane.WARNING_MESSAGE);
	        	}
				else if(txtUrunAd.getText().trim().equals("") || txtUrunPuan.getText().trim().equals("") || txtUrunOzellik.getText().trim().equals(""))
	        	{
	        		JOptionPane.showMessageDialog(null, "H��B�R ALAN SPACE TU�U �LE DOLDURULAMAZ","",JOptionPane.ERROR_MESSAGE);
	        	}
				else if(!txtUrunPuan.getText().trim().matches("[0-9]+") || Integer.parseInt(txtUrunPuan.getText().trim()) == 0)
		    	{
		    		JOptionPane.showMessageDialog(null, "GE�ERS�Z PUAN","HATA",JOptionPane.ERROR_MESSAGE);
		    	}
	        	else if(var_mi("SELECT * FROM `urunler` WHERE urunAd = '" + txtUrunAd.getText() + "' AND urunOzellik = '" + txtUrunOzellik.getText() + "'"))
	        	{
	        		JOptionPane.showMessageDialog(null, "BU �R�N KAYITLI","UYARI",JOptionPane.WARNING_MESSAGE);
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
					JOptionPane.showMessageDialog(null, "L�TFEN B�LG�LER� G�NCELLENECEK �R�N� TABLODA SE��N�Z","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else if(txtUrunAd.getText().isEmpty() || txtUrunPuan.getText().isEmpty() || txtUrunOzellik.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "T�M ALANLARIN DOLDURULMASI GEREK�R","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else if(txtUrunAd.getText().trim().equals("") || txtUrunPuan.getText().trim().equals("") || txtUrunOzellik.getText().trim().equals(""))
	        	{
	        		JOptionPane.showMessageDialog(null, "H��B�R ALAN SPACE TU�U �LE DOLDURULAMAZ","",JOptionPane.ERROR_MESSAGE);
	        	}
				else if(!txtUrunPuan.getText().trim().matches("[0-9]+") || Integer.parseInt(txtUrunPuan.getText().trim()) == 0)
		    	{
		    		JOptionPane.showMessageDialog(null, "GE�ERS�Z PUAN","",JOptionPane.ERROR_MESSAGE);
		    	}
				else
				{
		        	//String query = "UPDATE `urunler` SET `urunAd` = '"+txtUrunAd.getText().trim()+"', `urunOzellik` = '"+txtUrunOzellik.getText().trim()+"', `puan` = '"+txtUrunPuan.getText().trim()+"' WHERE `id` = "+ ID;
					String query = "UPDATE `urunler` SET `urunAd` = '"+txtUrunAd.getText().trim()+"', `urunOzellik` = '"+txtUrunOzellik.getText().trim()+"', `puan` = '"+txtUrunPuan.getText().trim()+"' WHERE urunAd = '" + urunAd + "' AND urunOzellik = '" + urunOzellik + "' AND puan = '" + urunPuan + "'";
					executeSQlQuery(query, "G�NCELLEME",3);
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
					JOptionPane.showMessageDialog(null, "S�L�NECEK �R�N� SE��N�Z","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else if(!var_mi("SELECT * FROM `urunler` WHERE urunAd LIKE '" + txtUrunAd.getText() + "'"))
				{
					JOptionPane.showMessageDialog(null, "BU �S�MDE B�R �R�N BULUNAMADI","UYARI",JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					int selected = JOptionPane.showConfirmDialog(null, "BU �R�N� S�LMEK �STED���N�ZE EM�N M�S�N�Z?","S�LME",JOptionPane.YES_NO_OPTION);
					if(selected == 0)
					{
						//String query = "DELETE FROM `urunler` WHERE id = " + ID;
						String query = "DELETE FROM `urunler` WHERE urunAd = '" + txtUrunAd.getText() + "' AND urunOzellik = '" + txtUrunOzellik.getText() + "' AND puan = '" + txtUrunPuan.getText() + "'";
						executeSQlQuery(query, "S�LME",3);
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
					JOptionPane.showMessageDialog(null, "ARAMAK �STED���N�Z �R�N�N B�LG�LER�N� G�R�N�Z","UYARI",JOptionPane.WARNING_MESSAGE);
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

	private void urunlerShow(String query)//Urunlersayfas�ndaki tabloda verilerin g�sterildi�i method
	{
		Object[] columns = {"SIRA","�R�N ADI","GRAMAJ/L�TRE","PUAN"};
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

	private void urunlerTableClick()//Sat�� sayfas�ndaki tabloya bas�ld���nda �al��sacak olan method
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



	private void urunlerEdit()//�r�nler sayfas�nda verilerin yeniden d�zenlendi�i method
	{
		//T�m nesneler sil
		container = getContentPane();
        container.removeAll();
        anaPanel.removeAll();
        anaPanel.revalidate();
        anaPanel.repaint();

		menu = "urunler";

		anaPanel.setBackground(Color.GREEN); //Sayfan�n rengini belirliyor

		if(portControl == true)
    	{
    		myTimer.cancel();
    		port.closePort();
    		portControl = false;
    	}

        //Panelleri d�zenle
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

	private void istatistikEdit()//�statistik sayfas�nda verilerin yeniden d�zenlendi�i method
	{
		//T�m nesneler sil
		container = getContentPane();
        container.removeAll();
        anaPanel.removeAll();
        anaPanel.revalidate();
        anaPanel.repaint();

		menu = "istatistik";

		anaPanel.setBackground(Color.YELLOW); //Sayfan�n rengini belirliyor

		if(portControl == true)
    	{
    		myTimer.cancel();
    		port.closePort();
    		portControl = false;
    	}

        //Panelleri d�zenle
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

	private void satisLabelEdit()//Sat�� sayfas�nda labellar�n yeniden d�zenlendi�i method
	{
		labelCmm.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelCmm);	//Ana panele ekle

		portList.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 8)); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(portList); //Ana panele ekle

		labelAd.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelAd);	//Ana panele ekle

		labelSoyad.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelSoyad);	//Ana panele ekle

		labelKartID.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelKartID);	//Ana panele ekle

		labelMevcutPuan.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6)); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelMevcutPuan);	//Ana panele ekle

		labelAlinanUrunler.setBounds(pagePositionX + (pageWidth /2) + (pageWidth /5), pagePositionY, ((pageWidth /2)/ 3), ((pageHeigth / 2) / 6)); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelAlinanUrunler); //Ana panele ekle

		labelToplamPuan.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5) * 2) + 10,pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelToplamPuan); //Ana panele ekle
	}

	private void satisTxtEdit()//Sat�� sayfas�nda textlerin yeniden d�zenlendi�i method
	{
		txtAd.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
		txtAd.setEditable(false);//Text �zerinde de�i�iklik yapabilmeyi kapat�yor
		anaPanel.add(txtAd); //Ana panele ekle

		txtSoyad.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
		txtSoyad.setEditable(false);//Text �zerinde de�i�iklik yapabilmeyi kapat�yor
		anaPanel.add(txtSoyad); //Ana panele ekle

		txtKartID.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
		txtKartID.setEditable(false);//Text �zerinde de�i�iklik yapabilmeyi kapat�yor
		anaPanel.add(txtKartID); //Ana panele ekle

		txtMevcutPuan.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
		txtMevcutPuan.setEditable(false);//Text �zerinde de�i�iklik yapabilmeyi kapat�yor
		anaPanel.add(txtMevcutPuan); //Ana panele ekle

        JScrollPane areaPane = new JScrollPane(alinanUrunler); //Ba�lang�� noktas�n� ve boyutunu belirle
		areaPane.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10), pagePositionY + ((pageHeigth / 2) / 4), (((pageWidth / 2) / 7) * 5), (((pageHeigth / 2) / 10) * 5));
		anaPanel.add(areaPane); //Ana panele ekle

		txtToplamPuan.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 3) - 10, pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
		txtToplamPuan.setEditable(false);//Text �zerinde de�i�iklik yapabilmeyi kapat�yor
		anaPanel.add(txtToplamPuan); //Ana panele ekle
	}

	private void satisBtnEdit()//Sat�� sayfas�nda butonlar�n yeniden d�zenlendi�i method
	{
		//btnSatisTemizle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		btnSatisTemizle.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10) + (((((pageWidth / 2) / 7) * 5)/ 8) * 6), pagePositionY + ((pageHeigth / 2) / 4) + (((pageHeigth / 2) / 10) * 5) + ((pageHeigth / 2) / 25),(((((pageWidth / 2) / 7) * 5)/ 8) * 2), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(btnSatisTemizle); //Ana panele ekle

		//btnSatisAdet.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		btnSatisAdet.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10) + (((((pageWidth / 2) / 7) * 5)/ 8) * 3), pagePositionY + ((pageHeigth / 2) / 4) + (((pageHeigth / 2) / 10) * 5) + ((pageHeigth / 2) / 25),(((((pageWidth / 2) / 7) * 5)/ 8) * 2), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(btnSatisAdet); //Ana panele ekle

		//btnSatisCikar.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		btnSatisCikar.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2) / 10), pagePositionY + ((pageHeigth / 2) / 4) + (((pageHeigth / 2) / 10) * 5) + ((pageHeigth / 2) / 25),(((((pageWidth / 2) / 7) * 5)/ 8) * 2), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(btnSatisCikar); //Ana panele ekle

        btnSatisSatis.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (pageHeigth / 2) + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnSatisSatis); //Ana panele ekle

        btnSatisYenile.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10); //Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnSatisYenile); //Ana panele ekle
	}

	private void kisilerLabelEdit()//Ki�iler sayfas�nda labellar�n yeniden d�zenlendi�i method
	{
		labelCmm.setBounds(pagePositionX + (pageWidth / 2) + 10 , pagePositionY, ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelCmm);	//Ana panele ekle

		portList.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 8));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(portList);//Ana panele ekle

		labelAd.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelAd);//Ana panele ekle

		labelSoyad.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelSoyad);//Ana panele ekle

		labelTC.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelTC);//Ana panele ekle

		labelKartID.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelKartID);//Ana panele ekle
	}

	private void kisilerTxtEdit()//Ki�iler sayfas�nda textlerin yeniden d�zenlendi�i method
	{
		txtAd.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		txtAd.setEditable(true);//Text �zerinde de�i�iklik yapabilmeyi sa�l�yor
		anaPanel.add(txtAd);//Ana panele ekle

		txtSoyad.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		txtSoyad.setEditable(true);//Text �zerinde de�i�iklik yapabilmeyi sa�l�yor
		anaPanel.add(txtSoyad);//Ana panele ekle

		txtTC.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		txtTC.setEditable(true);//Text �zerinde de�i�iklik yapabilmeyi sa�l�yor
		anaPanel.add(txtTC);//Ana panele ekle

		txtKartID.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 4), ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(txtKartID);//Ana panele ekle
	}

	private void kisilerBtnEdit()//Ki�iler sayfas�nda butonlar�n yeniden d�zenlendi�i method
	{
		btnKisilerEkle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnKisilerEkle);//Ana panele ekle

        btnKisilerGuncelle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnKisilerGuncelle);//Ana panele ekle

        btnKisilerSil.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnKisilerSil);//Ana panele ekle

        btnKisilerArama.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 5),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnKisilerArama);//Ana panele ekle

        btnKisilerYenile.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnKisilerYenile);//Ana panele ekle

        btnKisilerTemizle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnKisilerTemizle);//Ana panele ekle
	}

	private void urunlerLabelEdit()//�r�nler sayfas�nda labellar�n yeniden d�zenlendi�i method
	{
		labelUrunAd.setBounds(pagePositionX + (pageWidth / 2) + 10 , pagePositionY, ((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelUrunAd);//Ana panele ekle

		labelUrunOzellik.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelUrunOzellik);//Ana panele ekle

		labelUrunPuan.setBounds(pagePositionX + (pageWidth / 2) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(labelUrunPuan);//Ana panele ekle
	}

	private void urunlerTxtEdit()//�r�nler sayfas�nda textlerin yeniden d�zenlendi�i method
	{
		txtUrunAd.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 8));//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(txtUrunAd);//Ana panele ekle

		txtUrunOzellik.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(txtUrunOzellik);//Ana panele ekle

		txtUrunPuan.setBounds(pagePositionX + (pageWidth / 2) + ((pageWidth / 2 )/ 5) + 10, pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
		anaPanel.add(txtUrunPuan);//Ana panele ekle
	}

	private void urunlerBtnEdit()//�r�nler sayfas�nda butonlar�n yeniden d�zenlendi�i method
	{
		btnUrunlerEkle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 2),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnUrunlerEkle);//Ana panele ekle

        btnUrunlerGuncelle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 3),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnUrunlerGuncelle);//Ana panele ekle

        btnUrunlerSil.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 4),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnUrunlerSil);//Ana panele ekle

        btnUrunlerArama.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 5),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnUrunlerArama);//Ana panele ekle

        btnUrunlerYenile.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY,((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnUrunlerYenile);//Ana panele ekle

        btnUrunlerTemizle.setBounds(pagePositionX + (pageWidth / 2) + (((pageWidth / 2 )/ 5)* 4), pagePositionY + (((pageHeigth / 2) / 6) * 1),((pageWidth / 2 )/ 5), ((pageHeigth / 2) / 6) - 10);//Ba�lang�� noktas�n� ve boyutunu belirle
        anaPanel.add(btnUrunlerTemizle);//Ana panele ekle
	}

	

	public static void main(String[] args) throws SQLException
	{
		recycle nesne = new recycle();
		nesne.setVisible(true);
	}
}
